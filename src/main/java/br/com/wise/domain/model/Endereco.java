package br.com.wise.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Endereco {
    private String rua;
    private String numero;
    private String cep;
    private String cidade;
    private String uf;
}
