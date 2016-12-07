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

import java.util.Arrays;
import java.util.List;

import de.fdamken.yalp.exec.datatype.exception.DatatypeCreationException;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * This is the abstract class of any data type. A data type is the class and a
 * value of the data type is an instance of a data type class.
 *
 * @param <V>
 *            The data type of the value to save.
 */
@Data
@RequiredArgsConstructor
public abstract class AbstractDatatype<V> {
    /**
     * The name of this data type.
     *
     */
    private final String name;
    /**
     * The saved value.
     *
     */
    private final V value;
    /**
     * All data type classes that this data type can be casted into.
     *
     */
    private final List<Class<? extends AbstractDatatype<?>>> castables;

    /**
     * Constructor of AbstractDatatype.
     *
     * @param name
     *            The {@link #name} to set.
     * @param value
     *            The value to save.
     * @param castables
     *            The {@link #castables} to set.
     */
    @SafeVarargs
    public AbstractDatatype(final String name, final V value, final Class<? extends AbstractDatatype<?>>... castables) {
        this(name, value, Arrays.asList(castables));
    }

    /**
     * Casts this data type into the given data type class. The given data type
     * class must be one of {@link #castables}. If it is not, crazy things may
     * happen.
     *
     * @param <T>
     *            The data type of the data type to be casted into.
     * @param datatypeClass
     *            The data type class to be casted into.
     * @return The casted value.
     */
    protected abstract <T extends AbstractDatatype<?>> T safeCast(final Class<? extends AbstractDatatype<?>> datatypeClass);

    /**
     * Checks whether one data type can be casted into another.
     *
     * <p>
     * Checks whether the following will work (implicit in Lisp, but this is
     * Java code): <code>(ToDatatype) fromDatatype</code>
     * </p>
     *
     * @param fromDatatypeClass
     *            The data type that should be casted.
     * @param toDatatypeClass
     *            The data type that should be casted into.
     * @return Whether the from-data type can be casted into the to-data type.
     */
    public static boolean isCastable(final Class<? extends AbstractDatatype<?>> fromDatatypeClass,
            final Class<? extends AbstractDatatype<?>> toDatatypeClass) {
        return AbstractDatatype.create(fromDatatypeClass).isCastable(toDatatypeClass);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    /**
     * Checks whether this data type instance can be casted into the given data
     * type class.
     *
     * @param datatypeClass
     *            The data type class to be casted into.
     * @return Whether this data type instance can be casted into an instance of
     *         the given data type class.
     */
    public boolean isCastable(final Class<? extends AbstractDatatype<?>> datatypeClass) {
        return this.castables.stream().anyMatch(clazz -> clazz == datatypeClass);
    }

    /**
     * Casts this data type instance into an instance of the given data type
     * class.
     *
     * @param <T>
     *            The data type of the data type.
     * @param datatypeClass
     *            The class of the data type to be casted into.
     * @return The casted data type.
     * @throws ClassCastException
     *             If this data type is not castable into the given data type
     *             class.
     */
    public <T extends AbstractDatatype<?>> T cast(final Class<T> datatypeClass) throws ClassCastException {
        if (!this.isCastable(datatypeClass)) {
            throw new ClassCastException(
                    this.getName() + " cannot be casted into " + AbstractDatatype.create(datatypeClass).getName());
        }

        return this.safeCast(datatypeClass);
    }

    /**
     * Creates an instance of the given data type class.
     *
     * @param <T>
     *            The type of the data type to create.
     * @param clazz
     *            The class of the data type.
     * @return The newly created data type.
     */
    private static <T extends AbstractDatatype<?>> T create(final Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (final InstantiationException | IllegalAccessException ex) {
            throw new DatatypeCreationException("The datatype " + clazz.getCanonicalName() + " is not implemented correctly!",
                    ex);
        }
    }
}
