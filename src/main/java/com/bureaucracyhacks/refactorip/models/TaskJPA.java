package com.bureaucracyhacks.refactorip.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * This class defines the model for a Task entity
 */
@Data
@Entity
@Table(name = "tasks")
public class TaskJPA
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Long id;
    private String name;
    private String description;
    private String estimated_time;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "task_documents",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "document_id")
    )
    private List<DocumentJPA> documents;
}
