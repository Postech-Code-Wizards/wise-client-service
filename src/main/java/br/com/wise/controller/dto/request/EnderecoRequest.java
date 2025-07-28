package br.com.wise.controller.dto.request;


public record EnderecoRequest(
        String rua,
        String numero,
        String cep,
        String cidade,
        String uf
) {
}
