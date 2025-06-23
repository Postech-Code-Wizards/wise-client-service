package br.com.wise.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ClienteControllerTest {

    @Test
    void deveRetornar404_QuandoClienteNaoExiste() {
        given()
                .when()
                .get("/clientes/9999")

                .then()
                .statusCode(404);
    }

    @Test
    void deveListarClientes_VazioQuandoNaoExistem() {
        given()
                .when()
                .get("/clientes")
                .then()
                .statusCode(200)
                .body("", hasSize(0));
    }

    @Test
    void deveCriarClienteERetornar201() {
        var novoClienteJson = """
                {
                  "nome": "Joana Teste",
                  "cpf": "12345678900",
                  "dataNascimento": "1995-05-20",
                  "endereco": {
                    "rua": "Rua Nova",
                    "numero": "42",
                    "cep": "13000-000",
                    "cidade": "Campinas",
                    "uf": "SP"
                  }
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(novoClienteJson)
                .when()
                .post("/clientes")
                .then()
                .statusCode(201)
                .header("Location", containsString("/clientes/"))
                .body("nome", equalTo("Joana Teste"));
    }

    @Test
    void deveBuscarClienteAposCriacao() {
        var novoClienteJson = """
            {
              "nome": "Lucas Teste",
              "cpf": "98765432100",
              "dataNascimento": "1992-10-10",
              "endereco": {
                "rua": "Rua do Centro",
                "numero": "100",
                "cep": "13000-000",
                "cidade": "Campinas",
                "uf": "SP"
              }
            }
            """;

        var location = given()
                .contentType(ContentType.JSON)
                .body(novoClienteJson)
                .when()
                .post("/clientes")
                .then()
                .statusCode(201)
                .extract()
                .header("Location");

        given()
                .when()
                .get(location)
                .then()
                .statusCode(200)
                .body("cpf", equalTo("98765432100"))
                .body("nome", equalTo("Lucas Teste"));
    }

    @Test
    void deveAtualizarCliente() {
        var novoClienteJson = """
            {
              "nome": "Maria Original",
              "cpf": "11122233344",
              "dataNascimento": "1990-08-01",
              "endereco": {
                "rua": "Rua Original",
                "numero": "123",
                "cep": "13000-000",
                "cidade": "Campinas",
                "uf": "SP"
              }
            }
            """;

        var location = given()
                .contentType(ContentType.JSON)
                .body(novoClienteJson)
                .when()
                .post("/clientes")
                .then()
                .statusCode(201)
                .extract()
                .header("Location");

        String clienteAtualizado = """
            {
              "nome": "Maria Atualizada",
              "cpf": "11122233344",
              "dataNascimento": "1990-08-01",
              "endereco": {
                "rua": "Rua Original",
                "numero": "123",
                "cep": "13000-000",
                "cidade": "Campinas",
                "uf": "SP"
              }
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(clienteAtualizado)
                .when()
                .put(location)
                .then()
                .statusCode(200)
                .body("nome", equalTo("Maria Atualizada"));
    }

    @Test
    void deveDeletarCliente() {
        var novoClienteJson = """
            {
              "nome": "Carlos Apagar",
              "cpf": "55566677788",
              "dataNascimento": "1985-12-25",
              "endereco": {
                "rua": "Rua Temporária",
                "numero": "777",
                "cep": "13000-000",
                "cidade": "Campinas",
                "uf": "SP"
              }
            }
            """;

        var location = given()
                .contentType(ContentType.JSON)
                .body(novoClienteJson)
                .when()
                .post("/clientes")
                .then()
                .statusCode(201)
                .extract()
                .header("Location");

        given()
                .when()
                .delete(location)
                .then()
                .statusCode(204);

        given()
                .when()
                .get(location)
                .then()
                .statusCode(404);
    }

}
