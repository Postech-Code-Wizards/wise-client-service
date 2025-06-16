package br.com.wise.gateway.database.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoEmbeddable {

    private String rua;
    private String numero;
    private String cep;
    private String cidade;
    private String uf;
}