package br.com.wise.controller.dto.request;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ClienteRequest(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        EnderecoRequest endereco
) {}
