package br.com.wise.gateway;

import br.com.wise.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteGateway {
    Optional<Cliente> buscarPorId(Long id);

    Optional<Cliente> buscarPorCpf(String cpf);

    Cliente salvar(Cliente cliente);

    void deletar(Cliente cliente);

    List<Cliente> listarTodos();
}
