package com.techsolutions.empleados.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombres;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String apellidos;

    @NotBlank
    @Email
    @Column(nullable = false, length = 100)
    private String email;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sueldo;

    public Empleado() { }

    public Empleado(Long id, String nombres, String apellidos, String email, BigDecimal sueldo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.sueldo = sueldo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public BigDecimal getSueldo() { return sueldo; }
    public void setSueldo(BigDecimal sueldo) { this.sueldo = sueldo; }
}
