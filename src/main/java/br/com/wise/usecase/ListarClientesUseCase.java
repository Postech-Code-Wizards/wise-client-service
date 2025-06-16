package br.com.wise.usecase;

import br.com.wise.domain.model.Cliente;
import br.com.wise.gateway.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ListarClientesUseCase {

    private final ClienteGateway clienteGateway;

    public List<Cliente> executar() {
        return clienteGateway.listarTodos();
    }
}
