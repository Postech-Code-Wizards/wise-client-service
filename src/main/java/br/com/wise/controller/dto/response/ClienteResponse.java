package br.com.wise.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClienteResponse {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private EnderecoResponse endereco;
}
