package io.zemware.Exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {

    public static class ErrorResponse {
        private int code;
        private String message;

        public ErrorResponse() {
        }

        public ErrorResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @Override
    public Response toResponse(NotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse(404, "No encontrado"))
                .build();
    }
}