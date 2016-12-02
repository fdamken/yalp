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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
public class ListElementContainer implements ListElement, Iterable<ListElement> {
    /**
     * The stored elements of this container.
     *
     */
    private final List<ListElement> elements;

    /**
     * Constructor of ListElementContainer.
     *
     */
    public ListElementContainer() {
        this(new ArrayList<>());
    }

    /**
     * Constructor of ListElementContainer.
     *
     * @param elements
     *            The elements to set.
     */
    private ListElementContainer(final List<ListElement> elements) {
        this.elements = elements;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<ListElement> iterator() {
        // Create an unmodifiable list as iterating loops are able to remove
        // elements from the initial list.
        return Collections.unmodifiableList(this.elements).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @see de.fdamken.yalp.ast.ListElement#copy()
     */
    @Override
    public ListElementContainer copy() {
        return new ListElementContainer(this.elements.stream().map(ListElement::copy).collect(Collectors.toList()));
    }

    /**
     * Adds the given {@link ListElement list element} to the stored elements.
     *
     * @param element
     *            The {@link ListElement list element} to add.
     */
    public void addElement(final ListElement element) {
        this.elements.add(element);
    }
}
