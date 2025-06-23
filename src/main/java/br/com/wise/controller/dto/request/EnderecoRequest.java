package br.com.wise.controller.dto.request;

import lombok.Builder;

@Builder
public record EnderecoRequest(
        String rua,
        String numero,
        String cep,
        String cidade,
        String uf
) {}
