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
package de.fdamken.yalp.tokentree.representation;

/**
 * One {@link SimpleTokenTreeElement list element} represents a single element
 * of the Lisp programming language and can be either a
 * {@link TokenTreeContainer list element container} or a
 * {@link TokenTreeElement simple list element}. The {@link TokenTreeContainer
 * list element container} is used for storing other {@link TokenTreeElement
 * list elements}, both {@link TokenTreeContainer list element containers} and
 * {@link TokenTreeElement simple list elements}.
 *
 * <p>
 * This is used to represent the basic TT of the List programming language as
 * Lisp is a list-based programming language.
 * </p>
 * <br>
 * <p>
 * For example: <br>
 * Given the following Lisp code:
 *
 * <pre>
 * <code>
 *     (* (+ 1 2) 3)
 * </code>
 * </pre>
 *
 * Which will be parsed into the following ASL:
 *
 * <pre>
 * <code>
 *     LEC:
 *         SLE: *
 *         LEC:
 *             SLE: +
 *             SLE: 1
 *             SLE: 2
 *         SLE: 3
 * </code>
 * </pre>
 * </p>
 *
 */
public interface TokenTreeElement {
    /**
     * Executes a deep copy of the {@link SimpleTokenTreeElement list element}.
     *
     * @return The deep copy of the {@link SimpleTokenTreeElement list element}.
     */
    TokenTreeElement copy();
}
