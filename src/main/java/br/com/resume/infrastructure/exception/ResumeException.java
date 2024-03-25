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
package br.com.resume.infrastructure.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * <b>Description:</b> FIXME: Document this type <br>
 * <b>Project:</b> resume-api <br>
 *
 * @author Danylo
 * @date: 12 de mar. de 2024
 * @version $
 */
@SuppressWarnings("serial")
public class ResumeException extends ResponseStatusException {

    private final String   key;
    private final String[] args;

    public ResumeException(HttpStatus status, String key) {
        this(status, key, new Object[] {});
    }

    public ResumeException(HttpStatus status, String key, Object... args) {
        super(status);
        this.key = key;
        this.args = Arrays.stream(args).map(String::valueOf).toArray(String[]::new);
    }

    public String getKey() {
        return key;
    }

    public String[] getArgs() {
        return args;
    }

}
