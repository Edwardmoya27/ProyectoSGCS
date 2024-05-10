package org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @file: HistoriaMedica
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 04:09 p. m.
 */
@Data
@Entity
public class HistoriaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> alergias = new ArrayList<>();

    private List<String> enfermedadesCronicas = new ArrayList<>();

    private List<String> cirugiasPrevias = new ArrayList<>();

    private List<String> hospitalizacionesPrevias = new ArrayList<>();

    private List<String> medicamentosActuales = new ArrayList<>();

    private List<String> resultadosExamenesPrevios = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    @NotNull(message = "El ID del paciente es obligatorio")
    private Paciente paciente;
}
