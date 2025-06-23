package br.com.wise.controller.mapper;

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
        return Endereco.builder()
                .rua(request.rua())
                .numero(request.numero())
                .cep(request.cep())
                .cidade(request.cidade())
                .uf(request.uf())
                .build();
    }

    private static EnderecoResponse toEnderecoResponse(Endereco endereco) {
        return EnderecoResponse.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .build();
    }

    public ClienteEntity toEntity(Cliente cliente) {
        if (cliente == null) return null;

        return ClienteEntity.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .dataNascimento(cliente.getDataNascimento())
                .endereco(toEmbeddable(cliente.getEndereco()))
                .build();
    }

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;

        return Cliente.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .cpf(entity.getCpf())
                .dataNascimento(entity.getDataNascimento())
                .endereco(toDomainEndereco(entity.getEndereco()))
                .build();
    }
    public static ClienteResponse toResponse(Cliente cliente) {
        if (cliente == null) return null;

        return ClienteResponse.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .dataNascimento(cliente.getDataNascimento())
                .endereco(toEnderecoResponse(cliente.getEndereco()))
                .build();
    }

    private EnderecoEmbeddable toEmbeddable(Endereco endereco) {
        if (endereco == null) return null;

        return EnderecoEmbeddable.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .build();
    }

    private Endereco toDomainEndereco(EnderecoEmbeddable embeddable) {
        if (embeddable == null) return null;

        return Endereco.builder()
                .rua(embeddable.getRua())
                .numero(embeddable.getNumero())
                .cep(embeddable.getCep())
                .cidade(embeddable.getCidade())
                .uf(embeddable.getUf())
                .build();
    }
}
