package com.bureaucracyhacks.refactorip.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email"),

})
public class UserJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String phone_number;
    private String created_at;
    private String city;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<RoleJPA> roles;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private RoleJPA role;

    public void setRole(RoleJPA role) {
        roles.clear();
        roles.add(role);
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_documents",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "document_id")
    )
    private Set<DocumentJPA> documents;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_documents",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "groupID", referencedColumnName = "groupID"),
                    @JoinColumn(name = "document_id", referencedColumnName = "document_id"),
                    @JoinColumn(name = "task_id", referencedColumnName = "task_id"),
                    @JoinColumn(name = "status", referencedColumnName = "status")
            }
    )
    private List<UserDocumentsJPA> userDocumentInfo;


    //join table for user and tasks
    //for retrieving the tasks of a specific user
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_tasks",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    )
    private List<TaskJPA> tasks;


}
