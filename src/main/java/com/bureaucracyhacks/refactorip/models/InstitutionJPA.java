package com.bureaucracyhacks.refactorip.models;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "institution", uniqueConstraints = {
        @UniqueConstraint(columnNames = "institution_id"),
        @UniqueConstraint(columnNames = "phone_number"),
})
public class InstitutionJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institution_id")
    private Long institutionId;

    private String name;
    private String address;
    private String phone_number;
    private String website;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;

}
