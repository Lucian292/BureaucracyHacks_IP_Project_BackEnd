package com.bureaucracyhacks.refactorip.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class RoleJPA implements GrantedAuthority
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Column(length = 60)
    private String name;

    @Override
    public String getAuthority()
    {
        return name;
    }

}
