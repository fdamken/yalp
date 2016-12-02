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

import de.fdamken.yalp.ast.exception.ParsingException;

@SuppressWarnings("javadoc")
public class ASTParserTest {
    @Test
    public void testSimple() throws ParsingException {
        final ASTBuilder builder = new ASTBuilder();
        builder.openContainer();
        builder.addElement("+");
        builder.addElement("1");
        builder.addElement("2");
        builder.closeContainer();

        this.check("(+ 1 2)", builder);
    }

    @Test
    public void testComplex() throws ParsingException {
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

        this.check("(sqrt (+ (* a a) (* b b)))", builder);
    }

    private void check(final String code, final ASTBuilder builder) throws ParsingException {
        Assert.assertEquals("The built AST does not match the expected AST!", builder.getAST(), ASTParser.parse(code));
    }
}
