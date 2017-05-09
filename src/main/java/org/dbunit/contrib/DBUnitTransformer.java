package org.dbunit.contrib;

import java.io.File;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PatternOptionBuilder;

/**
 * A utility class to transform a DBUnit XML file to another format.
 *
 * @author Allon Mureinik (amureini@redhat.com)
 */
public class DBUnitTransformer {

    private static HelpFormatter helpFormatter = new HelpFormatter();

    private static File inFile;
    private static File outFile;

    public static void main(String[] args) {
        DBUnitTransformerExitCodes rc = parseOptions(args);
        if (rc.isError()) {
            System.exit(rc.getExitCode());
        }
    }

    /**
     * Parses the command line arguments.
     *
     * @param args The command line arguments
     * @return The program's exist status, so far
     */
    private static DBUnitTransformerExitCodes parseOptions(String[] args) {
        Options options = new Options();
        options.addOption(
                Option.builder("if")
                        .argName("file")
                        .longOpt("inFile")
                        .hasArg()
                        .required()
                        .desc("input file name")
                        .type(PatternOptionBuilder.FILE_VALUE)
                        .build()
        );
        options.addOption(
                Option.builder("of")
                        .argName("file")
                        .longOpt("outFile")
                        .hasArg()
                        .required()
                        .desc("output file name")
                        .type(PatternOptionBuilder.FILE_VALUE)
                        .build()
        );

        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            inFile = (File) commandLine.getParsedOptionValue("if");
            outFile = (File) commandLine.getParsedOptionValue("of");

            if (!inFile.exists()) {
                String errorMessage = String.format("inFile %s does not exist", inFile.getAbsolutePath());
                PrintWriter errorWriter = new PrintWriter(System.err);
                helpFormatter.printWrapped(errorWriter, errorMessage.length(), errorMessage);
                errorWriter.flush();
                return DBUnitTransformerExitCodes.ERROR_INFILE;
            }
        } catch (ParseException e) {
            helpFormatter.printHelp("java DBUnitTransformer", options);
            return DBUnitTransformerExitCodes.ERROR_ARGS;
        }

        return DBUnitTransformerExitCodes.OK;
    }
}
