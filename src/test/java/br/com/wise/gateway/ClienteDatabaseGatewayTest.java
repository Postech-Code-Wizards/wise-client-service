package br.com.wise.gateway;

import br.com.wise.controller.mapper.ClienteMapper;
import br.com.wise.domain.model.Cliente;
import br.com.wise.domain.model.Endereco;
import br.com.wise.gateway.database.jpa.entity.ClienteEntity;
import br.com.wise.gateway.database.jpa.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteDatabaseGatewayTest {

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    ClienteMapper clienteMapper;

    @InjectMocks
    ClienteDatabaseGateway gateway;

    @Test
    void deveSalvarClienteComSucesso() {
        Cliente cliente = new Cliente(1L, "Bela", "123", LocalDate.of(2000, 1, 1), mock(Endereco.class));
        ClienteEntity entity = new ClienteEntity();
        ClienteEntity salvo = new ClienteEntity();
        Cliente clienteRetornado = new Cliente(1L, "Bela", "123", LocalDate.of(2000, 1, 1), mock(Endereco.class));

        when(clienteMapper.toEntity(cliente)).thenReturn(entity);
        when(clienteRepository.salvar(entity)).thenReturn(salvo);
        when(clienteMapper.toDomain(salvo)).thenReturn(clienteRetornado);

        Cliente resultado = gateway.salvar(cliente);

        assertEquals(clienteRetornado, resultado);
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        Cliente cliente = mock(Cliente.class);
        ClienteEntity entity = mock(ClienteEntity.class);
        ClienteEntity atualizado = mock(ClienteEntity.class);
        Cliente esperado = mock(Cliente.class);

        when(clienteMapper.toEntity(cliente)).thenReturn(entity);
        when(clienteRepository.atualizar(entity)).thenReturn(atualizado);
        when(clienteMapper.toDomain(atualizado)).thenReturn(esperado);

        Cliente resultado = gateway.atualizar(cliente);

        assertEquals(esperado, resultado);
    }

    @Test
    void deveBuscarPorCpfComSucesso() {
        String cpf = "123";
        ClienteEntity entity = mock(ClienteEntity.class);
        Cliente esperado = mock(Cliente.class);

        when(clienteRepository.buscarPorCpf(cpf)).thenReturn(Optional.of(entity));
        when(clienteMapper.toDomain(entity)).thenReturn(esperado);

        var resultado = gateway.buscarPorCpf(cpf);

        assertTrue(resultado.isPresent());
        assertEquals(esperado, resultado.get());
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        Long id = 1L;
        ClienteEntity entity = mock(ClienteEntity.class);
        Cliente esperado = mock(Cliente.class);

        when(clienteRepository.buscarPorId(id)).thenReturn(Optional.of(entity));
        when(clienteMapper.toDomain(entity)).thenReturn(esperado);

        var resultado = gateway.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(esperado, resultado.get());
    }

    @Test
    void deveListarTodos() {
        var entidades = List.of(mock(ClienteEntity.class), mock(ClienteEntity.class));
        var clientes = List.of(mock(Cliente.class), mock(Cliente.class));

        when(clienteRepository.listAll()).thenReturn(entidades);
        when(clienteMapper.toDomain(any(ClienteEntity.class))).thenReturn(clientes.get(0), clientes.get(1));

        var resultado = gateway.listarTodos();

        assertEquals(clientes, resultado);
    }

    @Test
    void deveDeletarCliente() {
        Cliente cliente = new Cliente(1L, "Nome", "123", LocalDate.of(2000, 1, 1), mock(Endereco.class));
        ClienteEntity entity = mock(ClienteEntity.class);

        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(entity));

        gateway.deletar(cliente);

        verify(clienteRepository).deletar(entity);
    }

    @Test
    void naoDeveDeletarSeNaoEncontrarCliente() {
        Cliente cliente = new Cliente(99L, "Desconhecido", "000", LocalDate.of(1990, 1, 1), mock(Endereco.class));
        when(clienteRepository.buscarPorId(99L)).thenReturn(Optional.empty());

        gateway.deletar(cliente);

        verify(clienteRepository, never()).deletar(any());
    }
}
