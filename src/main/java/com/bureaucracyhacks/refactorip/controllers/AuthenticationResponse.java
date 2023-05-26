package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.models.UserJPA;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private UserJPA user;
}
