package br.com.wise.controller.dto.request;

import lombok.Data;

@Data
public class EnderecoRequest {
    private String rua;
    private String numero;
    private String cep;
    private String cidade;
    private String uf;
}
