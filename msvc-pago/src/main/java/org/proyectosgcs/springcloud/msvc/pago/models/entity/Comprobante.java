package org.proyectosgcs.springcloud.msvc.pago.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @file: Comprobante
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:49 p. m.
 */
@Data
@Entity
public class Comprobante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idPago;
    private LocalDateTime fechaEmision;
    private BigDecimal monto;

    @OneToOne
    @JoinColumn(name = "pago_id", referencedColumnName = "id")
    private Pago pago;

}
