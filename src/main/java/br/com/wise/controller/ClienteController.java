package br.com.wise.controller;


import br.com.wise.controller.dto.request.ClienteRequest;
import br.com.wise.controller.mapper.ClienteMapper;
import br.com.wise.domain.model.Cliente;
import br.com.wise.usecase.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteController {

    private final CriarClienteUseCase criarClienteUseCase;
    private final BuscarClientePorCpfUseCase buscarClientePorCpfUseCase;
    private final BuscarClientePorIdUseCase buscarClientePorIdUseCase;
    private final ListarClientesUseCase listarClientesUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;
    private final DeletarClienteUseCase deletarClienteUseCase;
    private final ClienteMapper mapper;

    @Inject
    public ClienteController(
            CriarClienteUseCase criarClienteUseCase,
            BuscarClientePorCpfUseCase buscarClientePorCpfUseCase,
            BuscarClientePorIdUseCase buscarClientePorIdUseCase,
            ListarClientesUseCase listarClientesUseCase,
            AtualizarClienteUseCase atualizarClienteUseCase,
            DeletarClienteUseCase deletarClienteUseCase,
            ClienteMapper mapper
    ) {
        this.criarClienteUseCase = criarClienteUseCase;
        this.buscarClientePorCpfUseCase = buscarClientePorCpfUseCase;
        this.buscarClientePorIdUseCase = buscarClientePorIdUseCase;
        this.listarClientesUseCase = listarClientesUseCase;
        this.atualizarClienteUseCase = atualizarClienteUseCase;
        this.deletarClienteUseCase = deletarClienteUseCase;
        this.mapper = mapper;
    }

    @POST
    public Response criarCliente(ClienteRequest request) {
        var cliente = mapper.toDomain(request);
        var clienteCriado = criarClienteUseCase.executar(cliente);
        return Response.created(URI.create("/clientes/" + clienteCriado.id()))
                .entity(ClienteMapper.toResponse(clienteCriado))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return buscarClientePorIdUseCase.executar(id)
                .map(cliente -> Response.ok(ClienteMapper.toResponse(cliente)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        return buscarClientePorCpfUseCase.executar(cpf)
                .map(cliente -> Response.ok(ClienteMapper.toResponse(cliente)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response listarClientes() {
        var clientes = listarClientesUseCase.executar();
        var responses = clientes.stream()
                .map(ClienteMapper::toResponse)
                .toList();
        return Response.ok(responses).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        var deletado = deletarClienteUseCase.executar(id);
        return deletado
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, ClienteRequest request) {
        Cliente cliente = mapper.toDomain(request);
        return atualizarClienteUseCase.executar(id, cliente)
                .map(c -> Response.ok(ClienteMapper.toResponse(c)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

}
