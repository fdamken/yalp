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
 * A {@link YalpString YALP String} represents a string in Lisp.
 *
 */
public class YalpString extends AbstractDatatype<String> {
    /**
     * Constructor of YalpString.
     *
     * @param value
     *            The value to save.
     */
    public YalpString(final String value) {
        super("string", value);
    }

    /**
     * Constructor of YalpString.
     *
     */
    public YalpString() {
        this(null);
    }

    /**
     * {@inheritDoc}
     *
     * @see de.fdamken.yalp.exec.datatype.AbstractDatatype#safeCast(java.lang.Class)
     */
    @Override
    protected <T extends AbstractDatatype<?>> T safeCast(final Class<? extends AbstractDatatype<?>> datatypeClass) {
        return null;
    }
}
