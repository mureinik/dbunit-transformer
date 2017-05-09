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
 * A container for the DBUnit Transformer's program command line interaction.
 *
 * @author Allon Mureinik (amureini@redhat.com)
 */
public class DBUnitTransformerCLI {
    private HelpFormatter helpFormatter = new HelpFormatter();

    private File inFile;
    private File outFile;

    /**
     * Parses the command line arguments.
     *
     * @param args The command line arguments
     */
    public DBUnitTransformerCLI(String[] args) {
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

        DBUnitTransformerExitCodes exitCode = DBUnitTransformerExitCodes.OK;
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
                exitCode = DBUnitTransformerExitCodes.ERROR_INFILE;
            }
        } catch (ParseException e) {
            helpFormatter.printHelp("java DBUnitTransformer", options);
            exitCode = DBUnitTransformerExitCodes.ERROR_ARGS;
        }

        exitOnError(exitCode);
    }

    public File getInFile() {
        return inFile;
    }

    public File getOutFile() {
        return outFile;
    }

    private void exitOnError(DBUnitTransformerExitCodes exitCode) {
        if (exitCode.isError()) {
            System.exit(exitCode.getExitCode());
        }
    }
}
