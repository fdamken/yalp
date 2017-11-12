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
package de.fdamken.yalp.tokentree.compile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import de.fdamken.yalp.AbstractCompilationStep;
import de.fdamken.yalp.Input;
import de.fdamken.yalp.exception.CompilationException;
import de.fdamken.yalp.tokentree.exception.ParsingException;
import de.fdamken.yalp.tokentree.representation.TokenTree;

/**
 * This class parsed Lisp code and returns it as an TT.
 *
 */
public class TokenTreeParser extends AbstractCompilationStep<Input, TokenTree> {
    /**
     * {@inheritDoc}
     *
     * @see de.fdamken.yalp.AbstractCompilationStep#parse(de.fdamken.yalp.IntermediateRepresentation)
     */
    @Override
    public TokenTree parse(final Input input) throws CompilationException {
        try {
            return this.internalParse(input);
        } catch (final IOException cause) {
            throw new CompilationException("Error whilst doing I/O things.", cause);
        }
    }

    /**
     * Parses the Lisp code that is returned by the given (non-closed!)
     * {@link Reader reader}.
     *
     * @param input
     *            The code.
     * @return The token tree.
     * @throws IOException
     *             If an I/O error occurs.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    private TokenTree internalParse(final Input input) throws IOException, ParsingException {
        final BufferedReader reader = new BufferedReader(input.getReader());

        final TokenTreeBuilder builder = new TokenTreeBuilder();

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

        return builder.getTokenTree();
    }
}
