package br.com.wise.controller.dto.response;

import java.time.LocalDate;

public record ClienteResponse(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        EnderecoResponse endereco
) {
}
