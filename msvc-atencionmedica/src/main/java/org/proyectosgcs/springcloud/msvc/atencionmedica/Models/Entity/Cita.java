package org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Medico;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Pago;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @file: Citas
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 04:10 p. m.
 */
@Data
@Entity
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha y hora no puede ser nula")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    private Paciente paciente;

    private Long medicoId;

    @NotBlank(message = "El motivo de la cita no puede estar en blanco")
    private String motivo;

    @NotBlank(message = "El estado de la cita no puede estar en blanco")
    private String estado;

    private Long pagoId;

}
