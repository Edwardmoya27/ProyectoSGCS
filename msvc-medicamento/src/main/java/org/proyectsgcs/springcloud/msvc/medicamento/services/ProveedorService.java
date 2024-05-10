package org.proyectsgcs.springcloud.msvc.medicamento.services;

import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Proveedor;

import java.util.*;

public interface ProveedorService {
    List<Proveedor> listar();
    Optional<Proveedor> porId(Long id);
    Proveedor guardar(Proveedor medicamento);
    void eliminar(Long id);
}
