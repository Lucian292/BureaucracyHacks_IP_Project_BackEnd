package com.bureaucracyhacks.refactorip.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="doc_image")
public class DocumentImageJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    @Lob
    @Column(name = "image")
    private byte[] image;
    private int id_user;

}