package io.zemware.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Producto extends PanacheEntity {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @PositiveOrZero(message = "El precio debe ser positivo o cero")
    private double precio;

    public double aplicarImpuesto(double procentaje){
        if(procentaje < 0 || procentaje > 50) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 50");
        }
        double impuesto = this.precio * (procentaje / 100.0);
        this.precio += impuesto;
        return this.precio;
    }

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
    public Producto() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}

