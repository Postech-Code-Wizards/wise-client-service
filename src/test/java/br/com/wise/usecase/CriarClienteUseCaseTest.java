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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarClienteUseCaseTest {

    @Mock
    ClienteGateway clienteGateway;

    @InjectMocks
    CriarClienteUseCase criarClienteUseCase;

    ClienteRequest request;
    Cliente cliente;

    @BeforeEach
    void setUp() {
        request = ClienteRequest.builder()
                .nome("Maria Teste")
                .cpf("98765432100")
                .dataNascimento(LocalDate.of(1995, 5, 20))
                .endereco(
                        EnderecoRequest.builder()
                                .rua("Rua das Testadoras")
                                .numero("42")
                                .cep("13000-999")
                                .cidade("Campinas")
                                .uf("SP")
                                .build()
                )
                .build();

        cliente = Cliente.builder()
                .nome(request.nome())
                .cpf(request.cpf())
                .dataNascimento(request.dataNascimento())
                .endereco(
                        Endereco.builder()
                                .rua(request.endereco().rua())
                                .numero(request.endereco().numero())
                                .cep(request.endereco().cep())
                                .cidade(request.endereco().cidade())
                                .uf(request.endereco().uf())
                                .build()
                )
                .build();
    }

    @Test
    void deveCriarClienteComDadosValidos() {
        when(clienteGateway.buscarPorCpf(cliente.getCpf())).thenReturn(Optional.empty());
        when(clienteGateway.salvar(any())).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        Cliente criado = criarClienteUseCase.executar(cliente);

        assertThat(criado.getId()).isNotNull();
        assertThat(criado.getNome()).isEqualTo("Maria Teste");
        assertThat(criado.getEndereco().getCidade()).isEqualTo("Campinas");

        verify(clienteGateway).salvar(any());
    }

    @Test
    void deveLancarExcecao_QuandoCpfJaExiste() {
        when(clienteGateway.buscarPorCpf(cliente.getCpf()))
                .thenReturn(Optional.of(Cliente.builder().id(2L).cpf(cliente.getCpf()).build()));

        assertThrows(IllegalArgumentException.class, () -> criarClienteUseCase.executar(cliente));
        verify(clienteGateway, never()).salvar(any());
    }
}
