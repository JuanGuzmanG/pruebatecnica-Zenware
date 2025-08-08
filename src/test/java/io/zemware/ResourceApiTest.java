package io.zemware;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ResourceApiTest {
    @Test
    void crearYListar_ok() {
        given()
                .contentType("application/json")
                .body("{\"nombre\":\"monitor\",\"precio\":1200.0}")
                .when()
                .post("/productos")
                .then()
                .statusCode(201)
                .header("Location", containsString("/productos/"))
                .body("id", notNullValue())
                .body("nombre", is("monitor"))
                .body("precio", is(1200.0f));

        given()
                .when()
                .get("/productos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void obtener_404() {
        given()
                .when()
                .get("/productos/99999999")
                .then()
                .statusCode(404)
                .body("code", is(404))
                .body("message", is("No encontrado"));
    }
}
