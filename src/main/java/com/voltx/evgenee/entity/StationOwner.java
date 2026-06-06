package com.voltx.evgenee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "station_owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User authUser;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Station> stations;
}
