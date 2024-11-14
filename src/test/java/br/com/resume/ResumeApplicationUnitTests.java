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

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Classe de teste unitário para a aplicação {@link ResumeApplication}. Este teste tem como objetivo verificar se o
 * contexto da aplicação Spring pode ser carregado corretamente em um ambiente de testes unitários.
 *
 * <p>
 * A anotação {@link SpringBootTest} indica que o teste deve carregar o contexto completo da aplicação Spring para
 * validar sua configuração e inicialização. A anotação {@link ActiveProfiles} ativa o perfil de testes ("test"), o que
 * pode incluir configurações específicas para o ambiente de testes, como bancos de dados em memória, mocks, entre
 * outros.
 * </p>
 *
 * <p>
 * O método {@link #contextLoads()} não realiza nenhuma validação explícita, mas serve para garantir que o contexto da
 * aplicação seja carregado sem falhas. Caso o contexto não consiga ser carregado, o teste falhará automaticamente.
 * </p>
 *
 * <p>
 * <b>Projeto:</b> resume-api
 * </p>
 *
 * @author MDanylo
 * @version 1.0
 * @since 6 de nov. de 2021
 */
@ActiveProfiles("test")
@SpringBootTest
class ResumeApplicationUnitTests {

    /**
     * Teste que verifica se o contexto da aplicação Spring é carregado corretamente.
     *
     * <p>
     * Este método de teste não executa ações explícitas ou verificações, mas é utilizado para garantir que o contexto
     * da aplicação seja inicializado corretamente. Se houver falhas de configuração ou problemas de inicialização do
     * contexto, o Spring lançará exceções e o teste será automaticamente marcado como falho.
     * </p>
     */
    @Test
    void contextLoads() {
    }

}
