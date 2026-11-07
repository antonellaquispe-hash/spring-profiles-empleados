package com.techsolutions.empleados.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ConfiguracionController {
    private final Environment environment;

    @Value("${empresa.ambiente}")
    private String ambiente;

    @Value("${server.port}")
    private String puerto;

    @Value("${spring.datasource.url}")
    private String urlBaseDatos;

    public ConfiguracionController(Environment environment) { this.environment = environment; }

    @GetMapping("/configuracion")
    public Map<String, String> obtenerConfiguracion() {
        String[] perfiles = environment.getActiveProfiles();
        String perfil = perfiles.length > 0 ? perfiles[0] : environment.getProperty("spring.profiles.active", "default");
        String baseDatos = extraerBaseDatos(urlBaseDatos);
        Map<String, String> respuesta = new LinkedHashMap<>();
        respuesta.put("ambiente", ambiente);
        respuesta.put("perfil", perfil);
        respuesta.put("puerto", puerto);
        respuesta.put("baseDatos", baseDatos);
        return respuesta;
    }

    private String extraerBaseDatos(String url) {
        if (url == null) return "desconocida";
        int inicio = url.lastIndexOf('/') + 1;
        int fin = url.indexOf('?', inicio);
        return fin >= 0 ? url.substring(inicio, fin) : url.substring(inicio);
    }
}
