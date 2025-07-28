package br.com.wise.controller.dto.response;

public record EnderecoResponse(
        String rua,
        String numero,
        String cep,
        String cidade,
        String uf
) {
}
