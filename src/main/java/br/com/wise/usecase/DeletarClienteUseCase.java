package br.com.wise.usecase;

import br.com.wise.gateway.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class DeletarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public boolean executar(Long id) {
        return clienteGateway.buscarPorId(id)
                .map(cliente -> {
                    clienteGateway.deletar(cliente);
                    return true;
                })
                .orElse(false);
    }
}
