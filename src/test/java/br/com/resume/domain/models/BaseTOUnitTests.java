
/**
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
package br.com.resume.domain.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Classe de teste para validar as anotações e convenções de mapeamento de entidades JPA nas classes do pacote
 * {@link br.com.resume.domain.models}. Essa classe verifica se todas as entidades no pacote possuem as anotações
 * apropriadas, como {@link Entity} e {@link Table}, e valida se os nomes das tabelas, constraints e índices estão
 * formatados corretamente de acordo com os prefixos e convenções estabelecidas.
 *
 * <p>
 * O teste percorre todas as classes que estendem {@link BaseTO}, que são mapeadas como entidades JPA, e valida se elas
 * seguem as convenções de nomenclatura:
 * <ul>
 * <li>O nome da tabela deve começar com "RSM_" e ter o nome da classe em maiúsculas.</li>
 * <li>Os nomes das constraints únicas devem começar com "uk_" e ser gerados com base na tabela e nas colunas.</li>
 * <li>Os nomes dos índices devem começar com "idx_" e ser gerados de maneira semelhante às constraints únicas.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Projeto:</b> resume-api
 * </p>
 *
 * @version 1.0
 * @since 12 de mar. de 2024
 */
@ActiveProfiles("test")
@SpringBootTest
class BaseTOUnitTests {

    /**
     * Lista das classes específicas para validação. Ao invés de escanear todo o pacote, definimos as classes que devem
     * ser testadas.
     */
    private static final List<String> CLASSES_TO_TEST = Arrays.asList(
            "br.com.resume.feature.user.UserTO");

    /**
     * Sufixo esperado para o nome das entidades.
     */
    private static final String CLASS_NAME_SUFFIX = "TO";

    /**
     * Prefixo usado para o nome das tabelas nas entidades.
     */
    private static final String TABLE_NAME_PREFIX = "RSM_";

    /**
     * Prefixo usado para o nome das constraints únicas.
     */
    private static final String UNIQUE_KEY_PREFIX = "uk_";

    /**
     * Prefixo usado para o nome dos índices.
     */
    private static final String INDEX_KEY_PREFIX = "idx_";

    /**
     * Teste que percorre todas as classes definidas na lista {@link CLASSES_TO_TEST} e valida suas anotações, nomes de
     * tabelas, constraints e índices de acordo com as convenções.
     */
    @Test
    void testSpecificDomainClasses() {
        for (String className : CLASSES_TO_TEST) {
            try {
                // Carrega a classe especificada pelo nome
                Class<?> clazz = Class.forName(className);
                // Valida a entidade
                validateEntity(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Valida se uma classe está corretamente anotada como uma entidade JPA e se segue as convenções de nomenclatura
     * estabelecidas para tabelas, constraints e índices.
     *
     * @param clazz A classe a ser validada
     */
    void validateEntity(Class<?> clazz) {
        // Verifica se a classe está anotada com @Entity
        Entity entityAnnotation = clazz.getAnnotation(Entity.class);
        assertThat(entityAnnotation).isNotNull();

        // Verifica se a classe está anotada com @Table
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        assertThat(tableAnnotation).isNotNull();

        // Valida se o nome da entidade termina com o sufixo definido
        String entityName = clazz.getSimpleName();
        assertThat(entityName).endsWith(CLASS_NAME_SUFFIX)
                .withFailMessage("A classe " + entityName + " deve terminar com '" + CLASS_NAME_SUFFIX + "'");

        // Valida o nome da tabela
        String tableName = tableAnnotation.name();
        assertThat(tableName).startsWith(TABLE_NAME_PREFIX);

        // Ajusta o nome da tabela removendo o sufixo da entidade
        String expectedTableName = TABLE_NAME_PREFIX
                + entityName.substring(0, entityName.length() - CLASS_NAME_SUFFIX.length()).toUpperCase();
        assertThat(tableName).isEqualTo(expectedTableName);

        // Valida as constraints únicas
        UniqueConstraint[] constraints = tableAnnotation.uniqueConstraints();
        for (UniqueConstraint constraint : constraints) {
            assertThat(constraint.name()).startsWith(UNIQUE_KEY_PREFIX);

            String[] columnNames = constraint.columnNames();
            String expectedConstraintName = buildConstraintName(UNIQUE_KEY_PREFIX, expectedTableName, columnNames);
            assertThat(constraint.name()).isEqualTo(expectedConstraintName);
        }

        // Valida os índices
        Index[] indexes = tableAnnotation.indexes();
        for (Index index : indexes) {
            assertThat(index.name()).startsWith(INDEX_KEY_PREFIX);

            String[] columnList = new String[] { index.columnList() };
            String expectedName = buildConstraintName(INDEX_KEY_PREFIX, expectedTableName, columnList);
            assertThat(index.name()).isEqualTo(expectedName);
        }
    }

    /**
     * Constrói o nome da constraint ou índice com base no prefixo, nome da tabela e colunas envolvidas. Utiliza a
     * convenção de nomenclatura com o prefixo seguido do nome da tabela e dos nomes das colunas.
     *
     * @param prefix O prefixo para o nome da constraint ou índice
     * @param tableName O nome da tabela associada
     * @param columnNames As colunas associadas à constraint ou índice
     * @return O nome gerado para a constraint ou índice
     */
    String buildConstraintName(String prefix, String tableName, String[] columnNames) {
        StringBuilder constraintName = new StringBuilder(prefix)
                .append(tableName.substring(TABLE_NAME_PREFIX.length()).toLowerCase())
                .append("_");
        for (int i = 0; i < columnNames.length; i++) {
            constraintName.append(columnNames[i]);
            if (i < columnNames.length - 1) {
                constraintName.append("__");
            }
        }
        return constraintName.toString();
    }
}
