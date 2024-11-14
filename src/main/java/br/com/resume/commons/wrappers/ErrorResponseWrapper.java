/*
 * MIT License
 *
 * Copyright (c) 2024.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package br.com.resume.commons.wrappers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa a estrutura de uma resposta de erro para ser enviada ao cliente em caso de falhas ou exceções
 * durante o processamento de uma requisição. Ela contém informações como o timestamp do erro, o código de status HTTP,
 * a razão do erro e a lista de erros detalhados.
 *
 * <p>
 * A classe utiliza {@link JsonFormat} para formatar a data e hora do erro no padrão especificado. Ela é usada para
 * encapsular a resposta de erro e fornecer informações detalhadas para o cliente.
 * </p>
 *
 * <p>
 * <b>Projeto:</b> resume-api
 * </p>
 *
 * @author MDanylo
 * @version 1.0
 * @since 12 de mar. de 2024
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseWrapper implements Serializable {

    /**
     * O timestamp do erro, indicando a data e hora exatas em que o erro ocorreu. O formato é "dd-MM-yyyy hh:mm:ss".
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    /**
     * O código de status HTTP que representa o tipo de erro ocorrido.
     */
    private int status;

    /**
     * A razão associada ao código de status HTTP, descrevendo o erro de maneira mais legível.
     */
    private String reasonPhrase;

    /**
     * Lista de erros detalhados, podendo ser mensagens ou descrições adicionais relacionadas ao erro.
     */
    private List<String> errors;

    /**
     * Construtor para criar uma instância da resposta de erro, inicializando todos os campos.
     *
     * @param timestamp o timestamp do erro
     * @param status o código de status HTTP associado ao erro
     * @param errors lista de erros detalhados
     */
    public ErrorResponseWrapper(LocalDateTime timestamp, HttpStatusCode status, List<String> errors) {
        this.timestamp = timestamp;
        this.status = status.value();
        this.reasonPhrase = status instanceof HttpStatus ? ((HttpStatus) status).getReasonPhrase() : "";
        this.errors = errors;
    }

    /**
     * Construtor que inicializa a resposta de erro com um único erro.
     *
     * @param timestamp o timestamp do erro
     * @param status o código de status HTTP associado ao erro
     * @param error uma mensagem de erro
     */
    public ErrorResponseWrapper(LocalDateTime timestamp, HttpStatusCode status, String error) {
        this(timestamp, status, Arrays.asList(error));
    }
}
