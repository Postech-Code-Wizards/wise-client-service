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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarClienteUseCaseTest {

    @Mock
    ClienteGateway clienteGateway;

    @InjectMocks
    DeletarClienteUseCase deletarClienteUseCase;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        Endereco endereco = new Endereco(
                "Rua que será esquecida",
                "404",
                "13000-404",
                "Campinas",
                "SP"
        );

        cliente = new Cliente(
                1L,
                "Cliente Para Deletar",
                "11122233344",
                LocalDate.of(1980, 3, 15),
                endereco
        );
    }

    @Test
    void deveDeletarClienteComObjetoValido() {
        when(clienteGateway.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        var resultado = deletarClienteUseCase.executar(cliente.id());

        assertThat(resultado).isTrue();
        verify(clienteGateway).deletar(cliente);
    }

    @Test
    void deveRetornarFalse_QuandoClienteNaoExiste() {
        when(clienteGateway.buscarPorId(1L)).thenReturn(Optional.empty());

        var resultado = deletarClienteUseCase.executar(1L);

        assertThat(resultado).isFalse();
        verify(clienteGateway, never()).deletar(any());
    }
}