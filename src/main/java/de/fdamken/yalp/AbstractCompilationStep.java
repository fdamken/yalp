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
package de.fdamken.yalp;

import de.fdamken.yalp.exception.CompilationException;
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
     * The input representation of the code.
     *
     */
    protected final I input;
    /**
     * The output representation of the code.
     *
     */
    protected O output;

    /**
     * Parses the input and produces the output.
     *
     * @throws CompilationException
     *             If an error occurs during the compilation.
     */
    public abstract void parse() throws CompilationException;

    /**
     * Checks whether the parsing has finished. This is usually the case after
     * {@link #parse()} was invoked.
     *
     * @return Whether the parsing has been finished or not.
     */
    public abstract boolean isFinished();

    /**
     *
     * @return {@link #output}.
     * @throws CompilationException
     *             If the parsing was not finished (so call
     *             {@link #isFinished()} first).
     */
    public O getOutput() throws CompilationException {
        if (!this.isFinished()) {
            throw new CompilationException("The parsing has not yet finished!");
        }

        return this.output;
    }
}
