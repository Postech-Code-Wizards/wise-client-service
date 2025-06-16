package br.com.wise.usecase;

import br.com.wise.domain.model.Cliente;
import br.com.wise.gateway.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class BuscarClientePorCpfUseCase {

    private final ClienteGateway clienteGateway;

    public Optional<Cliente> executar(String cpf) {
        return clienteGateway.buscarPorCpf(cpf);
    }
}