package org.proyectosgcs.springcloud.msvc.atencionpaciente.modules.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "recetas_medicas")
public class RecetaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del paciente es obligatorio")
    @Column(name = "id_paciente")
    private Long idPaciente;

    @NotNull(message = "El ID del médico es obligatorio")
    @Column(name = "id_medico")
    private Long idMedico;

    @NotNull(message = "La fecha de prescripción es obligatoria")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_prescripcion")
    private Date fechaPrescripcion;

    @NotBlank(message = "El nombre del medicamento es obligatorio")
    @Column(name = "nombre_medicamento")
    private String nombreMedicamento;

    @NotBlank(message = "La dosis es obligatoria")
    @Column(name = "dosis")
    private String dosis;

    @NotBlank(message = "La frecuencia es obligatoria")
    @Column(name = "frecuencia")
    private String frecuencia;

    @NotNull(message = "La duración del tratamiento es obligatoria")
    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "instrucciones_adicionales")
    private String instruccionesAdicionales;

    @ManyToOne
    @JoinColumn(name = "diagnostico_id", referencedColumnName = "id")
    private DiagnosticoMedico diagnosticoMedico;

}

