package com.voltx.evgenee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "stations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer chargersCount;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private StationOwner owner;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
