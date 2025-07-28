package br.com.wise.controller.mapper;

import br.com.wise.controller.dto.request.ClienteRequest;
import br.com.wise.controller.dto.request.EnderecoRequest;
import br.com.wise.controller.dto.response.ClienteResponse;
import br.com.wise.controller.dto.response.EnderecoResponse;
import br.com.wise.domain.model.Cliente;
import br.com.wise.domain.model.Endereco;
import br.com.wise.gateway.database.jpa.entity.ClienteEntity;
import br.com.wise.gateway.database.jpa.entity.EnderecoEmbeddable;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteMapper {

    public static Endereco toEnderecoDomain(EnderecoRequest request) {
        if (request == null) return null;

        return new Endereco(
                request.rua(),
                request.numero(),
                request.cep(),
                request.cidade(),
                request.uf()
        );
    }

    private static EnderecoResponse toEnderecoResponse(Endereco endereco) {
        if (endereco == null) return null;

        return new EnderecoResponse(
                endereco.rua(),
                endereco.numero(),
                endereco.cep(),
                endereco.cidade(),
                endereco.uf()
        );
    }

    public static ClienteResponse toResponse(Cliente cliente) {
        if (cliente == null) return null;

        return new ClienteResponse(
                cliente.id(),
                cliente.nome(),
                cliente.cpf(),
                cliente.dataNascimento(),
                toEnderecoResponse(cliente.endereco())
        );
    }

    public ClienteEntity toEntity(Cliente cliente) {
        if (cliente == null) return null;

        return new ClienteEntity(
                cliente.id(),
                cliente.nome(),
                cliente.cpf(),
                cliente.dataNascimento(),
                toEmbeddable(cliente.endereco())
        );
    }

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;

        return new Cliente(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getDataNascimento(),
                toDomainEndereco(entity.getEndereco())
        );
    }

    private EnderecoEmbeddable toEmbeddable(Endereco endereco) {
        if (endereco == null) return null;

        return new EnderecoEmbeddable(
                endereco.rua(),
                endereco.numero(),
                endereco.cep(),
                endereco.cidade(),
                endereco.uf()
        );
    }

    private Endereco toDomainEndereco(EnderecoEmbeddable embeddable) {
        if (embeddable == null) return null;

        return new Endereco(
                embeddable.getRua(),
                embeddable.getNumero(),
                embeddable.getCep(),
                embeddable.getCidade(),
                embeddable.getUf()
        );
    }

    public Cliente toDomain(ClienteRequest request) {
        if (request == null) return null;

        return new Cliente(
                null,
                request.nome(),
                request.cpf(),
                request.dataNascimento(),
                toEnderecoDomain(request.endereco())
        );
    }
}

