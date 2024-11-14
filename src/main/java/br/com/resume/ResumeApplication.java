/*
 * MIT License
 *
 * Copyright (c) 2021.
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
package br.com.resume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot para o projeto <b>resume-api</b>. Esta classe inicia a aplicação,
 * configurando e carregando o contexto do Spring.
 *
 * <p>
 * A classe utiliza a anotação {@link SpringBootApplication}, que habilita o uso das configurações padrão do Spring
 * Boot, incluindo escaneamento de componentes, configuração automática e inicialização da aplicação.
 * </p>
 *
 * <p>
 * <b>Projeto:</b> resume-api
 * </p>
 *
 * @see SpringBootApplication
 * @see SpringApplication
 *
 * @author MDanylo
 * @version 1.0
 * @since 6 de nov. de 2021
 */
@SpringBootApplication
public class ResumeApplication {

    /**
     * Método principal da aplicação que executa o Spring Boot.
     *
     * @param args argumentos de linha de comando passados durante a execução da aplicação
     */
    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
    }

}
