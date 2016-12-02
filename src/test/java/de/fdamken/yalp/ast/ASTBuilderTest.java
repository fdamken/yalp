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
package de.fdamken.yalp.ast;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ASTBuilderTest {
    @Test
    public void testNoContainer() {
        final ASTBuilder builder = new ASTBuilder();

        this.checkFinished(builder);

        this.checkAST(builder, new ListElementContainer());
    }

    @Test
    public void testOneContainer() {
        final ASTBuilder builder = new ASTBuilder();

        builder.openContainer();

        this.checkNotFinished(builder);

        builder.closeContainer();

        this.checkFinished(builder);

        this.checkAST(builder, new ListElementContainer());
    }

    @Test
    public void testTwoContainer() {
        final ASTBuilder builder = new ASTBuilder();

        builder.openContainer();
        builder.openContainer();

        this.checkNotFinished(builder);

        builder.closeContainer();
        builder.closeContainer();

        this.checkFinished(builder);

        final ListElementContainer expected = new ListElementContainer();
        final ListElementContainer container = new ListElementContainer();
        expected.addElement(container);
        this.checkAST(builder, expected);
    }

    @Test
    public void testOneElement() {
        final ASTBuilder builder = new ASTBuilder();

        builder.openContainer();
        builder.addElement("Hello");

        this.checkNotFinished(builder);

        builder.closeContainer();

        this.checkFinished(builder);

        final ListElementContainer expected = new ListElementContainer();
        expected.addElement(new SimpleListElement("Hello"));
        this.checkAST(builder, expected);
    }

    @Test
    public void testTwoElement() {
        final ASTBuilder builder = new ASTBuilder();

        builder.openContainer();
        builder.addElement("Hello");
        builder.addElement("World");

        this.checkNotFinished(builder);

        builder.closeContainer();

        this.checkFinished(builder);

        final ListElementContainer expected = new ListElementContainer();
        expected.addElement(new SimpleListElement("Hello"));
        expected.addElement(new SimpleListElement("World"));
        this.checkAST(builder, expected);
    }

    @Test
    public void testComplex() {
        // The following represents the building of the AST of the following
        // Lisp Code:
        // (sqrt (+ (* a a) (* b b)))

        final ASTBuilder builder = new ASTBuilder();

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

        final ListElementContainer containerSqrt = new ListElementContainer();
        containerSqrt.addElement(new SimpleListElement("sqrt"));
        final ListElementContainer containerPlus = new ListElementContainer();
        containerSqrt.addElement(containerPlus);
        containerPlus.addElement(new SimpleListElement("+"));
        final ListElementContainer containerA = new ListElementContainer();
        containerPlus.addElement(containerA);
        containerA.addElement(new SimpleListElement("*"));
        containerA.addElement(new SimpleListElement("a"));
        containerA.addElement(new SimpleListElement("a"));
        final ListElementContainer containerB = new ListElementContainer();
        containerPlus.addElement(containerB);
        containerB.addElement(new SimpleListElement("*"));
        containerB.addElement(new SimpleListElement("b"));
        containerB.addElement(new SimpleListElement("b"));
        this.checkAST(builder, containerSqrt);
    }

    private void checkNotFinished(final ASTBuilder builder) {
        Assert.assertFalse("The builder must not be finished!", builder.isFinished());
    }

    private void checkFinished(final ASTBuilder builder) {
        Assert.assertTrue("The builder must be finished!", builder.isFinished());
    }

    private void checkAST(final ASTBuilder builder, final ListElementContainer expected) {
        Assert.assertEquals("The built AST does not match the expected AST!", expected, builder.getAST());
    }
}
