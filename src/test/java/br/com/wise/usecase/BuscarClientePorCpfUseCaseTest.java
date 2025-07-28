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
class BuscarClientePorCpfUseCaseTest {

    @Mock
    ClienteGateway clienteGateway;

    @InjectMocks
    BuscarClientePorCpfUseCase buscarClientePorCpfUseCase;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        Endereco endereco = new Endereco(
                "Rua do CPF",
                "77",
                "13000-200",
                "Campinas",
                "SP"
        );

        cliente = new Cliente(
                1L,
                "Cliente CPF",
                "99988877766",
                LocalDate.of(1985, 6, 15),
                endereco
        );
    }

    @Test
    void deveRetornarCliente_QuandoCpfExiste() {
        when(clienteGateway.buscarPorCpf("99988877766")).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = buscarClientePorCpfUseCase.executar("99988877766");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().cpf()).isEqualTo("99988877766");
        assertThat(resultado.get().nome()).isEqualTo("Cliente CPF");

        verify(clienteGateway).buscarPorCpf("99988877766");
    }

    @Test
    void deveRetornarOptionalVazio_QuandoCpfNaoExiste() {
        when(clienteGateway.buscarPorCpf("00000000000")).thenReturn(Optional.empty());

        Optional<Cliente> resultado = buscarClientePorCpfUseCase.executar("00000000000");

        assertThat(resultado).isEmpty();
        verify(clienteGateway).buscarPorCpf("00000000000");
    }
}