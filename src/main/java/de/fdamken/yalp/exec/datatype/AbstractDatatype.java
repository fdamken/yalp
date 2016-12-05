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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the abstract class of any data type. A data type is the class and a
 * value of the data type is an instance of a data type class.
 *
 */
public abstract class AbstractDatatype {
    /**
     * The logger.
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatatype.class);

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
    public static boolean isCastable(final Class<? extends AbstractDatatype> fromDatatypeClass,
            final Class<? extends AbstractDatatype> toDatatypeClass) {
        try {
            return fromDatatypeClass.newInstance().isCastable(toDatatypeClass);
        } catch (final InstantiationException | IllegalAccessException ex) {
            AbstractDatatype.LOGGER
                    .error("The datatype " + fromDatatypeClass.getCanonicalName() + " is not implemented correctly!", ex);

            return false;
        }
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
    public abstract boolean isCastable(final Class<? extends AbstractDatatype> datatypeClass);

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
    public abstract <T extends AbstractDatatype> T cast(final Class<T> datatypeClass) throws ClassCastException;
}
