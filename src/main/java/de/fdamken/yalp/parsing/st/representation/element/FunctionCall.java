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
package de.fdamken.yalp.parsing.st.representation.element;

import java.util.Arrays;

import de.fdamken.yalp.parsing.st.representation.SyntaxTreeElement;
import lombok.Value;

/**
 * Represents a function call in the syntax tree.
 *
 */
@Value
public class FunctionCall implements SyntaxTreeElement {
    /**
     * The name of the function (that can also be a function!).
     *
     */
    SyntaxTreeElement function;
    /**
     * The parameters of the function.
     *
     */
    SyntaxTreeElement[] parameters;

    /**
     * Constructor of FunctionCall.
     *
     * @param function
     *            See {@link #function}.
     * @param parameters
     *            See {@link #parameters}.
     */
    public FunctionCall(final SyntaxTreeElement function, final SyntaxTreeElement... parameters) {
        this.function = function;
        this.parameters = parameters;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.function + Arrays.toString(this.parameters);
    }
}
