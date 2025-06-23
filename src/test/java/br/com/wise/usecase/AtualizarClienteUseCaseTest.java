package br.com.wise.usecase;

import br.com.wise.controller.dto.request.ClienteRequest;
import br.com.wise.controller.dto.request.EnderecoRequest;
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
    private ClienteGateway clienteGateway;

    @InjectMocks
    private AtualizarClienteUseCase atualizarClienteUseCase;

    private Cliente clienteExistente;
    private ClienteRequest request;

    @BeforeEach
    void setup() {
        clienteExistente = Cliente.builder()
                .id(1L)
                .nome("Antigo Nome")
                .cpf("12345678900")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .endereco(Endereco.builder()
                        .rua("Rua Velha")
                        .numero("100")
                        .cep("13000-000")
                        .cidade("Campinas")
                        .uf("SP")
                        .build())
                .build();

        request = ClienteRequest.builder()
                .nome("Novo Nome")
                .cpf("12345678900")
                .dataNascimento(LocalDate.of(1998, 10, 15))
                .endereco(
                        EnderecoRequest.builder()
                                .rua("Rua Nova")
                                .numero("200")
                                .cep("13000-001")
                                .cidade("Campinas")
                                .uf("SP")
                                .build()
                )
                .build();
    }

    @Test
    void deveAtualizarClienteComDadosValidos() {
        when(clienteGateway.buscarPorId(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteGateway.buscarPorCpf("12345678900")).thenReturn(Optional.of(clienteExistente));
        when(clienteGateway.atualizar(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente atualizado = atualizarClienteUseCase.executar(1L, request);

        assertThat(atualizado.getNome()).isEqualTo("Novo Nome");
        assertThat(atualizado.getDataNascimento()).isEqualTo(LocalDate.of(1998, 10, 15));
        assertThat(atualizado.getEndereco().getRua()).isEqualTo("Rua Nova");

        verify(clienteGateway).atualizar(any());
    }

    @Test
    void deveLancarExcecao_QuandoClienteNaoExiste() {
        when(clienteGateway.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> atualizarClienteUseCase.executar(1L, request));

        verify(clienteGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecao_QuandoCpfDuplicado() {
        Cliente outroCliente = Cliente.builder().id(2L).cpf("12345678900").build();
        when(clienteGateway.buscarPorId(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteGateway.buscarPorCpf("12345678900")).thenReturn(Optional.of(outroCliente));

        assertThrows(IllegalArgumentException.class, () -> atualizarClienteUseCase.executar(1L, request));

        verify(clienteGateway, never()).salvar(any());
    }
}
