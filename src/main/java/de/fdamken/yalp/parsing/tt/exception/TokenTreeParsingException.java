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
package de.fdamken.yalp.parsing.tt.exception;

import de.fdamken.yalp.parsing.exception.CompilationException;

@SuppressWarnings("javadoc")
public class TokenTreeParsingException extends CompilationException {
    private static final long serialVersionUID = -7186407377972049167L;

    public TokenTreeParsingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TokenTreeParsingException(final String message) {
        super(message);
    }
}
