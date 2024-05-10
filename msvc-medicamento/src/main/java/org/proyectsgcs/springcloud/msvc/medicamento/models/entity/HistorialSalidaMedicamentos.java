package org.proyectsgcs.springcloud.msvc.medicamento.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
@Table(name = "historial_pedidos")
public class HistorialSalidaMedicamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @ManyToOne
    private Medicamento medicamento;
    @Positive
    @NotEmpty
    private int cantidadSolicitada;
    @NotEmpty
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPedido;
    @NotEmpty
    private String estadoPedido;
    @NotEmpty
    @ManyToOne
    private Proveedor proveedor;

    //@ManyToMany
    //private Medico medicoSolicitante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
