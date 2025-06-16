package br.com.wise.controller;


import br.com.wise.domain.model.Cliente;
import br.com.wise.usecase.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteController {

    @Inject
    CriarClienteUseCase criarClienteUseCase;

    @Inject
    BuscarClientePorCpfUseCase buscarClientePorCpfUseCase;

    @Inject
    BuscarClientePorIdUseCase buscarClientePorIdUseCase;

    @Inject
    ListarClientesUseCase listarClientesUseCase;

    @Inject
    DeletarClienteUseCase deletarClienteUseCase;

    @POST
    public Response criarCliente(Cliente cliente) {
        var clienteCriado = criarClienteUseCase.executar(cliente);
        return Response.created(URI.create("/clientes/" + clienteCriado.getId()))
                .entity(clienteCriado)
                .build();
    }

    @GET
    public List<Cliente> listarClientes() {
        return listarClientesUseCase.executar();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return buscarClientePorIdUseCase.executar(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        return buscarClientePorCpfUseCase.executar(cpf)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        return buscarClientePorIdUseCase.executar(id)
                .map(cliente -> {
                    deletarClienteUseCase.executar(cliente);
                    return Response.noContent().build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
