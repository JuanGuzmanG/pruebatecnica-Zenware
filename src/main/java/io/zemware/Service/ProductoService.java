package io.zemware.Service;

import io.zemware.Entity.Producto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductoService {

    public List<Producto> listaProductos() {
        return Producto.listAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return Producto.findByIdOptional(id);
    }

    @Transactional
    public Producto crear(String nombre, double precio) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.persist();
        return producto;
    }

    @Transactional
    public boolean eliminar(Long id){
        return Producto.deleteById(id);
    }
}
