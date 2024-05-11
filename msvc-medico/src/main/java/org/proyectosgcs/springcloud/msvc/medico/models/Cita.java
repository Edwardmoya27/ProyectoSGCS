package org.proyectosgcs.springcloud.msvc.medico.models;

import jakarta.persistence.*;
import lombok.Data;
import org.proyectosgcs.springcloud.msvc.medico.models.entity.Medico;
import java.time.LocalDateTime;

@Data
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHora;
    private Paciente paciente;
    private String medico;
    private String motivo;
    private String estado;

}
