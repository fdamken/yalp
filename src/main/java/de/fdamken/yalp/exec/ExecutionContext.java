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
package de.fdamken.yalp.exec;

import java.util.HashMap;
import java.util.Map;

import de.fdamken.yalp.exec.datatype.AbstractDatatype;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExecutionContext {
    /**
     * The parent of this execution context.
     *
     */
    @Getter
    private final ExecutionContext parent;

    /**
     * The values that are stored in this execution context.
     *
     */
    private final Map<String, AbstractDatatype> values = new HashMap<>();

    /**
     * Constructor of ExecutionContext.
     *
     * <p>
     * This creates an {@link ExecutionContext execution context} with no
     * parent.
     * </p>
     *
     */
    public ExecutionContext() {
        this(null);
    }

    /**
     * Defines a value with the given name.
     *
     * @param name
     *            The name to define.
     * @param value
     *            The value to associate the name with.
     * @return <code>true</code> if there was no conflict whilst defining the
     *         value, <code>false</code> is an existing definition was
     *         overwritten.
     */
    public boolean defineValue(final String name, final AbstractDatatype value) {
        return this.values.put(name, value) == null;
    }

    /**
     * Finds the value with the given name in the current execution context or
     * in the parent context, respectively.
     *
     * <p>
     * This implements the value scoping.
     * </p>
     *
     * @param name
     *            The name of the value to look up.
     * @param lookupParent
     *            whether the parent execution context should be used for
     *            looking up the value.
     * @return The value that is associated with the given name, if any.
     *         Otherwise <code>null</code>.
     */
    public AbstractDatatype lookupValue(final String name, final boolean lookupParent) {
        AbstractDatatype value = this.values.get(name);
        if (lookupParent && value == null && this.parent != null) {
            value = this.parent.lookupValue(name, true);
        }
        return value;
    }

    /**
     * Finds the value with the given name in the current execution context or
     * in the parent context, respectively.
     *
     * <p>
     * This implements the value scoping.
     * </p>
     *
     * @param name
     *            The name of the value to look up.
     * @return The value that is associated with the given name, if any.
     *         Otherwise <code>null</code>.
     */
    public AbstractDatatype lookupValue(final String name) {
        return this.lookupValue(name, true);
    }

    /**
     * Aggregates the values of this execution context with the values of the
     * parent execution context and returns the map.
     *
     * @return The aggregated value map.
     */
    public Map<String, AbstractDatatype> getValues() {
        final Map<String, AbstractDatatype> result = this.parent == null ? new HashMap<>()
                : new HashMap<>(this.parent.getValues());
        this.values.forEach((key, value) -> result.put(key, value));
        return result;
    }
}
