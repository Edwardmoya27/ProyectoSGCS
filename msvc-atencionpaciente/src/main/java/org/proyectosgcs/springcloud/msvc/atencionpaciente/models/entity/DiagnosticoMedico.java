package org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "diagnosticos_medicos")
public class DiagnosticoMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_paciente")
    private Long idPaciente;

    @NotNull
    @Column(name = "id_medico")
    private Long idMedico;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_diagnostico")
    private Date fechaDiagnostico;

    @NotNull
    @Size(min = 10, max = 500)
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 1000)
    @Column(name = "observaciones")
    private String observaciones;

    @OneToOne(mappedBy = "diagnosticoMedico")
    private RecetaMedica recetaMedica;

}

