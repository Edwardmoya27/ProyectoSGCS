package org.proyectosgcs.springcloud.msvc.medico.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class CitaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Cita cita;
    private Pago pago;

}
