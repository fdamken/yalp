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
package de.fdamken.yalp.exec.operation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.fdamken.yalp.exec.datatype.AbstractDatatype;
import lombok.Getter;

/**
 * An operation is a named procedure that is build into the interpreter to allow
 * basic programs and the building of libraries.
 *
 * <p>
 * An operation may have parameters which types are stored in
 * {@link #parameterTypes}. The last parameter may be a vararg.
 * </p>
 *
 */
@Getter
public abstract class AbstractOperation {
    /**
     * The named of this operation.
     *
     */
    private final String name;
    /**
     * The (ordered!) of the accepted parameter types of this operation.
     *
     */
    private final List<Class<? extends AbstractDatatype>> parameterTypes;
    /**
     * Whether the last parameter is a vararg.
     *
     */
    private final boolean vararg;

    /**
     * Constructor of AbstractOperation.
     *
     * @param name
     *            The name of the operation.
     * @param vararg
     *            Whether the last parameter is a vararg.
     * @param parameterTypes
     *            The types of the parameters.
     */
    @SafeVarargs
    public AbstractOperation(final String name, final boolean vararg, final Class<? extends AbstractDatatype>... parameterTypes) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (vararg && parameterTypes.length < 1) {
            throw new IllegalArgumentException("The last parameter can not be a vararg if there is not parameter!");
        }

        this.name = name;
        this.parameterTypes = parameterTypes == null ? Collections.emptyList()
                : Collections.unmodifiableList(Arrays.asList(parameterTypes));
        this.vararg = vararg;
    }
}
