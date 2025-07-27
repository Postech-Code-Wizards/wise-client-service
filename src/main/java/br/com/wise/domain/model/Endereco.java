package br.com.wise.domain.model;

public record Endereco(
        String rua,
        String numero,
        String cep,
        String cidade,
        String uf
) {
}
