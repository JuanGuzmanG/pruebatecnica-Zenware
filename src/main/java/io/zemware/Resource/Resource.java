package io.zemware.Resource;

import io.zemware.Resource.DTO.ProductoRequestDTO;
import io.zemware.Entity.Producto;
import io.zemware.Service.ProductoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Resource {

    @Inject
    ProductoService productoService;

    // GET /productos
    @GET
    public List<Producto> lista(){
        return productoService.listaProductos();
    }

    // POST /productos
    @POST
    public Response crear(@Valid ProductoRequestDTO request) {
        Producto nuevo = productoService.crear(request.getNombre(), request.getPrecio());

        return Response
                .created(URI.create("/productos/" + nuevo.id))
                .entity(nuevo)
                .build();
    }

    // GET /productos/{id}
    @GET
    @Path("/{id}")
    public Producto obtenerPorId(@PathParam("id") Long id){
        return productoService.obtenerProductoPorId(id)
                .orElseThrow(NotFoundException::new);
    }

    // DELETE /productos/{id}
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = productoService.eliminar(id);
        if (!eliminado) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }

}
