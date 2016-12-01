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

import lombok.Value;

/**
 * A {@link SimpleListElement simple list element} is a {@link ListElement list
 * element} that contains a single value that can be either an identifier, a
 * number, etc.. It does not contain any sub-nodes and that is why it is
 * "simple".
 *
 */
@Value
public class SimpleListElement implements ListElement {
    /**
     * The content of this element.
     *
     */
    String content;
}
