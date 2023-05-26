package com.bureaucracyhacks.refactorip.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "user_documents")
@Entity
public class UserDocumentsJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupID;

    private int user_id;

    private int document_id;

    private int task_id;

    private String status;
}
