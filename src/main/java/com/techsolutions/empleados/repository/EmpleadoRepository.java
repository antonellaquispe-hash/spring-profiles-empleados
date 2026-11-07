package com.techsolutions.empleados.repository;

import com.techsolutions.empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> { }
