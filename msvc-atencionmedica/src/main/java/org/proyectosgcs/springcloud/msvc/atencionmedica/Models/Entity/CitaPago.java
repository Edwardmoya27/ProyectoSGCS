    package org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity;

    import jakarta.persistence.*;
    import lombok.Data;
    import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Pago;

    /**
     * @file: CitaPago
     * @author: EdwarMoya
     * @created: 08/05/2024
     * @HoraCreated: 06:12 p. m.
     */
    @Data
    @Entity
    public class CitaPago {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "cita_id", referencedColumnName = "id")
        private Cita cita;

        @OneToOne
        @JoinColumn(name = "pago_id", referencedColumnName = "id")
        private Pago pago;

    }
