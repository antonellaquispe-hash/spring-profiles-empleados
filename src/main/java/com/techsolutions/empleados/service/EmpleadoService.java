package com.techsolutions.empleados.service;

import com.techsolutions.empleados.model.Empleado;
import com.techsolutions.empleados.repository.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmpleadoService {
    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) { this.repository = repository; }

    public Empleado crear(Empleado empleado) {
        empleado.setId(null);
        return repository.save(empleado);
    }

    public List<Empleado> listar() { return repository.findAll(); }

    public Empleado obtener(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado: " + id));
    }

    public Empleado actualizar(Long id, Empleado datos) {
        Empleado actual = obtener(id);
        actual.setNombres(datos.getNombres());
        actual.setApellidos(datos.getApellidos());
        actual.setEmail(datos.getEmail());
        actual.setSueldo(datos.getSueldo());
        return repository.save(actual);
    }

    public void eliminar(Long id) {
        Empleado actual = obtener(id);
        repository.delete(actual);
    }
}
