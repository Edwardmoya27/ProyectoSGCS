package org.proyectosgcs.springcloud.msvc.atencionmedica.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.CitaPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @file: Pago
 * @author: EdwarMoya
 * @created: 08/05/2024
 * @HoraCreated: 06:11 p. m.
 */
@Data
@Entity
public class Pago {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Long idPaciente;
        private BigDecimal monto;
        private LocalDateTime fechaPago;
        private String metodoPago;

        @OneToOne(mappedBy = "pago")
        private CitaPago citaPago;
        
}
