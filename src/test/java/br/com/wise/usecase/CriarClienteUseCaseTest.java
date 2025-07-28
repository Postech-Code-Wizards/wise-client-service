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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarClienteUseCaseTest {

    @Mock
    ClienteGateway clienteGateway;

    @InjectMocks
    CriarClienteUseCase criarClienteUseCase;

    Cliente cliente;

    @BeforeEach
    void setUp() {
        Endereco endereco = new Endereco(
                "Rua das Testadoras",
                "42",
                "13000-999",
                "Campinas",
                "SP"
        );

        cliente = new Cliente(
                1L,
                "Maria Teste",
                "98765432100",
                LocalDate.of(1995, 5, 20),
                endereco
        );
    }

    @Test
    void deveCriarClienteComDadosValidos() {
        when(clienteGateway.buscarPorCpf(cliente.cpf())).thenReturn(Optional.empty());
        when(clienteGateway.salvar(any())).thenReturn(cliente);

        var criado = criarClienteUseCase.executar(cliente);

        assertThat(criado.id()).isNotNull();
        assertThat(criado.nome()).isEqualTo("Maria Teste");
        assertThat(criado.endereco().cidade()).isEqualTo("Campinas");

        verify(clienteGateway).salvar(any());
    }

    @Test
    void deveLancarExcecao_QuandoCpfJaExiste() {
        var existente = new Cliente(2L, "Outro Nome", cliente.cpf(), LocalDate.now(), cliente.endereco());

        when(clienteGateway.buscarPorCpf(cliente.cpf())).thenReturn(Optional.of(existente));

        assertThrows(IllegalArgumentException.class, () -> criarClienteUseCase.executar(cliente));

        verify(clienteGateway, never()).salvar(any());
    }
}

