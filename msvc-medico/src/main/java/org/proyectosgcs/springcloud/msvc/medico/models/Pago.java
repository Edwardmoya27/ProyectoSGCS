package org.proyectosgcs.springcloud.msvc.medico.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Pago {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Long idPaciente;
        private BigDecimal monto;
        private LocalDateTime fechaPago;
        private String metodoPago;
        private CitaPago citaPago;
}
