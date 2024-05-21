package org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table(name = "recetas")
@Entity
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaHora;
    private Long idMedicamento;
    private String dosis;
    private String frecuencia;
    private Integer duracion;
    private String instrucciones;
    @ManyToOne
    @JoinColumn(name = "id_diagnostico", referencedColumnName = "id")
    private Diagnostico diagnostico;
}

