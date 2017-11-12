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

import org.junit.Assert;
import org.junit.Test;

import de.fdamken.yalp.CompilationPipeline;
import de.fdamken.yalp.Input;
import de.fdamken.yalp.exception.CompilationException;
import de.fdamken.yalp.syntaxtree.representation.SyntaxTree;
import de.fdamken.yalp.syntaxtree.representation.element.FunctionCall;
import de.fdamken.yalp.syntaxtree.representation.element.Literal;
import de.fdamken.yalp.tokentree.compile.TokenTreeParser;

@SuppressWarnings("javadoc")
public class SyntaxTreeParserTest {
    private static final CompilationPipeline<Input, SyntaxTree> COMPILER = new CompilationPipeline<>(new TokenTreeParser(),
            new SyntaxTreeParser());

    @Test
    public void testSimple() throws CompilationException {
        final FunctionCall rootElement = new FunctionCall(new Literal("+"), new Literal("1"), new Literal("2"));

        this.check("(+ 1 2)", new SyntaxTree(rootElement));
    }

    @Test
    public void testComplex() throws CompilationException {
        final FunctionCall rootElement = new FunctionCall(new Literal("sqrt"), //
                new FunctionCall(new Literal("+"), //
                        new FunctionCall(new Literal("*"), new Literal("a"), new Literal("a")), //
                        new FunctionCall(new Literal("*"), new Literal("b"), new Literal("b")) //
                ));

        this.check("(sqrt (+ (* a a) (* b b)))", new SyntaxTree(rootElement));
    }

    @Test
    public void testVeryComplex() throws CompilationException {
        final FunctionCall rootElement = new FunctionCall(new Literal("define"), //
                new FunctionCall(new Literal("add-interest"), new Literal("capital"), new Literal("interest")), //
                new FunctionCall(new Literal("cond"), //
                        new FunctionCall(new FunctionCall(new Literal("<"), new Literal("interest"), new Literal("0")), //
                                new FunctionCall(new Literal("error"), new Literal("'add-interest"),
                                        new Literal("\"no negative interest rates allowed\"")) //
                        ), //
                        new FunctionCall(new FunctionCall(new Literal("<="), new Literal("capital"), new Literal("0")), //
                                new Literal("capital") //
                        ), new FunctionCall(new Literal("else"), //
                                new FunctionCall(new Literal("*"), //
                                        new Literal("capital"), //
                                        new FunctionCall(new Literal("+"), new Literal("interest"), new Literal("1")) //
                                ) //
                        ) //
                ));

        this.check("(define (add-interest capital interest)" //
                + "(cond" //
                + "[(< interest 0) (error 'add-interest \"no negative interest rates allowed\")]" //
                + "[(<= capital 0) capital]" //
                + "[else (* capital (+ interest 1))]" //
                + "))", new SyntaxTree(rootElement));
    }

    @Test
    public void testSimpleComment() throws CompilationException {
        final FunctionCall rootElement = new FunctionCall(new Literal("+"), new Literal("1"), new Literal("2"));

        this.check("; Hello, World!\n(+ 1 2)", new SyntaxTree(rootElement));
    }

    private void check(final String code, final SyntaxTree syntaxTree) throws CompilationException {
        final SyntaxTree compiledSyntaxTree = SyntaxTreeParserTest.COMPILER.parse(new Input(code));
        Assert.assertEquals("The built syntax tree does not match the expected syntax tree!", syntaxTree, compiledSyntaxTree);
    }
}
