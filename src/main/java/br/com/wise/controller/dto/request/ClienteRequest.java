package br.com.wise.controller.dto.request;

import java.time.LocalDate;

public record ClienteRequest(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        EnderecoRequest endereco
) {
}
