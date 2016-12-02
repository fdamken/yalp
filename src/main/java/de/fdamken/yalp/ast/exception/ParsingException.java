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
package de.fdamken.yalp.ast.exception;

/**
 * This exception is thrown if any error happens whilst parsing some Lisp code.
 *
 */
public class ParsingException extends Exception {
    /**
     * The serial version UID.
     *
     */
    private static final long serialVersionUID = -7186407377972049167L;

    /**
     * Constructor of ParsingException.
     *
     * @param message
     *            A detailed error message.
     * @param cause
     *            The error causing this exception.
     */
    public ParsingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor of ParsingException.
     *
     * @param message
     *            A detailed error message.
     */
    public ParsingException(final String message) {
        super(message);
    }

    /**
     * Constructor of ParsingException.
     *
     * @param cause
     *            The error causing this exception.
     */
    public ParsingException(final Throwable cause) {
        super(cause);
    }
}
