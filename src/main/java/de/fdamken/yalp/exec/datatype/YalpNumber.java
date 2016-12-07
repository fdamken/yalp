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
 * A {@link YalpNumber YALP Number} represents a floating point number in Lisp.
 *
 */
public class YalpNumber extends AbstractDatatype<Double> {
    /**
     * Constructor of YalpNumber.
     *
     * @param value
     *            The value to safe.
     */
    public YalpNumber(final Double value) {
        super("number", value, YalpString.class, YalpSymbol.class);
    }

    /**
     * Constructor of YalpNumber.
     *
     */
    public YalpNumber() {
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
            return (T) new YalpString(String.valueOf(this.getValue()));
        }
        if (datatypeClass == YalpSymbol.class) {
            return (T) new YalpSymbol(String.valueOf(this.getValue()));
        }

        return null;
    }
}
