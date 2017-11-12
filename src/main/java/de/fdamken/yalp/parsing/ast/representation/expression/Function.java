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
package de.fdamken.yalp.parsing.ast.representation.expression;

import de.fdamken.yalp.parsing.ast.representation.Expression;

/**
 * A function is an expression that must be evaluated and produces a result.
 *
 */
public interface Function extends Expression {
    /**
     *
     * @return The minimum parameter count.
     */
    int getMinParameterCount();

    /**
     *
     * @return The maximum parameter count or <code>-1</code> for infinity.
     */
    int getMaxParameterCount();
}
