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
package de.fdamken.yalp.exec.datatype;

/**
 * A {@link YalpSymbol YALP Symbol} represents a symbol (a very simple string
 * with no blanks or similar) in Lisp.
 *
 */
public class YalpSymbol extends AbstractDatatype<String> {
    /**
     * Constructor of YaspSymbol.
     *
     * @param value
     *            The value to save.
     */
    public YalpSymbol(final String value) {
        super("symbol", value, YalpString.class);
    }

    /**
     * Constructor of YalpSymbol.
     *
     */
    public YalpSymbol() {
        this(null);
    }

    /**
     * {@inheritDoc}
     *
     * @see de.fdamken.yalp.exec.datatype.AbstractDatatype#safeCast(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected <T extends AbstractDatatype<?>> T safeCast(final Class<? extends AbstractDatatype<?>> datatypeClass) {
        if (datatypeClass == YalpString.class) {
            return (T) new YalpString(this.getValue());
        }

        return null;
    }
}
