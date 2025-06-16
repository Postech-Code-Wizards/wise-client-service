package br.com.wise.controller.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", exception.getMessage());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(erro)
                .build();
    }
}