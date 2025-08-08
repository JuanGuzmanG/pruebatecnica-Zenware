package io.zemware;

import io.quarkus.test.junit.QuarkusTest;
import io.zemware.Entity.Producto;
import org.junit.jupiter.api.Test;

import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class ProductTest {
    @Test
    void aplicarImpuesto_exitoso_sumaImpuesto() {
        Producto p = new Producto("Monitor", 100.0);

        // Cuando: 10% de 100 = 10; nuevo precio = 110
        double nuevoPrecio = p.aplicarImpuesto(10.0);

        assertEquals(110.0, nuevoPrecio, 1e-6);
        assertEquals(110.0, p.getPrecio(), 1e-6);
    }

    @Test
    void aplicarImpuesto_fueraDeRango_lanzaExcepcion() {
        Producto p = new Producto("Monitor", 100.0);

        // Cuando: porcentaje > 50 debe lanzar IllegalArgumentException
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> p.aplicarImpuesto(60.0));
        assertTrue(ex.getMessage().contains("entre 0 y 50"));
    }

}