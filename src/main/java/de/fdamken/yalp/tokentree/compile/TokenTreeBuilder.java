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
package de.fdamken.yalp.tokentree.compile;

import java.util.ArrayDeque;
import java.util.Deque;

import de.fdamken.yalp.tokentree.representation.SimpleTokenTreeElement;
import de.fdamken.yalp.tokentree.representation.TokenTree;
import de.fdamken.yalp.tokentree.representation.TokenTreeContainer;

/**
 * A builder used for building an TT in a flat way.
 *
 */
public class TokenTreeBuilder {
    /**
     * The parent containers of the current container. This is used for
     * {@link #closeContainer()}.
     *
     */
    private final Deque<TokenTreeContainer> parentContainers = new ArrayDeque<>();

    /**
     * The root container that is returned by {@link #getTokenTree()}.
     *
     */
    private final TokenTreeContainer rootContainer = new TokenTreeContainer();
    /**
     * The current container where new elements will be added to.
     *
     */
    private TokenTreeContainer currentContainer;

    /**
     * Constructor of TokenTreeBuilder.
     *
     */
    public TokenTreeBuilder() {
        // Nothing to do.
    }

    /**
     * Opens a new container where the new elements will be added to.
     *
     */
    public void openContainer() {
        if (this.currentContainer == null) {
            this.currentContainer = this.rootContainer;
        } else {
            this.parentContainers.push(this.currentContainer);

            final TokenTreeContainer container = new TokenTreeContainer();
            this.currentContainer.addElement(container);
            this.currentContainer = container;
        }
    }

    /**
     * Closes the current container. If there is no open container, this does
     * nothing.
     *
     */
    public void closeContainer() {
        if (this.parentContainers.size() > 0) {
            this.currentContainer = this.parentContainers.pop();
        } else {
            this.currentContainer = null;
        }
    }

    /**
     * Adds the given content to the current container.
     *
     * @param content
     *            The content to add to the current container.
     * @throws IllegalStateException
     *             If no container is open.
     */
    public void addElement(final String content) throws IllegalStateException {
        if (this.currentContainer == null) {
            throw new IllegalStateException("There is no open container!");
        }

        this.currentContainer.addElement(new SimpleTokenTreeElement(content));
    }

    /**
     *
     * @return Whether the building was finished.
     */
    public boolean isFinished() {
        return this.currentContainer == null;
    }

    /**
     *
     * @return Returns the current token tree.
     */
    public TokenTree getTokenTree() {
        return new TokenTree(this.rootContainer.copy());
    }
}
