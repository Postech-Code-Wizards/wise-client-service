package br.com.wise.usecase;

import br.com.wise.controller.dto.request.ClienteRequest;
import br.com.wise.domain.model.Cliente;
import br.com.wise.gateway.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import static br.com.wise.controller.mapper.ClienteMapper.toEnderecoDomain;

@ApplicationScoped
@RequiredArgsConstructor
public class AtualizarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public Cliente executar(Long id, ClienteRequest request) {
        Cliente existente = clienteGateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (clienteGateway.buscarPorCpf(request.cpf())
                .filter(c -> !c.getId().equals(id))
                .isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente com o CPF informado.");
        }

        existente.setNome(request.nome());
        existente.setCpf(request.cpf());
        existente.setDataNascimento(request.dataNascimento());
        existente.setEndereco(toEnderecoDomain(request.endereco()));

        return clienteGateway.atualizar(existente);
    }
}