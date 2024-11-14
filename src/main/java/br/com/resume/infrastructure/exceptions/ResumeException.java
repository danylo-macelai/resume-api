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
package br.com.resume.infrastructure.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe `ResumeException` que estende `ResponseStatusException` para tratamento personalizado de exceções com suporte
 * a mensagens parametrizadas e internacionalização. Utiliza uma lista de mensagens (`Messages`) para armazenar chaves
 * de mensagens e seus argumentos, possibilitando a formatação e a tradução das mensagens com `MessageSource`.
 *
 * <p>
 * <b>Projeto:</b> resume-api
 * </p>
 *
 * @see ResponseStatusException
 * @see MessageSource
 *
 * @author MDanylo
 * @version 1.0
 * @since 12 de mar. de 2024
 */
@Slf4j
@SuppressWarnings("serial")
public class ResumeException extends ResponseStatusException {

    private final List<Messages> mensagens;

    /**
     * Construtor privado da classe `ResumeException`.
     *
     * @param status o status HTTP associado à exceção
     * @param mensagens a lista de mensagens para exibição
     */
    private ResumeException(HttpStatus status, List<Messages> mensagens) {
        super(status, mensagens.isEmpty() ? null : mensagens.get(0).getKey());
        this.mensagens = mensagens;
    }

    /**
     * Construtor que cria uma exceção com um status HTTP e uma chave de mensagem.
     *
     * @param status o status HTTP associado à exceção
     * @param key a chave da mensagem para exibição
     */
    public ResumeException(HttpStatus status, String key) {
        this(status, key, new Object[] {});
    }

    /**
     * Construtor que cria uma exceção com um status HTTP, uma chave de mensagem e parâmetros opcionais para a
     * formatação da mensagem.
     *
     * @param status o status HTTP associado à exceção
     * @param key a chave da mensagem para exibição
     * @param args os argumentos para formatação da mensagem
     */
    public ResumeException(HttpStatus status, String key, Object... args) {
        super(status, key);
        this.mensagens = new ArrayList<>();
        this.mensagens.add(new Messages(key, Arrays.stream(args).map(String::valueOf).toArray(String[]::new)));
    }

    /**
     * Retorna a lista de mensagens formatadas com base no `MessageSource` fornecido. Caso o `MessageSource` seja nulo,
     * as chaves originais das mensagens serão retornadas.
     *
     * @param messageSource a fonte de mensagens para tradução e formatação
     * @return a lista de mensagens formatadas ou chaves originais caso `messageSource` seja nulo
     */
    public List<String> getMensagens(@Nullable MessageSource messageSource) {
        return mensagens.stream()
                .map(m -> {
                    String message;
                    try {
                        message = (messageSource != null)
                                ? messageSource.getMessage(m.getKey(), m.getArgs(), LocaleContextHolder.getLocale())
                                : m.getKey();
                    } catch (Exception e) {
                        log.warn("Mensagem não encontrada para a chave '{}', usando chave como fallback.", m.getKey(),
                                e);
                        message = m.getKey();
                    }
                    return message;
                })
                .collect(Collectors.toList());
    }

    /**
     * Builder para facilitar a construção de uma instância de `ResumeException`, permitindo a adição de várias
     * mensagens com diferentes chaves e parâmetros.
     *
     * @see ResumeException
     */
    public static class Builder implements Serializable {
        private final HttpStatus     status;
        private final List<Messages> mensagens;

        /**
         * Construtor do builder que inicializa o status HTTP da exceção.
         *
         * @param status o status HTTP para a exceção a ser construída
         */
        public Builder(HttpStatus status) {
            this.status = status;
            this.mensagens = new ArrayList<>();
        }

        /**
         * Adiciona uma mensagem ao builder usando apenas uma chave.
         *
         * @param key a chave da mensagem a ser adicionada
         * @return o próprio builder para encadeamento de chamadas
         */
        public Builder append(String key) {
            return append(key, new Object[] {});
        }

        /**
         * Adiciona uma mensagem ao builder com chave e argumentos para formatação.
         *
         * @param key a chave da mensagem a ser adicionada
         * @param args os argumentos para formatação da mensagem
         * @return o próprio builder para encadeamento de chamadas
         */
        public Builder append(String key, Object... args) {
            mensagens.add(new Messages(key, Arrays.stream(args).map(String::valueOf).toArray(String[]::new)));
            return this;
        }

        /**
         * Constrói e retorna uma instância de `ResumeException` usando as mensagens e status configurados.
         *
         * @return uma nova instância de `ResumeException`
         */
        public ResumeException build() {
            return new ResumeException(status, mensagens);
        }
    }

    /**
     * Classe interna `Messages` que representa uma mensagem com uma chave e uma lista de argumentos para formatação.
     * Utilizada para armazenar as mensagens associadas à exceção.
     */
    @Getter
    @AllArgsConstructor
    private static class Messages implements Serializable {

        /**
         * A chave da mensagem que será usada para buscar o texto no `MessageSource`.
         */
        private final String key;

        /**
         * Os argumentos que serão aplicados à mensagem para formatação.
         */
        private final String[] args;

    }
}
