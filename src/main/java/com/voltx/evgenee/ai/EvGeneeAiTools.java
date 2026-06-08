package com.voltx.evgenee.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voltx.evgenee.client.GeocodingService;
import com.voltx.evgenee.entity.*;
import com.voltx.evgenee.enums.BookingStatus;
import com.voltx.evgenee.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.time.*;
import java.util.*;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class EvGeneeAiTools {

    private final StationRepository stationRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final EvUserRepository evUserRepository;
    private final VehicleRepository vehicleRepository;
    private final GeocodingService geocodingService;

    private static final double BhopalLon = 77.4126;
    private static final double BhopalLat = 23.2599;
    private static final ZoneId IST = ZoneId.of("Asia/Kolkata");

    private static String minutesToTime(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%02d:%02d", h, m);
    }

    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    private static boolean isOverlapping(String startA, String endA, String startB, String endB) {
        return timeToMinutes(startA) < timeToMinutes(endB) && timeToMinutes(startB) < timeToMinutes(endA);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c;
    }

    private AvailabilityResult checkAvailability(Long stationId, LocalDate date, String startTime, String endTime, int maxPorts, List<Booking> bookings) {
        int reqStart = timeToMinutes(startTime);
        int reqEnd = timeToMinutes(endTime);

        List<Event> events = new ArrayList<>();
        int currentConcurrent = 0;

        for (Booking b : bookings) {
            ZonedDateTime startZ = b.getStartTime().atZone(IST);
            ZonedDateTime endZ = b.getEndTime().atZone(IST);
            if (!startZ.toLocalDate().equals(date)) continue;

            String bStartStr = String.format("%02d:%02d", startZ.getHour(), startZ.getMinute());
            String bEndStr = String.format("%02d:%02d", endZ.getHour(), endZ.getMinute());

            int bStart = timeToMinutes(bStartStr);
            int bEnd = timeToMinutes(bEndStr);

            if (bStart < reqEnd && bEnd > reqStart) {
                events.add(new Event(bStart, 1));
                events.add(new Event(bEnd, -1));
            }
            if (reqStart >= bStart && reqStart < bEnd) {
                currentConcurrent++;
            }
        }

        if (currentConcurrent >= maxPorts) {
            return new AvailabilityResult(false, startTime);
        }

        events.sort(Comparator.comparingInt((Event e) -> e.time).thenComparingInt(e -> e.type));

        for (Event event : events) {
            if (event.time >= reqEnd) break;
            if (event.time > reqStart) {
                currentConcurrent += event.type;
                if (currentConcurrent >= maxPorts) {
                    return new AvailabilityResult(false, minutesToTime(event.time));
                }
            }
        }

        return new AvailabilityResult(true, null);
    }

    private String findNextAvailableSlot(Long stationId, LocalDate date, String startTime, int durationMinutes, int maxPorts, String openingHours, List<Booking> bookings) {
        int currentStartMin = timeToMinutes(startTime);
        int searchLimitMin = currentStartMin + 480;
        int openMin = 0;
        int closeMin = 1439;

        if (openingHours != null && openingHours.contains("-")) {
            try {
                String[] parts = openingHours.split("-");
                openMin = timeToMinutes(parts[0].trim());
                closeMin = timeToMinutes(parts[1].trim());
            } catch (Exception ignored) {}
        }

        List<Interval> bookingIntervals = new ArrayList<>();
        for (Booking b : bookings) {
            ZonedDateTime startZ = b.getStartTime().atZone(IST);
            ZonedDateTime endZ = b.getEndTime().atZone(IST);
            if (!startZ.toLocalDate().equals(date)) continue;

            String bStartStr = String.format("%02d:%02d", startZ.getHour(), startZ.getMinute());
            String bEndStr = String.format("%02d:%02d", endZ.getHour(), endZ.getMinute());

            bookingIntervals.add(new Interval(timeToMinutes(bStartStr), timeToMinutes(bEndStr)));
        }

        while (currentStartMin + durationMinutes <= Math.min(searchLimitMin, closeMin)) {
            currentStartMin += 15;
            int nextStart = currentStartMin;
            int nextEnd = currentStartMin + durationMinutes;

            int concurrent = 0;
            for (Interval interval : bookingIntervals) {
                if (interval.start < nextEnd && interval.end > nextStart) {
                    concurrent++;
                    if (concurrent >= maxPorts) break;
                }
            }

            if (concurrent < maxPorts) {
                return minutesToTime(nextStart);
            }
        }

        return null;
    }

    private record Event(int time, int type) {}
    private record Interval(int start, int end) {}
    private record AvailabilityResult(boolean available, String conflictTime) {}

    private Map<String, Object> mapStationToDto(Station st, Double distKm, Double travelTimeMins) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", st.getId().toString());
        dto.put("_id", st.getId().toString());
        dto.put("name", st.getName());
        dto.put("city", "Bhopal");
        if (st.getAddress() != null && st.getAddress().contains(",")) {
            dto.put("city", st.getAddress().split(",")[0].trim());
        }
        dto.put("isOpen", true);
        
        int ports = st.getChargersCount() != null ? st.getChargersCount() : 4;
        dto.put("totalPorts", ports);
        dto.put("availablePorts", ports);
        dto.put("chargerTypes", List.of("CCS2", "Type2", "CHAdeMO"));
        dto.put("chargingSpeed", 50);

        List<Map<String, Object>> pricing = new ArrayList<>();
        pricing.add(Map.of("connectorType", "CCS2", "priceperKWh", 15.0, "portCount", ports / 2 + 1, "currency", "INR"));
        pricing.add(Map.of("connectorType", "Type2", "priceperKWh", 12.0, "portCount", ports / 2 + 1, "currency", "INR"));
        pricing.add(Map.of("connectorType", "CHAdeMO", "priceperKWh", 18.0, "portCount", 1, "currency", "INR"));
        dto.put("pricing", pricing);
        
        dto.put("isCompatible", true);
        dto.put("roadDistance", distKm);
        dto.put("travelTime", travelTimeMins);
        
        return dto;
    }

    public record FindBestStationInput(
        String location,
        String date,
        String startTime,
        String endTime,
        String connectorType
    ) {}

    @Tool
    @Description("Searches for EV charging stations and checks port availability against active bookings.")
    public String findBestStation(@ToolParam(description = "input from the user for finding the best station") FindBestStationInput input) {
            try {
                log.info("Tool find_best_station called with input: {}", input);
                UserContextHolder.UserContext uCtx = UserContextHolder.get();
                String userEmail = uCtx != null ? uCtx.email() : null;
                Double userLat = uCtx != null ? uCtx.lat() : null;
                Double userLng = uCtx != null ? uCtx.lng() : null;

                double[] coords = null;
                String locationName = input.location();

                if (locationName != null && !locationName.trim().isEmpty()) {
                    String clean = locationName.trim();
                    if (clean.matches("^(-?\\d+(\\.\\d+)?)\\s*,\\s*(-?\\d+(\\.\\d+)?)$")) {
                        String[] parts = clean.split(",");
                        coords = new double[]{Double.parseDouble(parts[1].trim()), Double.parseDouble(parts[0].trim())};
                    } else {
                        coords = geocodingService.geocodeLocation(locationName);
                    }
                }

                if (coords == null && userLat != null && userLng != null) {
                    coords = new double[]{userLng, userLat};
                    String addr = geocodingService.reverseGeocode(userLat, userLng);
                    locationName = addr != null ? addr : "your current location";
                }

                if (coords == null && userEmail != null) {
                    Optional<EvUser> euOpt = evUserRepository.findByEmail(userEmail);
                    if (euOpt.isPresent()) {
                        List<Booking> userBookings = bookingRepository.findAll(); // Simple query
                        Booking last = userBookings.stream()
                                .filter(b -> b.getUser().getId().equals(euOpt.get().getId()))
                                .max(Comparator.comparing(Booking::getCreatedAt))
                                .orElse(null);
                        if (last != null && last.getStation() != null) {
                            Station st = last.getStation();
                            if (st.getLatitude() != null && st.getLongitude() != null) {
                                coords = new double[]{st.getLongitude(), st.getLatitude()};
                                locationName = st.getName();
                            }
                        }
                    }
                }

                if (coords == null) {
                    coords = new double[]{BhopalLon, BhopalLat};
                    locationName = "Bhopal";
                }

                List<Station> stations = stationRepository.findAll();
                final double[] finalCoords = coords;
                stations.sort(Comparator.comparingDouble(s -> calculateDistance(finalCoords[1], finalCoords[0], s.getLatitude() != null ? s.getLatitude() : BhopalLat, s.getLongitude() != null ? s.getLongitude() : BhopalLon)));

                if (stations.isEmpty()) {
                    return "{\"error\": \"I couldn't find any charging stations nearby.\" }";
                }

                LocalDate queryDate = LocalDate.now(IST);
                if (input.date() != null && !input.date().trim().isEmpty() && !input.date().equalsIgnoreCase("today")) {
                    try {
                        queryDate = LocalDate.parse(input.date().trim());
                    } catch (Exception ignored) {}
                }

                String effectiveEndTime = (input.endTime() != null && !input.endTime().trim().isEmpty())
                        ? input.endTime()
                        : minutesToTime(timeToMinutes(input.startTime()) + 60);

                LocalDate today = LocalDate.now(IST);
                LocalTime nowTime = LocalTime.now(IST);
                int currentMinutes = nowTime.getHour() * 60 + nowTime.getMinute();

                if (queryDate.isBefore(today)) {
                    return "{\"error\": \"Cannot search for past dates.\" }";
                }
                if (queryDate.equals(today) && timeToMinutes(input.startTime()) <= currentMinutes) {
                    return "{\"error\": \"The requested start time has already passed for today. Please provide a future time.\" }";
                }

                Station exactMatchStation = null;
                GeocodingService.RoadInfo exactRoadInfo = null;
                List<Map<String, Object>> stationsData = new ArrayList<>();

                int reqDuration = timeToMinutes(effectiveEndTime) - timeToMinutes(input.startTime());

                int limit = Math.min(5, stations.size());
                for (int i = 0; i < limit; i++) {
                    Station st = stations.get(i);
                    double dist = calculateDistance(finalCoords[1], finalCoords[0], st.getLatitude() != null ? st.getLatitude() : BhopalLat, st.getLongitude() != null ? st.getLongitude() : BhopalLon);
                    GeocodingService.RoadInfo roadInfo = geocodingService.getRoadDistance(finalCoords, new double[]{st.getLongitude() != null ? st.getLongitude() : BhopalLon, st.getLatitude() != null ? st.getLatitude() : BhopalLat});
                    
                    double roadDist = roadInfo != null ? roadInfo.distanceKm() : dist;
                    double roadTime = roadInfo != null ? roadInfo.durationMins() : (dist * 1.5);

                    List<Booking> stBookings = bookingRepository.findAll();
                    List<Booking> activeBookings = new ArrayList<>();
                    for (Booking b : stBookings) {
                        if (b.getStation().getId().equals(st.getId()) &&
                            (b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.IN_PROGRESS ||
                             (b.getStatus() == BookingStatus.PENDING && b.getCreatedAt().isAfter(Instant.now().minusSeconds(600))))) {
                            activeBookings.add(b);
                        }
                    }

                    int maxPorts = st.getChargersCount() != null ? st.getChargersCount() : 4;
                    AvailabilityResult avResult = checkAvailability(st.getId(), queryDate, input.startTime(), effectiveEndTime, maxPorts, activeBookings);

                    Map<String, Object> sDto = mapStationToDto(st, roadDist, roadTime);
                    if (avResult.available()) {
                        exactMatchStation = st;
                        exactRoadInfo = roadInfo;
                        sDto.put("nextAvailableSlot", input.startTime());
                        stationsData.add(sDto);
                        break;
                    } else {
                        String nextSlot = findNextAvailableSlot(st.getId(), queryDate, input.startTime(), reqDuration, maxPorts, "08:00 - 22:00", activeBookings);
                        sDto.put("nextAvailableSlot", nextSlot);
                        stationsData.add(sDto);
                    }
                }

                ObjectMapper mapper = new ObjectMapper();
                if (exactMatchStation != null) {
                    double distKm = exactRoadInfo != null ? exactRoadInfo.distanceKm() : calculateDistance(finalCoords[1], finalCoords[0], exactMatchStation.getLatitude(), exactMatchStation.getLongitude());
                    double timeMin = exactRoadInfo != null ? exactRoadInfo.durationMins() : distKm * 1.5;
                    String distStr = String.format(" (approx. %.2f KM, %.0f mins away by road)", distKm, timeMin);
                    
                    Map<String, Object> resp = new HashMap<>();
                    resp.put("text", "Found a great match! " + exactMatchStation.getName() + distStr + " is AVAILABLE from " + input.startTime() + " to " + effectiveEndTime + ".\nWould you like me to book it for you?");
                    resp.put("stations", stationsData);
                    resp.put("foundAvailable", true);
                    ToolResultHolder.set(new ToolResultHolder.ToolResult(null, null, stationsData));
                    return mapper.writeValueAsString(resp);
                }

                Station altMatchStation = null;
                String altStartStr = null;
                String altEndStr = null;
                GeocodingService.RoadInfo altRoadInfo = null;

                for (int i = 0; i < limit; i++) {
                    Station st = stations.get(i);
                    int maxPorts = st.getChargersCount() != null ? st.getChargersCount() : 4;
                    
                    List<Booking> stBookings = bookingRepository.findAll();
                    List<Booking> activeBookings = new ArrayList<>();
                    for (Booking b : stBookings) {
                        if (b.getStation().getId().equals(st.getId()) &&
                            (b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.IN_PROGRESS || b.getStatus() == BookingStatus.PENDING)) {
                            activeBookings.add(b);
                        }
                    }

                    int startMins = timeToMinutes(input.startTime());
                    for (int offset = 60; offset <= 240; offset += 60) {
                        int altStart = startMins + offset;
                        int altEnd = altStart + reqDuration;
                        if (altStart >= 24 * 60 || altEnd >= 24 * 60) continue;

                        String ast = minutesToTime(altStart);
                        String aed = minutesToTime(altEnd);

                        AvailabilityResult altAv = checkAvailability(st.getId(), queryDate, ast, aed, maxPorts, activeBookings);
                        if (altAv.available()) {
                            altMatchStation = st;
                            altStartStr = ast;
                            altEndStr = aed;
                            altRoadInfo = geocodingService.getRoadDistance(finalCoords, new double[]{st.getLongitude(), st.getLatitude()});
                            break;
                        }
                    }
                    if (altMatchStation != null) break;
                }

                if (altMatchStation != null) {
                    double distKm = altRoadInfo != null ? altRoadInfo.distanceKm() : calculateDistance(finalCoords[1], finalCoords[0], altMatchStation.getLatitude(), altMatchStation.getLongitude());
                    double timeMin = altRoadInfo != null ? altRoadInfo.durationMins() : distKm * 1.5;
                    String distStr = String.format(" (approx. %.2f KM, %.0f mins away)", distKm, timeMin);

                    Map<String, Object> resp = new HashMap<>();
                    resp.put("text", "The requested time slot is fully booked at nearby stations. However, " + altMatchStation.getName() + distStr + " is AVAILABLE later from " + altStartStr + " to " + altEndStr + ".\nWould you like to book this alternative slot instead?");
                    resp.put("stations", stationsData);
                    resp.put("foundAvailable", true);
                    ToolResultHolder.set(new ToolResultHolder.ToolResult(null, null, stationsData));
                    return mapper.writeValueAsString(resp);
                }

                Map<String, Object> resp = new HashMap<>();
                resp.put("error", "Sorry, all nearby stations are fully booked for " + input.connectorType() + " connectors around that time.");
                resp.put("stations", stationsData);
                resp.put("foundAvailable", false);
                ToolResultHolder.set(new ToolResultHolder.ToolResult(null, null, stationsData));
                return mapper.writeValueAsString(resp);

            } catch (Exception e) {
                log.error("Error in findBestStation tool", e);
                return "{\"error\": \"Sorry, I encountered an error while searching for stations: " + e.getMessage() + "\"}";
            }

    }

    public record CreateBookingInput(
        String stationId,
        String date,
        String startTime,
        String endTime,
        String connectorType
    ) {}

    @Tool
    @Description("Creates a formal booking in the system after the user confirms a specific slot and station.")
    public String createBooking(@ToolParam(description = "input for creating the desired booking") CreateBookingInput input) {

            try {
                log.info("Tool create_booking called with input: {}", input);
                UserContextHolder.UserContext uCtx = UserContextHolder.get();
                String userEmail = uCtx != null ? uCtx.email() : null;

                if (userEmail == null) {
                    return "{\"error\": \"User not authenticated.\" }";
                }

                Optional<EvUser> euOpt = evUserRepository.findByEmail(userEmail);
                if (euOpt.isEmpty()) {
                    return "{\"error\": \"EV User profile not found.\" }";
                }
                EvUser evUser = euOpt.get();

                Optional<Station> stOpt = stationRepository.findById(Long.parseLong(input.stationId()));
                if (stOpt.isEmpty()) {
                    return "{\"error\": \"Station not found.\" }";
                }
                Station station = stOpt.get();

                String effectiveEndTime = (input.endTime() != null && !input.endTime().trim().isEmpty())
                        ? input.endTime()
                        : minutesToTime(timeToMinutes(input.startTime()) + 60);

                LocalDate bookingDate = LocalDate.now(IST);
                if (input.date() != null && !input.date().trim().isEmpty() && !input.date().equalsIgnoreCase("today")) {
                    try {
                        bookingDate = LocalDate.parse(input.date().trim());
                    } catch (Exception ignored) {}
                }

                LocalDate today = LocalDate.now(IST);
                LocalTime nowTime = LocalTime.now(IST);
                int currentMinutes = nowTime.getHour() * 60 + nowTime.getMinute();

                if (bookingDate.isBefore(today)) {
                    return "{\"error\": \"Cannot book for a past date.\" }";
                }
                if (bookingDate.equals(today) && timeToMinutes(input.startTime()) <= currentMinutes) {
                    return "{\"error\": \"Cannot book a time slot in the past for today.\" }";
                }

                int reqStart = timeToMinutes(input.startTime());
                int reqEnd = timeToMinutes(effectiveEndTime);
                int durationMinutes = reqEnd - reqStart;

                if (reqEnd >= 24 * 60) {
                    return "{\"error\": \"Booking duration cannot cross midnight. Please choose an earlier start time.\" }";
                }
                if (durationMinutes < 60) {
                    return "{\"error\": \"Booking duration cannot be less than 1 hour.\" }";
                }

                int totalPorts = station.getChargersCount() != null ? station.getChargersCount() : 4;
                double pricePerKWh = 15.0; // fallback standard price
                double durationHours = (double) durationMinutes / 60.0;
                double estimatedKWh = Math.round((50.0 * durationHours) * 100.0) / 100.0; // 50kW charging speed
                double totalCost = Math.round((estimatedKWh * pricePerKWh) * 100.0) / 100.0;
                double platformFee = Math.round((totalCost * 0.1) * 100.0) / 100.0; // 10% platform fee
                double grandTotal = Math.round((totalCost + platformFee) * 100.0) / 100.0;

                Instant startInstant = ZonedDateTime.of(bookingDate, LocalTime.parse(input.startTime()), IST).toInstant();
                Instant endInstant = ZonedDateTime.of(bookingDate, LocalTime.parse(effectiveEndTime), IST).toInstant();

                List<Booking> stBookings = bookingRepository.findAll();
                List<Booking> activeBookings = new ArrayList<>();
                for (Booking b : stBookings) {
                    if (b.getStation().getId().equals(station.getId()) &&
                        (b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.IN_PROGRESS ||
                         (b.getStatus() == BookingStatus.PENDING && b.getCreatedAt().isAfter(Instant.now().minusSeconds(600))))) {
                        activeBookings.add(b);
                    }
                }

                AvailabilityResult avResult = checkAvailability(station.getId(), bookingDate, input.startTime(), effectiveEndTime, totalPorts, activeBookings);
                if (!avResult.available()) {
                    return "{\"error\": \"Conflict detected: This slot is no longer available. Please try another time.\" }";
                }

                Vehicle vehicle = null;
                List<Vehicle> vehicles = vehicleRepository.findByOwnerId(evUser.getId());
                if (!vehicles.isEmpty()) {
                    vehicle = vehicles.get(0);
                }

                Booking booking = Booking.builder()
                        .user(evUser)
                        .station(station)
                        .vehicle(vehicle)
                        .startTime(startInstant)
                        .endTime(endInstant)
                        .status(BookingStatus.PENDING)
                        .createdAt(Instant.now())
                        .build();

                bookingRepository.save(booking);

                Map<String, Object> resp = new HashMap<>();
                resp.put("success", true);
                resp.put("bookingId", booking.getId().toString());
                resp.put("message", "Booking is pending. User must pay advance within 10 minutes.");
                
                ToolResultHolder.set(new ToolResultHolder.ToolResult(booking.getId().toString(), true, null));
                return new ObjectMapper().writeValueAsString(resp);

            } catch (Exception e) {
                log.error("Error in createBooking tool", e);
                return "{\"error\": \"Failed to create booking: " + e.getMessage() + "\"}";
            }
    }
}
