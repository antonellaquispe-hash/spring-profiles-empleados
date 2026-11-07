package com.techsolutions.empleados.controller;

import com.techsolutions.empleados.model.Empleado;
import com.techsolutions.empleados.service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Empleado> crear(@Valid @RequestBody Empleado empleado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(empleado));
    }

    @GetMapping
    public List<Empleado> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Empleado obtener(@PathVariable Long id) { return service.obtener(id); }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Long id, @Valid @RequestBody Empleado empleado) {
        return service.actualizar(id, empleado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
