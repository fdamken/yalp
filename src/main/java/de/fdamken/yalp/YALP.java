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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.fdamken.yalp.ast.exception.ParsingException;
import de.fdamken.yalp.exec.LispExecutor;

/**
 * This is the main class of YALP (Yet Another Lisp Parser) that can be
 * executed.
 *
 */
public class YALP {
    /**
     * The main method of YALP.
     *
     * @param args
     *            The command line arguments.
     * @throws ParseException
     *             If any error occurs whilst parsing the CLI arguments.
     * @throws IOException
     *             If any I/O error occurs.
     * @throws ParsingException
     *             If any error occurs whilst parsing the Lisp code.
     */
    public static void main(final String[] args) throws ParseException, IOException, ParsingException {
        final Options options = new Options();
        options.addOption("h", "help", false, "Print help.");
        options.addOption("i", "input-file", true, "File to execute.");

        final CommandLine cmd = new DefaultParser().parse(options, args);
        if (cmd.hasOption('h')) {
            new HelpFormatter().printHelp("yalp", options);
        } else {
            final Reader reader;
            if (cmd.hasOption('i')) {
                reader = Files.newBufferedReader(Paths.get(cmd.getOptionValue('i')));
            } else {
                reader = new InputStreamReader(System.in);
            }
            // This may cause errors when reading the code from System.in and
            // using input methods.
            LispExecutor.makeExecutor(reader).execute(System.in, System.out, System.err);
        }
    }
}
