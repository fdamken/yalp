/*
 * #%L
 * Yet Another Lisp Parser
 * %%
 * Copyright (C) 2016 - 2016 Fabian Damken
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package de.fdamken.yalp.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fdamken.yalp.ast.ASTParser;
import de.fdamken.yalp.ast.ListElementContainer;
import de.fdamken.yalp.ast.exception.ParsingException;
import lombok.RequiredArgsConstructor;

/**
 * This class is used to parse and execute Lisp code.
 *
 */
@RequiredArgsConstructor
public class LispExecutor {
    /**
     * The logger.
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LispExecutor.class);

    /**
     * The Abstract Syntax Tree (AST) that is about to be executed.
     *
     */
    private final ListElementContainer ast;

    /**
     * Parses the Lisp code that is returned by the given (non-closed!)
     * {@link Reader reader} and creates a {@link LispExecutor executor}.
     *
     * @param in
     *            The {@link Reader reader} to read the Lisp code from.
     * @return The {@link LispExecutor executor}.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    public static LispExecutor makeExecutor(final Reader in) throws ParsingException {
        return new LispExecutor(ASTParser.parse(in));
    }

    /**
     * Parses the Lisp code that is returned by the given (non-closed!)
     * {@link InputStream input stream} and creates a {@link LispExecutor
     * executor}.
     *
     * @param in
     *            The {@link InputStream input stream} to read the Lisp code
     *            from.
     * @param charset
     *            The character set to be used.
     * @return The {@link LispExecutor executor}.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    public static LispExecutor makeExecutor(final InputStream in, final Charset charset) throws ParsingException {
        return LispExecutor.makeExecutor(new InputStreamReader(in, charset));
    }

    /**
     * Parses the Lisp code that is returned by the given (non-closed!)
     * {@link InputStream input stream} and creates a {@link LispExecutor
     * executor}.
     *
     * @param in
     *            The {@link InputStream input stream} to read the Lisp code
     *            from.
     * @return The {@link LispExecutor executor}.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    public static LispExecutor makeExecutor(final InputStream in) throws ParsingException {
        return LispExecutor.makeExecutor(new InputStreamReader(in, Charset.defaultCharset()));
    }

    /**
     * Parses the given Lisp code and creates a {@link LispExecutor executor}.
     *
     * @param str
     *            The Lisp code.
     * @return The {@link LispExecutor executor}.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    public static LispExecutor makeExecutor(final String str) throws ParsingException {
        return LispExecutor.makeExecutor(new StringReader(str));
    }

    /**
     * Executes the stored AST.
     *
     * @param stdin
     *            The {@link InputStream input stream} to be used as the
     *            standard input stream.
     * @param stdout
     *            The {@link OutputStream output stream} to be used as the
     *            standard output stream.
     * @param errout
     *            The {@link OutputStream output stream} to be used as the error
     *            output stream.
     */
    public void execute(final InputStream stdin, final OutputStream stdout, final OutputStream errout) {
        try (final BufferedReader in = stdin == null ? null
                : new BufferedReader(new InputStreamReader(stdin, Charset.defaultCharset()));
                final PrintStream out = stdout == null ? null : new PrintStream(stdout, true);
                final PrintStream err = errout == null ? null : new PrintStream(errout, true)) {
            this.internalExecute(in, out, err);
        } catch (final IOException ex) {
            LispExecutor.LOGGER.error("Failed to close stdin/stdout/errout!", ex);
        }
    }

    /**
     * Internal method for executing the AST.
     *
     * @param in
     *            The standard input. May be <code>null</code>.
     * @param out
     *            The standard output. May be <code>null</code>.
     * @param err
     *            The error output. May be <code>null</code>.
     */
    private void internalExecute(final BufferedReader in, final PrintStream out, final PrintStream err) {
        // Magic goes here.
    }
}
