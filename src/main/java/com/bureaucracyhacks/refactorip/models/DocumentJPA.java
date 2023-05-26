package com.bureaucracyhacks.refactorip.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "documents", uniqueConstraints = {
        @UniqueConstraint(columnNames = "document_id")
})
public class DocumentJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int document_id;
    private String name;
    private String description;
    private String price;
    private int institution_id;
    private String path;
    @Lob
    @Column(name="file")
    private byte[] file;


    //join table for user and user_documents
    //usable for actions on user_documents table
    //you can retrieve specific columns from user_documents table
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_documents",
            joinColumns = @JoinColumn(name = "document_id", referencedColumnName = "document_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "groupID", referencedColumnName = "groupID"),
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                    @JoinColumn(name = "task_id", referencedColumnName = "task_id"),
                    @JoinColumn(name = "status", referencedColumnName = "status")
            }
    )
    private UserDocumentsJPA userDocumentInfo;
}
