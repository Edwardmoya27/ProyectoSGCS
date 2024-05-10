package org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Date;

/**
 * @file: Paciente
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 03:58 p. m.
 */
@Data
@Entity
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Los nombres no pueden estar en blanco")
    private String nombres;

    @NotBlank(message = "Los apellidos no pueden estar en blanco")
    private String apellidos;

    @NotNull(message = "El DNI no puede estar en blanco")
    @Positive(message = "El DNI debe ser un número positivo")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    @Column(unique = true)
    private String dni;

    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private Date fechaNacimiento;

    @NotBlank(message = "El género no puede estar en blanco")
    private String genero;

    @NotBlank(message = "La dirección no puede estar en blanco")
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar en blanco")
    @Pattern(regexp = "\\d{9}", message = "El número de teléfono debe tener 9 dígitos")
    private String telefono;

    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "El formato del email no es válido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "El seguro médico no puede estar en blanco")
    private String seguroMedico;

}
