package org.proyectosgcs.springcloud.msvc.atencionpaciente.modules.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

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

    // Getters y setters

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

    public Date getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(Date fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getInstruccionesAdicionales() {
        return instruccionesAdicionales;
    }

    public void setInstruccionesAdicionales(String instruccionesAdicionales) {
        this.instruccionesAdicionales = instruccionesAdicionales;
    }

    public Optional<ResponseEntity<Object>> map(Object o) {
        return Optional.empty();
    }
}

