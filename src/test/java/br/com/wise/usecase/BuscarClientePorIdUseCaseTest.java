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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarClientePorIdUseCaseTest {

    @Mock
    ClienteGateway clienteGateway;

    @InjectMocks
    BuscarClientePorIdUseCase buscarClientePorIdUseCase;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        Endereco endereco = new Endereco(
                "Rua da Esperança",
                "100",
                "13000-000",
                "Campinas",
                "SP"
        );

        cliente = new Cliente(
                1L,
                "Cliente Existente",
                "11122233344",
                LocalDate.of(1990, 1, 1),
                endereco
        );
    }

    @Test
    void deveRetornarCliente_QuandoIdExiste() {
        when(clienteGateway.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        var resultado = buscarClientePorIdUseCase.executar(1L);

        assertThat(resultado)
                .isPresent()
                .get()
                .satisfies(result -> {
                    assertThat(result.id()).isEqualTo(1L);
                    assertThat(result.nome()).isEqualTo("Cliente Existente");
                });

        verify(clienteGateway).buscarPorId(1L);
    }

    @Test
    void deveRetornarOptionalVazio_QuandoClienteNaoExiste() {
        when(clienteGateway.buscarPorId(99L)).thenReturn(Optional.empty());

        Optional<Cliente> resultado = buscarClientePorIdUseCase.executar(99L);

        assertThat(resultado).isEmpty();
        verify(clienteGateway).buscarPorId(99L);
    }
}