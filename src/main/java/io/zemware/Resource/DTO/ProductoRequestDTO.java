package io.zemware.Resource.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductoRequestDTO{
        @NotBlank(message = "El nombre no puede estar vacío")
        private String nombre;

        @PositiveOrZero(message = "El precio debe ser positivo o cero")
        private double precio;

        public ProductoRequestDTO() {
        }

        public ProductoRequestDTO(String nombre, double precio) {
                this.nombre = nombre;
                this.precio = precio;
        }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public double getPrecio() { return precio; }
        public void setPrecio(double precio) { this.precio = precio; }

}