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

import de.fdamken.yalp.tokentree.representation.SimpleTokenTreeElement;
import de.fdamken.yalp.tokentree.representation.TokenTreeContainer;

@SuppressWarnings("javadoc")
public class TokenTreeBuilderTest {
    @Test
    public void testNoContainer() {
        final TokenTreeBuilder builder = new TokenTreeBuilder();

        this.checkFinished(builder);

        this.checkAST(builder, new TokenTreeContainer());
    }

    @Test
    public void testOneContainer() {
        final TokenTreeBuilder builder = new TokenTreeBuilder();

        builder.openContainer();

        this.checkNotFinished(builder);

        builder.closeContainer();

        this.checkFinished(builder);

        this.checkAST(builder, new TokenTreeContainer());
    }

    @Test
    public void testTwoContainer() {
        final TokenTreeBuilder builder = new TokenTreeBuilder();

        builder.openContainer();
        builder.openContainer();

        this.checkNotFinished(builder);

        builder.closeContainer();
        builder.closeContainer();

        this.checkFinished(builder);

        final TokenTreeContainer expected = new TokenTreeContainer();
        final TokenTreeContainer container = new TokenTreeContainer();
        expected.addElement(container);
        this.checkAST(builder, expected);
    }

    @Test
    public void testOneElement() {
        final TokenTreeBuilder builder = new TokenTreeBuilder();

        builder.openContainer();
        builder.addElement("Hello");

        this.checkNotFinished(builder);

        builder.closeContainer();

        this.checkFinished(builder);

        final TokenTreeContainer expected = new TokenTreeContainer();
        expected.addElement(new SimpleTokenTreeElement("Hello"));
        this.checkAST(builder, expected);
    }

    @Test
    public void testTwoElement() {
        final TokenTreeBuilder builder = new TokenTreeBuilder();

        builder.openContainer();
        builder.addElement("Hello");
        builder.addElement("World");

        this.checkNotFinished(builder);

        builder.closeContainer();

        this.checkFinished(builder);

        final TokenTreeContainer expected = new TokenTreeContainer();
        expected.addElement(new SimpleTokenTreeElement("Hello"));
        expected.addElement(new SimpleTokenTreeElement("World"));
        this.checkAST(builder, expected);
    }

    @Test
    public void testComplex() {
        // The following represents the building of the AST of the following
        // Lisp Code:
        // (sqrt (+ (* a a) (* b b)))

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

        this.checkFinished(builder);

        final TokenTreeContainer containerSqrt = new TokenTreeContainer();
        containerSqrt.addElement(new SimpleTokenTreeElement("sqrt"));
        final TokenTreeContainer containerPlus = new TokenTreeContainer();
        containerSqrt.addElement(containerPlus);
        containerPlus.addElement(new SimpleTokenTreeElement("+"));
        final TokenTreeContainer containerA = new TokenTreeContainer();
        containerPlus.addElement(containerA);
        containerA.addElement(new SimpleTokenTreeElement("*"));
        containerA.addElement(new SimpleTokenTreeElement("a"));
        containerA.addElement(new SimpleTokenTreeElement("a"));
        final TokenTreeContainer containerB = new TokenTreeContainer();
        containerPlus.addElement(containerB);
        containerB.addElement(new SimpleTokenTreeElement("*"));
        containerB.addElement(new SimpleTokenTreeElement("b"));
        containerB.addElement(new SimpleTokenTreeElement("b"));
        this.checkAST(builder, containerSqrt);
    }

    private void checkNotFinished(final TokenTreeBuilder builder) {
        Assert.assertFalse("The builder must not be finished!", builder.isFinished());
    }

    private void checkFinished(final TokenTreeBuilder builder) {
        Assert.assertTrue("The builder must be finished!", builder.isFinished());
    }

    private void checkAST(final TokenTreeBuilder builder, final TokenTreeContainer expected) {
        Assert.assertEquals("The built AST does not match the expected AST!", expected,
                builder.getTokenTree().getRootContainer());
    }
}
