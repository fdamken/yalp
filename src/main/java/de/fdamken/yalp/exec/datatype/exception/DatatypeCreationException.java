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
package de.fdamken.yalp.exec.datatype.exception;

import de.fdamken.yalp.exec.datatype.AbstractDatatype;

/**
 * This error occurs whilst creating a {@link AbstractDatatype data type} using
 * reflection.
 *
 */
public class DatatypeCreationException extends RuntimeException {
    /**
     * The serial version UID.
     *
     */
    private static final long serialVersionUID = 8396031430307460844L;

    /**
     * Constructor of DatatypeCreationException.
     *
     * @param message
     *            A detailed error message.
     * @param cause
     *            The error causing this exception.
     */
    public DatatypeCreationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor of DatatypeCreationException.
     *
     * @param message
     *            A detailed error message.
     */
    public DatatypeCreationException(final String message) {
        super(message);
    }
}
