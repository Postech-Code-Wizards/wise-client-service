package br.com.wise.gateway;

import br.com.wise.controller.mapper.ClienteMapper;
import br.com.wise.domain.model.Cliente;
import br.com.wise.gateway.database.jpa.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class ClienteGatewayImpl implements ClienteGateway {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public Cliente salvar(Cliente cliente) {
        var entity = clienteMapper.toEntity(cliente);
        var salvo = clienteRepository.salvar(entity);
        return clienteMapper.toDomain(salvo);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.buscarPorCpf(cpf)
                .map(clienteMapper::toDomain);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id)
                .map(clienteMapper::toDomain);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.listAll().stream()
                .map(clienteMapper::toDomain)
                .toList();
    }

    @Override
    public void deletar(Cliente cliente) {
        clienteRepository.buscarPorId(cliente.getId())
                .ifPresent(clienteRepository::deletar);
    }
}