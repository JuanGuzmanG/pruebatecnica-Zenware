# Catálogo de Productos – Quarkus 3 (Java 17)

Microservicio REST para gestionar un catálogo de productos.  
Persistencia en memoria con H2, ORM con Panache, validaciones Bean Validation y manejo uniforme de errores.  
Incluye pruebas unitarias y de API.

| Stack | Detalle |
| ----- | ------- |
| Lenguaje | Java 17 (compatible 8+) |
| Framework | Quarkus 3.x |
| Persistencia | H2 in-memory + Hibernate ORM with Panache |
| API/JSON | Quarkus REST + Jackson |
| Validación | Hibernate Validator |
| Tests | JUnit 5 + Rest-Assured |

---

## 1 · Estructura del proyecto

src/
├── main/
│ ├── java/io/zemware/
│ │ ├── Entity/Producto.java
│ │ ├── Controller/
│ │ │ ├── Resource.java
│ │ │ └── DTO/ProductoRequestDTO.java
│ │ ├── Service/ProductoService.java
│ │ └── Exception/
│ │ ├── NotFoundMapper.java
│ │ └── ValidationMapper.java
│ └── resources/application.properties
├── test/
│ ├── java/io/zemware/Entity/ProductoTest.java
│ └── java/io/zemware/Controller/ResourceApiTest.java
logs/diagnostico.log
pom.xml
README.md


---

## 2 · Configuración

`src/main/resources/application.properties`  

quarkus.datasource.db-kind=h2
quarkus.datasource.jdbc.url=jdbc:h2:mem:default;DB_CLOSE_DELAY=-1
quarkus.datasource.username=test
quarkus.datasource.password=test
quarkus.hibernate-orm.database.generation=drop-and-create

---

## 3 · Ejecutar en modo dev

git clone <URL_DEL_REPO>
cd <PROYECTO>
mvn quarkus:dev

Comprobación de salud:

curl -i http://localhost:8080/q/health

---

## 4 · Endpoints

| Método | Ruta | Descripción |
| ------ | ---- | ----------- |
| GET    | /productos | Lista todos |
| POST   | /productos | Crea uno |
| GET    | /productos/{id} | Devuelve uno o 404 |
| DELETE | /productos/{id} | Elimina o 404 |

Respuesta 404 uniforme  
{ "code": 404, "message": "No encontrado" }

---

## 5 · Ejemplos curl

Crear
curl -i -X POST http://localhost:8080/productos
-H 'Content-Type: application/json'
-d '{
"nombre":"monitor",
"precio":1200.0
}'

Listar
curl -i http://localhost:8080/productos

Obtener (id=1)
curl -i http://localhost:8080/productos/1

Obtener inexistente
curl -i http://localhost:8080/productos/999

Eliminar (id=1)
curl -i -X DELETE http://localhost:8080/productos/1

---

## 6 · Tests

mvn clean verify # ejecuta unitarios + API

| Tipo | Clase | Qué cubre |
| ---- | ----- | --------- |
| Unitario | `ProductoTest` | `aplicarImpuesto` OK y excepción |
| API | `ResourceApiTest` | POST+GET felices y GET 404 |

---

## 7 · Respuestas conceptuales

### 7.1 Logs
*Diagnóstico (extracto en logs/diagnostico.log)*

1. **Causa raíz** – `NumberFormatException`: se intentó parsear `"abc"` a entero en un job del scheduler.
2. **Prevención** – validar formato con `@Pattern("\\d+")` o `@Digits`, y/o parsear con try/catch seguro; fallar rápido si la fuente es config.
3. **Nivel de log** – • Entrada externa mala ⇒ `WARN`  
   • Config interna rota ⇒ `ERROR` (con stacktrace).

### 7.2 AWS

*EC2 vs Lambda*  
• EC2: procesos largos, control total del SO, cargas con estado.  
• Lambda: eventos breves, escalado automático, pago por uso, stateless.

*Observabilidad*
- **CloudWatch** – logs, métricas, alarmas.
- **X-Ray** – trazas distribuidas, latencia.
- **CloudTrail** – auditoría de llamadas AWS API.

### 7.3 SQL

-- Top 5 productos > 100 000 (desc)
SELECT id, nombre, precio
FROM productos
WHERE precio > 100000
ORDER BY precio DESC
LIMIT 5;

