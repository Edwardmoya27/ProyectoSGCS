package org.proyectosgcs.springcloud.msvc.pago.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @file: Paciente
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 03:58 p. m.
 */
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
