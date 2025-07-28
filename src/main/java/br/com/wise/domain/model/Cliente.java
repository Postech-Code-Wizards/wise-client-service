package br.com.wise.domain.model;

import java.time.LocalDate;

public record Cliente(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        Endereco endereco
) {
}
