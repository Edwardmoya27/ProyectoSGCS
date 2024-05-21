package org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Table(name = "diagnosticos")
@Entity
public class Diagnostico{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaHora;
    private String descripcion;
    private String observaciones;
}

