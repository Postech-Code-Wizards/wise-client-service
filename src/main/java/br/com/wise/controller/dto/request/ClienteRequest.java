package br.com.wise.controller.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequest {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private EnderecoRequest endereco;
}
