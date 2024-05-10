package org.proyectosgcs.springcloud.msvc.atencionpaciente.modules.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

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

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

