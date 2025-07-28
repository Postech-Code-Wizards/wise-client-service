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
import java.util.List;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarClientesUseCaseTest {

    /*@Mock
    ClienteGateway clienteGateway;

    @InjectMocks
    ListarClientesUseCase listarClientesUseCase;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nome("Cliente 1")
                .cpf("12345678900")
                .dataNascimento(LocalDate.of(1990, 2, 20))
                .endereco(
                        Endereco.builder()
                                .rua("Rua A")
                                .numero("10")
                                .cep("13000-123")
                                .cidade("Campinas")
                                .uf("SP")
                                .build()
                )
                .build();
    }

    @Test
    void deveRetornarListaDeClientes_QuandoExistemClientes() {
        when(clienteGateway.listarTodos()).thenReturn(List.of(cliente));

        List<Cliente> resultado = listarClientesUseCase.executar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.getFirst().getNome()).isEqualTo("Cliente 1");

        verify(clienteGateway).listarTodos();
    }

    @Test
    void deveRetornarListaVazia_QuandoNaoExistemClientes() {
        when(clienteGateway.listarTodos()).thenReturn(Collections.emptyList());

        List<Cliente> resultado = listarClientesUseCase.executar();

        assertThat(resultado).isEmpty();
        verify(clienteGateway).listarTodos();
    }*/
}
