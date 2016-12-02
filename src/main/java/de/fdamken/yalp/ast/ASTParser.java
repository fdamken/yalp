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
package de.fdamken.yalp.ast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;

import de.fdamken.yalp.ast.exception.ParsingException;
import lombok.experimental.UtilityClass;

/**
 * This class parsed Lisp code and returns it as an AST.
 *
 */
@UtilityClass
public class ASTParser {
    /**
     * Parses the Lisp code that is returned by the given (non-closed!)
     * {@link Reader reader}.
     *
     * @param in
     *            The {@link Reader reader} to read the Lisp code from.
     * @return The AST.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    public static ListElementContainer parse(final Reader in) throws ParsingException {
        try {
            return ASTParser.internalParse(in);
        } catch (final IOException cause) {
            throw new ParsingException("An error occurred whilst parsing the Lisp code!", cause);
        }
    }

    /**
     * Parses the Lisp code that is returned by the given (non-closed!)
     * {@link InputStream input stream}.
     *
     * @param in
     *            The {@link Reader reader} to read the Lisp code from.
     * @param charset
     *            The character set to be used.
     * @return The AST.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    public static ListElementContainer parse(final InputStream in, final Charset charset) throws ParsingException {
        return ASTParser.parse(new InputStreamReader(in, charset));
    }

    /**
     * Parses the Lisp code that is returned by the given (non-closed!)
     * {@link InputStream input stream}.
     *
     * @param in
     *            The {@link Reader reader} to read the Lisp code from.
     * @return The AST.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    public static ListElementContainer parse(final InputStream in) throws ParsingException {
        return ASTParser.parse(new InputStreamReader(in, Charset.defaultCharset()));
    }

    /**
     * Parses the given Lisp code.
     *
     * @param str
     *            The Lisp code from.
     * @return The AST.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    public static ListElementContainer parse(final String str) throws ParsingException {
        return ASTParser.parse(new StringReader(str));
    }

    /**
     * Parses the Lisp code that is returned by the given (non-closed!)
     * {@link Reader reader}.
     *
     * @param in
     *            The {@link Reader reader} to read the Lisp code from.
     * @return The AST.
     * @throws IOException
     *             If an I/O error occurs.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    private static ListElementContainer internalParse(final Reader in) throws IOException, ParsingException {
        final BufferedReader reader = new BufferedReader(in);

        final ASTBuilder builder = new ASTBuilder();

        StringBuilder elementBuilder = new StringBuilder();
        boolean escape = false;
        boolean comment = false;
        boolean string = false;

        int value = -1;
        while ((value = reader.read()) > -1) {
            final char control = (char) value;

            final boolean execute;
            if (escape) {
                elementBuilder.append(control);
                escape = false;

                execute = false;
            } else if (control == ';') {
                comment = true;

                execute = false;
            } else if (comment && (control == '\n' || control == '\r')) {
                comment = false;

                execute = false;
            } else if (control == '\\') {
                escape = true;

                execute = false;
            } else if (control == '"') {
                elementBuilder.append('"');
                if (string) {
                    builder.addElement(elementBuilder.toString());
                    elementBuilder = new StringBuilder();
                }
                string = !string;

                execute = false;
            } else if (string) {
                elementBuilder.append(control);

                execute = false;
            } else {
                execute = true;
            }

            if (!comment && execute) {
                final boolean isOpeningBracket = control == '(' || control == '{' || control == '[';
                final boolean isClosingBracket = control == ')' || control == '}' || control == ']';
                final boolean isWhitespace = Character.isWhitespace(control);

                if ((isOpeningBracket || isClosingBracket || isWhitespace) && elementBuilder.length() > 0) {
                    builder.addElement(elementBuilder.toString());
                    elementBuilder = new StringBuilder();
                }
                if (isOpeningBracket) {
                    builder.openContainer();
                } else if (isClosingBracket) {
                    builder.closeContainer();
                } else if (!isWhitespace) {
                    elementBuilder.append(control);
                }
            }
        }

        if (!builder.isFinished()) {
            throw new ParsingException("Not all brackets are closed!");
        }

        return builder.getAST();
    }
}
