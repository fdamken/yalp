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
package de.fdamken.yalp.syntaxtree.compile;

import java.util.ArrayList;
import java.util.List;

import de.fdamken.yalp.AbstractCompilationStep;
import de.fdamken.yalp.exception.CompilationException;
import de.fdamken.yalp.syntaxtree.representation.SyntaxTree;
import de.fdamken.yalp.syntaxtree.representation.SyntaxTreeElement;
import de.fdamken.yalp.syntaxtree.representation.element.FunctionCall;
import de.fdamken.yalp.syntaxtree.representation.element.Literal;
import de.fdamken.yalp.tokentree.representation.SimpleTokenTreeElement;
import de.fdamken.yalp.tokentree.representation.TokenTree;
import de.fdamken.yalp.tokentree.representation.TokenTreeContainer;
import de.fdamken.yalp.tokentree.representation.TokenTreeElement;

/**
 * The syntax tree parser for converting the token tree into a syntax tree (not
 * an AST!).
 *
 */
public class SyntaxTreeParser extends AbstractCompilationStep<TokenTree, SyntaxTree> {
    /**
     * {@inheritDoc}
     *
     * @see de.fdamken.yalp.AbstractCompilationStep#parse(de.fdamken.yalp.IntermediateRepresentation)
     */
    @Override
    public SyntaxTree parse(final TokenTree tokenTree) throws CompilationException {
        return new SyntaxTree(this.parse(tokenTree.getRootContainer()));
    }

    /**
     * Parses the given token tree element into a syntax tree element.
     *
     * @param tokenTree
     *            The token tree to parse.
     * @return The parsed syntax tree.
     * @throws CompilationException
     *             If an error occurs during the compilation.
     */
    private SyntaxTreeElement parse(final TokenTreeElement tokenTree) throws CompilationException {
        if (tokenTree instanceof TokenTreeContainer) {
            return this.parseFunctionCall((TokenTreeContainer) tokenTree);
        } else if (tokenTree instanceof SimpleTokenTreeElement) {
            return this.parseLiteral((SimpleTokenTreeElement) tokenTree);
        } else {
            throw new CompilationException("Unknown token tree element type " + tokenTree.getClass().getSimpleName() + "!");
        }
    }

    /**
     * Parses the given container as a function call.
     *
     * @param tokenTreeContainer
     *            The token tree container to parse.
     * @return The parsed function call.
     * @throws CompilationException
     *             If an error occurs during the compilation.
     */
    private FunctionCall parseFunctionCall(final TokenTreeContainer tokenTreeContainer) throws CompilationException {
        final List<SyntaxTreeElement> functionContent = new ArrayList<>();
        for (final TokenTreeElement tokenTreeElement : tokenTreeContainer) {
            functionContent.add(this.parse(tokenTreeElement));
        }
        if (functionContent.isEmpty()) {
            throw new CompilationException("Missing parameters for function call!");
        }
        return new FunctionCall(functionContent.remove(0),
                functionContent.toArray(new SyntaxTreeElement[functionContent.size()]));
    }

    /**
     * Parses the given element as a literal call.
     *
     * @param tokenTreeElement
     *            The token tree element to parse.
     * @return The parsed function call.
     */
    private Literal parseLiteral(final SimpleTokenTreeElement tokenTreeElement) {
        return new Literal(tokenTreeElement.getContent());
    }
}
