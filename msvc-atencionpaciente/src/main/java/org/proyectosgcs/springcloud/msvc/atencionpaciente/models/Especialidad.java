package org.proyectosgcs.springcloud.msvc.atencionpaciente.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

}
