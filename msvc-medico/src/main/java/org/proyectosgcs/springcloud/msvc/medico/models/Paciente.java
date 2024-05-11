package org.proyectosgcs.springcloud.msvc.medico.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private Date fechaNacimiento;
    private String genero;
    private String direccion;
    private String telefono;
    private String email;
    private String seguroMedico;
}
