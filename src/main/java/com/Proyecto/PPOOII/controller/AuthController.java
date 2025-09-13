package com.Proyecto.PPOOII.controller;

import com.Proyecto.PPOOII.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // ⚠️ Aquí deberías validar con tu tabla usuario
        // Por ahora, asumimos que si existe devuelve un token
        return authService.generarToken(username);
    }
}

