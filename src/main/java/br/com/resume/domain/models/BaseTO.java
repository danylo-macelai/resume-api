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
package br.com.resume.domain.models;

import static jakarta.persistence.GenerationType.AUTO;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe base abstrata que representa um Transfer Object (TO) comum para as entidades da aplicação. Esta classe é
 * utilizada para definir a estrutura básica de objetos de transferência de dados que podem ser clonados, comparados e
 * transformados em uma representação de string de maneira padrão.
 *
 * <p>
 * A classe contém um identificador único (`id`), que é gerado automaticamente. Além disso, implementa métodos comuns
 * como `clone()`, `equals()`, `hashCode()`, e `toString()`, oferecendo uma funcionalidade básica e reutilizável para
 * suas subclasses.
 * </p>
 *
 * <p>
 * A anotação {@link MappedSuperclass} indica que esta classe não é uma entidade no banco de dados, mas suas
 * propriedades podem ser herdadas por outras entidades.
 * </p>
 *
 * <p>
 * <b>Projeto:</b> resume-api
 * </p>
 *
 * @author MDanylo
 * @version 1.0
 * @since 3 de mar. de 2024
 */
@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTO implements Serializable, Cloneable {

    /**
     * Identificador único da entidade, gerado automaticamente.
     *
     * <p>
     * Este campo é utilizado como a chave primária da entidade em seu respectivo banco de dados.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    /**
     * Realiza um clone da instância atual, criando uma cópia independente do objeto.
     *
     * @return Uma nova instância do objeto clonado.
     * @throws CloneNotSupportedException Caso a clonagem não seja suportada.
     */
    @Override
    public final BaseTO clone() throws CloneNotSupportedException {
        return ObjectUtils.cloneIfPossible(this);
    }

    /**
     * Compara a instância atual com outro objeto para verificar se são iguais. A comparação é baseada no campo `id` da
     * entidade.
     *
     * @param other O objeto a ser comparado com a instância atual.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public final boolean equals(Object other) {
        if ((other == null) || !this.getClass().equals(other.getClass())) {
            return false;
        }
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(id, ((BaseTO) other).id);
        return builder.isEquals();
    }

    /**
     * Retorna o código hash da instância atual. Este método é sobrescrito para garantir que o código hash seja
     * consistente com o método `equals`.
     *
     * @return O código hash da instância.
     */
    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    /**
     * Retorna uma representação em string da instância, incluindo seus campos e valores. A representação é gerada
     * utilizando reflexão, excluindo o prefixo de campos.
     *
     * @return Uma string representando a instância.
     */
    @Override
    public final String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }

}
