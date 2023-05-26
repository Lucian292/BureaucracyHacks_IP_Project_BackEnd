package com.bureaucracyhacks.refactorip.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="feedback")
public class ReviewJPA
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedback_id;
    private Integer rating;
    private String comment;
    private String created_at;
    private Long user_id;
    private Long institution_id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users",
            joinColumns = @JoinColumn(name = "feedback_id", referencedColumnName = "feedback_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    private UserJPA users;

}


