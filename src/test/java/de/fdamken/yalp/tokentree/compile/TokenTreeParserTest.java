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

import org.junit.Assert;
import org.junit.Test;

import de.fdamken.yalp.parsing.CompilationPipeline;
import de.fdamken.yalp.parsing.Input;
import de.fdamken.yalp.parsing.exception.CompilationException;
import de.fdamken.yalp.parsing.tt.compile.TokenTreeBuilder;
import de.fdamken.yalp.parsing.tt.compile.TokenTreeParser;
import de.fdamken.yalp.parsing.tt.representation.TokenTree;

@SuppressWarnings("javadoc")
public class TokenTreeParserTest {
    private static final CompilationPipeline<Input, TokenTree> COMPILER = new CompilationPipeline<>(new TokenTreeParser());

    @Test
    public void testSimple() throws CompilationException {
        final TokenTreeBuilder builder = new TokenTreeBuilder();
        builder.openContainer();
        builder.addElement("+");
        builder.addElement("1");
        builder.addElement("2");
        builder.closeContainer();

        this.check("(+ 1 2)", builder.getTokenTree());
    }

    @Test
    public void testComplex() throws CompilationException {
        final TokenTreeBuilder builder = new TokenTreeBuilder();
        builder.openContainer();
        builder.addElement("sqrt");
        builder.openContainer();
        builder.addElement("+");
        builder.openContainer();
        builder.addElement("*");
        builder.addElement("a");
        builder.addElement("a");
        builder.closeContainer();
        builder.openContainer();
        builder.addElement("*");
        builder.addElement("b");
        builder.addElement("b");
        builder.closeContainer();
        builder.closeContainer();
        builder.closeContainer();

        this.check("(sqrt (+ (* a a) (* b b)))", builder.getTokenTree());
    }

    @Test
    public void testVeryComplex() throws CompilationException {
        final TokenTreeBuilder builder = new TokenTreeBuilder();
        builder.openContainer();
        builder.addElement("define");
        builder.openContainer();
        builder.addElement("add-interest");
        builder.addElement("capital");
        builder.addElement("interest");
        builder.closeContainer();
        builder.openContainer();
        builder.addElement("cond");
        builder.openContainer();
        builder.openContainer();
        builder.addElement("<");
        builder.addElement("interest");
        builder.addElement("0");
        builder.closeContainer();
        builder.openContainer();
        builder.addElement("error");
        builder.addElement("'add-interest");
        builder.addElement("\"no negative interest rates allowed\"");
        builder.closeContainer();
        builder.closeContainer();
        builder.openContainer();
        builder.openContainer();
        builder.addElement("<=");
        builder.addElement("capital");
        builder.addElement("0");
        builder.closeContainer();
        builder.addElement("capital");
        builder.closeContainer();
        builder.openContainer();
        builder.addElement("else");
        builder.openContainer();
        builder.addElement("*");
        builder.addElement("capital");
        builder.openContainer();
        builder.addElement("+");
        builder.addElement("interest");
        builder.addElement("1");
        builder.closeContainer();
        builder.closeContainer();
        builder.closeContainer();
        builder.closeContainer();
        builder.closeContainer();

        this.check("(define (add-interest capital interest)" //
                + "(cond" //
                + "[(< interest 0) (error 'add-interest \"no negative interest rates allowed\")]" //
                + "[(<= capital 0) capital]" //
                + "[else (* capital (+ interest 1))]" //
                + "))", builder.getTokenTree());
    }

    @Test
    public void testSimpleComment() throws CompilationException {
        final TokenTreeBuilder builder = new TokenTreeBuilder();
        builder.openContainer();
        builder.addElement("+");
        builder.addElement("1");
        builder.addElement("2");
        builder.closeContainer();

        this.check("; Hello, World!\n(+ 1 2)", builder.getTokenTree());
    }

    private void check(final String code, final TokenTree tokenTree) throws CompilationException {
        final TokenTree compiledTokenTree = TokenTreeParserTest.COMPILER.parse(new Input(code));
        Assert.assertEquals("The built token tree does not match the expected token tree!", tokenTree, compiledTokenTree);
    }
}
