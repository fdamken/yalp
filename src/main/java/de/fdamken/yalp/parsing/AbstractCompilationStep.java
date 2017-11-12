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
package de.fdamken.yalp.parsing;

import de.fdamken.yalp.parsing.exception.CompilationException;
import lombok.RequiredArgsConstructor;

/**
 * Defines a compilation step in the compilation queue.
 *
 * @param <I>
 *            The input representation type of the code.
 * @param <O>
 *            The output representation type of the code.
 */
@RequiredArgsConstructor
public abstract class AbstractCompilationStep<I extends IntermediateRepresentation, O extends IntermediateRepresentation> {
    /**
     * Parses the input and produces the output.
     *
     * @param input
     *            The input.
     * @return The output.
     * @throws CompilationException
     *             If an error occurs during the compilation.
     */
    public abstract O parse(final I input) throws CompilationException;

    /**
     * Dangerous method to parse input using casting.
     *
     * <p>
     * <b> NOTE: Do not use this unless you really know what you are doing! </b>
     * </p>
     *
     * @param input
     *            The input.
     * @return The output.
     * @throws CompilationException
     *             Compiler error.
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    final Object parseDanger(final Object input) throws CompilationException {
        return this.parse((I) input);
    }
}
