package br.com.wise.usecase;

import br.com.wise.domain.model.Cliente;
import br.com.wise.gateway.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class CriarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public Cliente executar(Cliente cliente) {
        log.info("Criando cliente com CPF {}", cliente.cpf());

        if (clienteGateway.buscarPorCpf(cliente.cpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente com o CPF informado.");
        }

        return clienteGateway.salvar(cliente);
    }
}