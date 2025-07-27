package br.com.wise.usecase;

import br.com.wise.domain.model.Cliente;
import br.com.wise.domain.model.Endereco;
import br.com.wise.gateway.ClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarClienteUseCaseTest {

    @Mock
    ClienteGateway clienteGateway;

    @InjectMocks
    AtualizarClienteUseCase atualizarClienteUseCase;

    Cliente clienteExistente;
    Cliente clienteParaAtualizacao;

    @BeforeEach
    void setUp() {
        clienteExistente = new Cliente(
                1L,
                "Antigo Nome",
                "12345678900",
                LocalDate.of(1990, 1, 1),
                new Endereco("Rua Velha", "100", "13000-000", "Campinas", "SP")
        );

        clienteParaAtualizacao = new Cliente(
                null,
                "Novo Nome",
                "12345678900",
                LocalDate.of(1998, 10, 15),
                new Endereco("Rua Nova", "200", "13000-001", "Campinas", "SP")
        );
    }

    @Test
    void deveAtualizarClienteComDadosValidos() {
        when(clienteGateway.buscarPorId(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteGateway.buscarPorCpf("12345678900")).thenReturn(Optional.of(clienteExistente));

        Cliente clienteAtualizadoEsperado = new Cliente(
                1L,
                "Novo Nome",
                "12345678900",
                LocalDate.of(1998, 10, 15),
                new Endereco("Rua Nova", "200", "13000-001", "Campinas", "SP")
        );

        when(clienteGateway.atualizar(any())).thenReturn(clienteAtualizadoEsperado);

        Optional<Cliente> resultado = atualizarClienteUseCase.executar(1L, clienteParaAtualizacao);

        assertThat(resultado)
                .isPresent()
                .get()
                .satisfies(cliente -> {
                    assertThat(cliente.id()).isEqualTo(1L);
                    assertThat(cliente.nome()).isEqualTo("Novo Nome");
                    assertThat(cliente.endereco().rua()).isEqualTo("Rua Nova");
                });

        verify(clienteGateway).atualizar(any());
    }

    @Test
    void deveRetornarEmpty_QuandoClienteNaoExiste() {
        when(clienteGateway.buscarPorId(1L)).thenReturn(Optional.empty());

        Optional<Cliente> resultado = atualizarClienteUseCase.executar(1L, clienteParaAtualizacao);

        assertThat(resultado).isEmpty();

        verify(clienteGateway, never()).atualizar(any());
    }

    @Test
    void deveLancarExcecao_QuandoCpfDuplicado() {
        Cliente outroCliente = new Cliente(
                2L,
                "Outro Cliente",
                "12345678900",
                LocalDate.of(1990, 1, 1),
                clienteExistente.endereco()
        );

        when(clienteGateway.buscarPorId(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteGateway.buscarPorCpf("12345678900")).thenReturn(Optional.of(outroCliente));

        assertThrows(IllegalArgumentException.class, () ->
                atualizarClienteUseCase.executar(1L, clienteParaAtualizacao)
        );

        verify(clienteGateway, never()).atualizar(any());
    }
}