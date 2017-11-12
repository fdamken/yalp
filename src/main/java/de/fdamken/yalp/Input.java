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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import lombok.Getter;

/**
 * The root representation that represents the code itself.
 *
 */
public class Input implements IntermediateRepresentation {
    /**
     * The reader that contains the code.
     *
     */
    @Getter
    private final Reader reader;

    /**
     * Constructor of Input.
     *
     * @param reader
     *            The reader that contains the code.
     */
    public Input(final Reader reader) {
        this.reader = reader;
    }

    /**
     * Constructor of Input.
     *
     * @param in
     *            The input stream that contains the code.
     * @param cs
     *            The character set of the input stream.
     */
    public Input(final InputStream in, final Charset cs) {
        this(new InputStreamReader(in, cs));
    }
}
