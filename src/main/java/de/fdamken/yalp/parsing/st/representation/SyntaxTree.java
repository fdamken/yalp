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
package de.fdamken.yalp.parsing.st.representation;

import java.util.Objects;

import de.fdamken.yalp.parsing.IntermediateRepresentation;
import lombok.Data;

/**
 * Represents the syntax tree.
 *
 */
@Data
public class SyntaxTree implements IntermediateRepresentation {
    /**
     * The root element of the syntax tree.
     *
     */
    private final SyntaxTreeElement rootElement;

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Objects.toString(this.rootElement);
    }
}
