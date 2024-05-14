package org.proyectosgcs.springcloud.msvc.atencionmedica.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombres;
    private String apellidos;
    private Date fechaNacimiento;
    private Especialidad especialidad;
}
