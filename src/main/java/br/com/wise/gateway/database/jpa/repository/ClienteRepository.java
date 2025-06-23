package br.com.wise.gateway.database.jpa.repository;

import br.com.wise.gateway.database.jpa.entity.ClienteEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<ClienteEntity> {

    public Optional<ClienteEntity> buscarPorCpf(String cpf) {
        return find("cpf", cpf).firstResultOptional();
    }

    public boolean existePorCpf(String cpf) {
        return find("cpf", cpf).count() > 0;
    }

    @Transactional
    public ClienteEntity salvar(ClienteEntity cliente) {
        persist(cliente);
        return cliente;
    }

    @Transactional
    public ClienteEntity atualizar(ClienteEntity cliente) {
        return getEntityManager().merge(cliente);
    }

    public Optional<ClienteEntity> buscarPorId(Long id) {
        return findByIdOptional(id);
    }

    @Transactional
    public void deletar(ClienteEntity cliente) {
        delete(cliente);
    }

    public List<ClienteEntity> listarTodos() {
        return listAll();
    }
}
