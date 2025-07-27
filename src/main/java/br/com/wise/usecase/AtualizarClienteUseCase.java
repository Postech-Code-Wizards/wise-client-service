package br.com.wise.usecase;

import br.com.wise.domain.model.Cliente;
import br.com.wise.gateway.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class AtualizarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public Optional<Cliente> executar(Long id, Cliente dadosAtualizados) {
        var clienteExistenteOpt = clienteGateway.buscarPorId(id);

        if (clienteExistenteOpt.isEmpty()) {
            return Optional.empty();
        }

        var cpfJaExiste = clienteGateway.buscarPorCpf(dadosAtualizados.cpf())
                .filter(c -> !c.id().equals(id))
                .isPresent();

        if (cpfJaExiste) {
            throw new IllegalArgumentException("Já existe um cliente com o CPF informado.");
        }

        var clienteAtualizado = new Cliente(
                id,
                dadosAtualizados.nome(),
                dadosAtualizados.cpf(),
                dadosAtualizados.dataNascimento(),
                dadosAtualizados.endereco()
        );

        return Optional.of(clienteGateway.atualizar(clienteAtualizado));
    }
}