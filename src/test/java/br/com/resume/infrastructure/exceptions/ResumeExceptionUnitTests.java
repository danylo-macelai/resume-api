
package br.com.resume.infrastructure.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;

/**
 * Classe de testes unitários para a classe {@link ResumeException}. Contém uma variedade de cenários de teste para
 * validar o comportamento dos métodos e construtores de `ResumeException`, incluindo o suporte à internacionalização de
 * mensagens e o uso do padrão Builder.
 *
 * <p>
 * Os testes verificam:
 * </p>
 * <ul>
 * <li>Construção da exceção com diferentes configurações de mensagens e parâmetros.</li>
 * <li>Formatação e tradução de mensagens usando um `MessageSource` simulado.</li>
 * <li>Comportamento do fallback ao exibir a chave da mensagem como razão (`reason`) da exceção.</li>
 * <li>Utilização do `Builder` para construir exceções com múltiplas mensagens, com suporte a tradução.</li>
 * </ul>
 *
 * <p>
 * <b>Projeto:</b> resume-api
 * </p>
 *
 * @see ResumeException
 * @see MessageSource
 *
 * @author MDanylo
 * @version 1.0
 * @since 12 de mar. de 2024
 */
@ActiveProfiles("test")
@SpringBootTest
public class ResumeExceptionUnitTests {

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o construtor `ResumeException` com uma mensagem sem parâmetros. Verifica se o status HTTP e a chave da
     * mensagem (`reason`) são configurados corretamente.
     */
    @Test
    public void testSingleMessageWithoutArgs() {
        ResumeException exception = new ResumeException(BAD_REQUEST, "error.key");
        assertEquals(BAD_REQUEST, exception.getStatusCode());
        assertEquals("error.key", exception.getReason());
    }

    /**
     * Testa o construtor `ResumeException` com uma mensagem e parâmetros. Verifica se o status HTTP e a chave da
     * mensagem (`reason`) são configurados corretamente com parâmetros.
     */
    @Test
    public void testSingleMessageWithArgs() {
        ResumeException exception = new ResumeException(NOT_FOUND, "error.not_found", "param1", "param2");
        assertEquals(NOT_FOUND, exception.getStatusCode());
        assertEquals("error.not_found", exception.getReason());
    }

    /**
     * Testa o método `getMensagens` com um `MessageSource` válido. Simula uma tradução usando o `MessageSource` e
     * verifica se a mensagem traduzida é retornada corretamente.
     */
    @Test
    public void testGetMensagensWithMessageSource() {
        Mockito.when(messageSource.getMessage(eq("error.key"), any(), any(Locale.class)))
                .thenReturn("Translated message");

        ResumeException exception = new ResumeException(BAD_REQUEST, "error.key");
        List<String> mensagens = exception.getMensagens(messageSource);

        assertEquals(1, mensagens.size());
        assertEquals("Translated message", mensagens.get(0));
    }

    /**
     * Testa o método `getMensagens` com `MessageSource` nulo. Verifica se a chave da mensagem é retornada diretamente
     * quando `MessageSource` é nulo.
     */
    @Test
    public void testGetMensagensWithNullMessageSource() {
        ResumeException exception = new ResumeException(BAD_REQUEST, "error.key");
        List<String> mensagens = exception.getMensagens(null);

        assertEquals(1, mensagens.size());
        assertEquals("error.key", mensagens.get(0)); // Deve retornar a chave diretamente
    }

    /**
     * Testa o método `getMensagens` com uma chave de mensagem inexistente no `MessageSource`. Simula um erro ao buscar
     * uma mensagem e verifica se a chave é usada como fallback.
     */
    @Test
    public void testGetMensagensWithMissingKeyInMessageSource() {
        Mockito.when(messageSource.getMessage(eq("missing.key"), any(), any(Locale.class)))
                .thenThrow(new RuntimeException("Message not found"));

        ResumeException exception = new ResumeException(INTERNAL_SERVER_ERROR, "missing.key");
        List<String> mensagens = exception.getMensagens(messageSource);

        assertEquals(1, mensagens.size());
        assertEquals("missing.key", mensagens.get(0)); // Deve retornar a chave como fallback
    }

    /**
     * Testa a construção de `ResumeException` usando o `Builder` com uma única mensagem. Verifica se a mensagem e o
     * status HTTP são configurados corretamente na exceção construída.
     */
    @Test
    public void testBuilderWithSingleMessage() {
        ResumeException exception = new ResumeException.Builder(BAD_REQUEST)
                .append("error.single_message")
                .build();

        assertEquals(BAD_REQUEST, exception.getStatusCode());
        List<String> mensagens = exception.getMensagens(null);
        assertEquals(1, mensagens.size());
        assertEquals("error.single_message", mensagens.get(0));
    }

    /**
     * Testa a construção de `ResumeException` usando o `Builder` com múltiplas mensagens. Verifica se todas as
     * mensagens são adicionadas corretamente à exceção.
     */
    @Test
    public void testBuilderWithMultipleMessages() {
        ResumeException exception = new ResumeException.Builder(BAD_REQUEST)
                .append("error.first")
                .append("error.second", "arg1")
                .build();

        assertEquals(BAD_REQUEST, exception.getStatusCode());
        List<String> mensagens = exception.getMensagens(null);
        assertEquals(2, mensagens.size());
        assertEquals("error.first", mensagens.get(0));
        assertEquals("error.second", mensagens.get(1));
    }

    /**
     * Testa o uso do `Builder` com tradução de mensagens usando o `MessageSource`. Verifica se as mensagens são
     * traduzidas corretamente a partir do `MessageSource`.
     */
    @Test
    public void testBuilderWithMessageSourceTranslation() {
        Mockito.when(messageSource.getMessage(eq("error.first"), any(), any(Locale.class)))
                .thenReturn("First translated message");
        Mockito.when(messageSource.getMessage(eq("error.second"), any(), any(Locale.class)))
                .thenReturn("Second translated message");

        ResumeException exception = new ResumeException.Builder(BAD_REQUEST)
                .append("error.first")
                .append("error.second", "arg1")
                .build();

        List<String> mensagens = exception.getMensagens(messageSource);
        assertEquals(2, mensagens.size());
        assertEquals("First translated message", mensagens.get(0));
        assertEquals("Second translated message", mensagens.get(1));
    }

    /**
     * Testa o fallback para a primeira mensagem da exceção no campo `reason`. Verifica se o campo `reason` da exceção
     * usa a primeira chave de mensagem quando fornecida.
     */
    @Test
    public void testFallbackToReasonMessageInGetReason() {
        ResumeException exception = new ResumeException(BAD_REQUEST, "reason.key", "param1");
        assertEquals("reason.key", exception.getReason());
    }

    /**
     * Testa o fallback para a chave da primeira mensagem como `reason` ao construir a exceção com o `Builder`. Verifica
     * se o campo `reason` utiliza a primeira chave de mensagem.
     */
    @Test
    public void testReasonMessageFallbackWithBuilder() {
        ResumeException exception = new ResumeException.Builder(BAD_REQUEST)
                .append("builder.reason.key")
                .build();

        assertEquals("builder.reason.key", exception.getReason());
    }
}
