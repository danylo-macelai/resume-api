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
package br.com.resume.infrastructure.config;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.resume.commons.wrappers.ErrorResponseWrapper;
import br.com.resume.infrastructure.exception.ResumeException;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Description:</b> FIXME: Document this type <br>
 * <b>Project:</b> resume-api <br>
 *
 * @author Danylo
 * @date: 12 de mar. de 2024
 * @version $
 */
@Slf4j
@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    private static final String H2_TRUNCATION                     = "22001"; // Truncamento de string
    private static final String H2_NOT_NULL_VIOLATION             = "23502"; // Violação de NOT NULL
    private static final String H2_UNIQUE_VIOLATION               = "23505"; // Violação de chave única
    private static final String H2_SYNTAX_ERROR                   = "42000"; // Erro de sintaxe SQL
    private static final String H2_NUMERIC_OUT_OF_RANGE           = "22003"; // Valor numérico fora do intervalo
    private static final String H2_DIVISION_BY_ZERO               = "22012"; // Divisão por zero
    private static final String H2_FUNCTION_NOT_EXIST             = "42883"; // Função não existe
    private static final String H2_CONNECTION_FAILURE             = "08006"; // Falha na conexão
    private static final String H2_GENERAL_ERROR                  = "54000"; // Erro genérico
    private static final String H2_INTEGRITY_CONSTRAINT_VIOLATION = "23000"; // Violação de integridade

    @Autowired
    MessageSource mensagens;

    /**
     * Trata exceções do tipo {@link ResumeException} lançadas na aplicação.
     *
     * @param ex A exceção do tipo {@link ResumeException}.
     * @param request A requisição HTTP que gerou a exceção.
     * @return Uma resposta com o erro associado à exceção.
     */
    @ExceptionHandler(value = { ResumeException.class })
    protected ResponseEntity<Object> handleResumeException(ResumeException ex, WebRequest request) {
        final String error = getMessage(ex.getKey(), ex.getArgs());
        log.error("Exceção personalizada (ResumeException) capturada: {}", error, ex);
        final ErrorResponseWrapper body = new ErrorResponseWrapper(LocalDateTime.now(), ex.getStatusCode(), error);
        return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getStatusCode(), request);
    }

    /**
     * Trata exceções do tipo {@link SQLException}, verificando o estado SQL do erro e respondendo com a mensagem
     * apropriada para cada tipo de erro específico.
     *
     * @param ex A exceção {@link SQLException}.
     * @param request A requisição HTTP que gerou a exceção.
     * @return Uma resposta com o erro associado à exceção SQL.
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException ex, WebRequest request) {
        ErrorResponseWrapper body = null;
        String error = null;
        HttpStatus status = BAD_REQUEST;

        // Verifica o código de erro e define a mensagem de erro com base no SQLState
        String columnName = extractColumnNameFromMessage(ex.getMessage());

        // Log de erro com detalhes da exceção
        if (H2_TRUNCATION.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.column.name.too.long", columnName);
            log.error("Erro de truncamento na coluna '{}'. SQLState: {} - Mensagem: {}", columnName, ex.getSQLState(),
                    ex.getMessage());
        } else if (H2_NOT_NULL_VIOLATION.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.notnull.violation", columnName);
            log.error("Violação de NOT NULL na coluna '{}'. SQLState: {} - Mensagem: {}", columnName, ex.getSQLState(),
                    ex.getMessage());
        } else if (H2_UNIQUE_VIOLATION.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.duplicate.key", columnName);
            log.error("Violação de chave única na coluna '{}'. SQLState: {} - Mensagem: {}", columnName,
                    ex.getSQLState(), ex.getMessage());
        } else if (H2_NUMERIC_OUT_OF_RANGE.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.numeric.out.of.range", columnName);
            log.error("Valor numérico fora do intervalo permitido na coluna '{}'. SQLState: {} - Mensagem: {}",
                    columnName, ex.getSQLState(), ex.getMessage());
        } else if (H2_DIVISION_BY_ZERO.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.division.by.zero");
            log.error("Erro de divisão por zero. SQLState: {} - Mensagem: {}", ex.getSQLState(), ex.getMessage());
        } else if (H2_SYNTAX_ERROR.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.syntax.error", columnName);
            log.error("Erro de sintaxe SQL na coluna '{}'. SQLState: {} - Mensagem: {}", columnName, ex.getSQLState(),
                    ex.getMessage());
        } else if (H2_FUNCTION_NOT_EXIST.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.function.not.exist", columnName);
            log.error("Função SQL não encontrada na coluna '{}'. SQLState: {} - Mensagem: {}", columnName,
                    ex.getSQLState(), ex.getMessage());
        } else if (H2_CONNECTION_FAILURE.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.connection.failure");
            status = INTERNAL_SERVER_ERROR;
            log.error("Falha na conexão com o banco de dados. SQLState: {} - Mensagem: {}", ex.getSQLState(),
                    ex.getMessage());
        } else if (H2_GENERAL_ERROR.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.sql.general", columnName);
            status = INTERNAL_SERVER_ERROR;
            log.error("Erro genérico SQL. SQLState: {} - Mensagem: {}", ex.getSQLState(), ex.getMessage());
        } else if (H2_INTEGRITY_CONSTRAINT_VIOLATION.equals(ex.getSQLState())) {
            error = getMessage("rsm.h2.integrity.violation", columnName);
            log.error("Violação de integridade referencial. SQLState: {} - Mensagem: {}", ex.getSQLState(),
                    ex.getMessage());
        }

        if (error != null && !error.trim().isBlank()) {
            body = new ErrorResponseWrapper(LocalDateTime.now(), status, error);
        }

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    /**
     * Trata exceções do tipo {@link MethodArgumentNotValidException}, que ocorrem quando os argumentos de uma
     * requisição falham na validação. Este método compila todas as mensagens de erro de validação e retorna uma
     * resposta contendo detalhes dos campos inválidos.
     *
     * @param ex A exceção do tipo {@link MethodArgumentNotValidException}.
     * @param headers Os cabeçalhos HTTP que acompanham a resposta.
     * @param status O código de status HTTP associado ao erro de validação.
     * @param request A requisição HTTP que gerou a exceção.
     * @return Uma resposta {@link ResponseEntity} com os detalhes dos erros de validação.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        final List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> {
                    final String errorMessage = String.format("Campo '%s': %s", field.getField(),
                            field.getDefaultMessage());
                    log.warn("Erro de validação no campo '{}': {}", field.getField(), field.getDefaultMessage());
                    return errorMessage;
                })
                .collect(Collectors.toList());

        ErrorResponseWrapper body = new ErrorResponseWrapper(LocalDateTime.now(), status, errors);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * Sobrescreve o método {@link ResponseEntityExceptionHandler#handleExceptionInternal} para personalizar a resposta
     * de erro.
     *
     * @param ex A exceção que foi tratada.
     * @param body O corpo da resposta de erro.
     * @param headers Os cabeçalhos da resposta.
     * @param statusCode O código de status HTTP.
     * @param request A requisição HTTP que gerou a exceção.
     * @return A resposta personalizada com o corpo de erro e código de status.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (body == null) {
            statusCode = INTERNAL_SERVER_ERROR;
            body = new ErrorResponseWrapper(LocalDateTime.now(), statusCode, getMessage("rsm.generics.error"));
            log.error("Erro inesperado: {}", ex.getMessage(), ex);
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    /**
     * Recupera a mensagem de erro baseada na chave e argumentos fornecidos.
     *
     * @param key A chave da mensagem.
     * @param args Os argumentos para formatar a mensagem.
     * @return A mensagem formatada.
     */
    private String getMessage(final String key, final String[] args) {
        return mensagens.getMessage(key, args, key, LocaleContextHolder.getLocale());
    }

    /**
     * Recupera a mensagem de erro baseada na chave e argumentos fornecidos.
     *
     * @param key A chave da mensagem.
     * @param args Os argumentos para formatar a mensagem.
     * @return A mensagem formatada.
     */
    private String getMessage(final String key, final Object... args) {
        return mensagens.getMessage(key, args, key, LocaleContextHolder.getLocale());
    }

    /**
     * Extrai o nome da coluna a partir da mensagem de erro.
     *
     * @param errorMessage A mensagem de erro que contém o nome da coluna entre aspas.
     * @return O nome da coluna extraído, ou "Desconhecido" se não for possível extrair.
     */
    private String extractColumnNameFromMessage(String errorMessage) {
        Pattern pattern = Pattern.compile("\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Desconhecido";
    }
}
