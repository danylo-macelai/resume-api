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
package br.com.resume.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * <b>Description:</b> FIXME: Document this type <br>
 * <b>Project:</b> resume-api <br>
 *
 * @author Danylo
 * @date: 12 de mar. de 2024
 * @version $
 */
@ActiveProfiles("test")
@SpringBootTest
class BaseTOTests {

    private static final String PACKAGE_NAME      = "br.com.resume.domain.model";
    private static final String TABLE_NAME_PREFIX = "RSM_";
    private static final String UNIQUE_KEY_PREFIX = "uk_";
    private static final String INDEX_KEY_PREFIX  = "idx_";

    @Test
    void testAllDomainClasses() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(BaseTO.class));
        Set<BeanDefinition> components = scanner.findCandidateComponents(PACKAGE_NAME);
        for (BeanDefinition bean : components) {
            try {
                Class<?> clazz = Class.forName(bean.getBeanClassName());
                validateEntity(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void validateEntity(Class<?> clazz) {
        Entity entityAnnotation = clazz.getAnnotation(Entity.class);
        assertThat(entityAnnotation).isNotNull();

        Table tableAnnotation = clazz.getAnnotation(Table.class);

        assertThat(tableAnnotation).isNotNull();

        String tableName = tableAnnotation.name();

        assertThat(tableName).startsWith(TABLE_NAME_PREFIX);

        String entityName = clazz.getSimpleName();
        String expectedTableName = TABLE_NAME_PREFIX + entityName.toUpperCase();

        assertThat(tableName).isEqualTo(expectedTableName);

        UniqueConstraint[] constraints = tableAnnotation.uniqueConstraints();
        for (UniqueConstraint constraint : constraints) {
            assertThat(constraint.name()).startsWith(UNIQUE_KEY_PREFIX);

            String[] columnNames = constraint.columnNames();
            String expectedConstraintName = buildConstraintName(UNIQUE_KEY_PREFIX, expectedTableName, columnNames);

            assertThat(constraint.name()).isEqualTo(expectedConstraintName);
        }

        Index[] indexes = tableAnnotation.indexes();
        for (Index index : indexes) {
            assertThat(index.name()).startsWith(INDEX_KEY_PREFIX);

            String[] columnList = new String[] { index.columnList() };
            String expectedName = buildConstraintName(INDEX_KEY_PREFIX, expectedTableName, columnList);

            assertThat(index.name()).isEqualTo(expectedName);
        }

    }

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
