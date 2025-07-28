package br.com.wise.controller.mapper;

import static org.junit.jupiter.api.Assertions.*;

import br.com.wise.controller.dto.request.ClienteRequest;
import br.com.wise.controller.dto.request.EnderecoRequest;
import br.com.wise.controller.dto.response.ClienteResponse;
import br.com.wise.controller.dto.response.EnderecoResponse;
import br.com.wise.domain.model.Cliente;
import br.com.wise.domain.model.Endereco;
import br.com.wise.gateway.database.jpa.entity.ClienteEntity;
import br.com.wise.gateway.database.jpa.entity.EnderecoEmbeddable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ClienteMapperTest {

    private ClienteMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ClienteMapper();
    }

    @Test
    void deveConverterEnderecoRequestParaEnderecoDomain() {
        EnderecoRequest request = new EnderecoRequest("Rua 1", "123", "01234-567", "Cidade", "SP");
        Endereco endereco = ClienteMapper.toEnderecoDomain(request);

        assertNotNull(endereco);
        assertEquals("Rua 1", endereco.rua());
        assertEquals("123", endereco.numero());
        assertEquals("01234-567", endereco.cep());
        assertEquals("Cidade", endereco.cidade());
        assertEquals("SP", endereco.uf());
    }

    @Test
    void deveConverterClienteParaClienteEntity() {
        Endereco endereco = new Endereco("Rua", "123", "12345-678", "Cidade", "SP");
        Cliente cliente = new Cliente(1L, "Maria", "12345678900", LocalDate.of(1990, 1, 1), endereco);

        ClienteEntity entity = mapper.toEntity(cliente);

        assertNotNull(entity);
        assertEquals("Maria", entity.getNome());
        assertEquals("12345678900", entity.getCpf());
        assertEquals("12345-678", entity.getEndereco().getCep());
    }

    @Test
    void deveConverterClienteEntityParaClienteDomain() {
        EnderecoEmbeddable emb = new EnderecoEmbeddable("Rua", "123", "12345-678", "Cidade", "SP");
        ClienteEntity entity = new ClienteEntity(1L, "João", "99999999999", LocalDate.of(1985, 5, 10), emb);

        Cliente cliente = mapper.toDomain(entity);

        assertNotNull(cliente);
        assertEquals("João", cliente.nome());
        assertEquals("Cidade", cliente.endereco().cidade());
    }

    @Test
    void deveConverterClienteDomainParaClienteResponse() {
        Endereco endereco = new Endereco("Rua", "123", "12345-678", "Cidade", "SP");
        Cliente cliente = new Cliente(1L, "Ana", "11122233344", LocalDate.of(1995, 3, 20), endereco);

        ClienteResponse response = ClienteMapper.toResponse(cliente);

        assertNotNull(response);
        assertEquals("Ana", response.nome());
        EnderecoResponse endResp = response.endereco();
        assertEquals("Rua", endResp.rua());
    }

    @Test
    void deveConverterClienteRequestParaClienteDomain() {
        EnderecoRequest enderecoRequest = new EnderecoRequest("Rua", "123", "12345-678", "Cidade", "SP");
        ClienteRequest request = new ClienteRequest("Carlos", "88877766655", LocalDate.of(2000, 1, 1), enderecoRequest);

        Cliente cliente = mapper.toDomain(request);

        assertNotNull(cliente);
        assertEquals("Carlos", cliente.nome());
        assertEquals("Cidade", cliente.endereco().cidade());
    }

    @Test
    void deveRetornarNullQuandoRequestOuEntidadeForemNulos() {
        assertNull(ClienteMapper.toEnderecoDomain(null));
        assertNull(mapper.toEntity(null));
        assertNull(mapper.toDomain((ClienteEntity) null));
        assertNull(mapper.toDomain((ClienteRequest) null));
        assertNull(ClienteMapper.toResponse(null));
    }
}
