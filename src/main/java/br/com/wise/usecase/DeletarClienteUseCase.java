package br.com.wise.usecase;

import br.com.wise.domain.model.Cliente;
import br.com.wise.gateway.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class DeletarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public void executar(Cliente cliente) {
        clienteGateway.deletar(cliente);
    }
}
