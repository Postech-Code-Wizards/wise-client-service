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
        cliente = Cliente.builder()
                .id(1L)
                .nome("Cliente Para Deletar")
                .cpf("11122233344")
                .dataNascimento(LocalDate.of(1980, 3, 15))
                .endereco(
                        Endereco.builder()
                                .rua("Rua que será esquecida")
                                .numero("404")
                                .cep("13000-404")
                                .cidade("Campinas")
                                .uf("SP")
                                .build()
                )
                .build();
    }

    @Test
    void deveDeletarClienteComObjetoValido() {
        deletarClienteUseCase.executar(cliente);

        verify(clienteGateway).deletar(cliente);
    }
}