/*
 * MIT License
 *
 * Copyright (c) 2022.
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
 * Classe de teste de integração para o contexto da aplicação {@link ResumeApplication}. Este teste é responsável por
 * garantir que o contexto da aplicação Spring seja carregado corretamente em um ambiente de testes.
 *
 * <p>
 * A anotação {@link SpringBootTest} indica que o teste deve carregar o contexto completo da aplicação Spring para
 * verificar se os beans são inicializados corretamente. A anotação {@link ActiveProfiles} define que o perfil "test"
 * deve ser ativado para esses testes, permitindo configurações específicas de teste, como banco de dados em memória,
 * configurações de mock, entre outros.
 * </p>
 *
 * <p>
 * Este teste não executa uma verificação específica, mas serve como uma validação simples de que o contexto da
 * aplicação pode ser carregado sem falhas.
 * </p>
 *
 * <p>
 * <b>Projeto:</b> resume-api
 * </p>
 *
 * @author MDanylo
 * @version 1.0
 * @since 1 de jan. de 2022
 */
@ActiveProfiles("test")
@SpringBootTest
class ResumeApplicationIntegrationTests {

    /**
     * Teste que verifica se o contexto da aplicação Spring é carregado corretamente.
     *
     * <p>
     * Este teste não realiza nenhuma ação explícita, mas é executado para garantir que o Spring consiga carregar o
     * contexto completo da aplicação. Se o contexto não carregar corretamente, o teste falhará.
     * </p>
     */
    @Test
    void contextLoads() {
    }

}
