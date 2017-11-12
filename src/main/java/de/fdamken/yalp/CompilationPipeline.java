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

import java.util.Arrays;
import java.util.List;

import de.fdamken.yalp.exception.CompilationException;

/**
 * Represents a simple compilation pipeline to pass the code thru all steps of
 * the compilation. This reduces the overhead when using it.
 *
 * <p>
 * <b> NOTE:
 * <ul>
 * You
 * </ul>
 * have to take care of {@link ClassCastException ClassCastExceptions}, etc.
 * that can happen if your pipeline is not well defined. </b>
 * </p>
 *
 * @param <I>
 *            The input type.
 * @param <O>
 *            The output type.
 */
public class CompilationPipeline<I extends IntermediateRepresentation, O extends IntermediateRepresentation>
        extends AbstractCompilationStep<I, O> {
    /**
     * The steps of the compilation to bring <code>I</code> to <code>O</code>.
     *
     */
    private final List<AbstractCompilationStep<?, ?>> compilationSteps;

    /**
     * Constructor of CompilationPipeline.
     *
     * @param compilationSteps
     *            The {@link #compilationSteps} to set.
     */
    public CompilationPipeline(final AbstractCompilationStep<?, ?>... compilationSteps) {
        this.compilationSteps = Arrays.asList(compilationSteps);
    }

    /**
     * {@inheritDoc}
     *
     * @see de.fdamken.yalp.AbstractCompilationStep#parse(de.fdamken.yalp.IntermediateRepresentation)
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public O parse(final I input) throws CompilationException {
        Object current = input;
        for (final AbstractCompilationStep<?, ?> compilationStep : this.compilationSteps) {
            current = compilationStep.parseDanger(current);
        }
        return (O) current;
    }
}
