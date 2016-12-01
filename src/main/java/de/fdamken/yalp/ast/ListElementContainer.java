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
package de.fdamken.yalp.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

/**
 * The {@link ListElementContainer list element container} contains other
 * {@link ListElement list elements} and therefore represents the brackets in
 * Lisp (e.g. <code>(+ 1 2)</code> creates a {@link ListElementContainer list
 * element container} with the {@link SimpleListElement simple list elements}
 * <code>+</code>, <code>1</code> and <code>2</code>).
 *
 */
@Data
public class ListElementContainer implements ListElement {
    /**
     * The stored elements of this container.
     *
     */
    private final List<ListElement> elements;

    /**
     * Constructor of ListElementContainer.
     *
     * <p>
     * <b> NOTE: Do not use this constructor directly! Use the
     * {@link ListElementContainer#builder() builder} instead! </b>
     * </p>
     *
     * @param elements
     *            The elements to be stored. This creates an unmodifiable list.
     */
    private ListElementContainer(final List<ListElement> elements) {
        this.elements = Collections.unmodifiableList(elements);
    }

    /**
     * Instantiates a new builder for the {@link ListElementContainer list
     * element container}.
     *
     * @return A newly created builder for the {@link ListElementContainer list
     *         element container}.
     */
    public static ListElementContainerBuilder builder() {
        return new ListElementContainerBuilder();
    }

    /**
     * The {@link ListElementContainerBuilder list element container builder}
     * used for building {@link ListElementContainer list element containers}.
     *
     */
    public static class ListElementContainerBuilder {
        /**
         * The elements to be stored in the {@link ListElementContainer list
         * element container}.
         *
         */
        private final List<ListElement> elements = new ArrayList<>();

        /**
         * Constructor of ListElementContainer.ListElementContainerBuilder.
         *
         */
        public ListElementContainerBuilder() {
            // Nothing to do.
        }

        /**
         * Adds the given element to the list of elements to be stored.
         *
         * @param element
         *            The element to add.
         */
        public void addElement(final ListElement element) {
            this.elements.add(element);
        }

        /**
         * Builds the actual {@link ListElementContainer list element
         * container}.
         *
         * @return The new {@link ListElementContainer list element container}.
         */
        public ListElementContainer build() {
            return new ListElementContainer(this.elements);
        }
    }
}
