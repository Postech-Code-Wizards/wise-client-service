package br.com.wise.controller.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoResponse {
    private String rua;
    private String numero;
    private String cep;
    private String cidade;
    private String uf;
}