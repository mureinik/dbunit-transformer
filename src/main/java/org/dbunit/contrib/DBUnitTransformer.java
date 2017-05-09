package org.dbunit.contrib;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.dataset.xml.XmlDataSet;

/**
 * A utility class to transform a DBUnit XML file to another format.
 *
 * @author Allon Mureinik (amureini@redhat.com)
 */
public class DBUnitTransformer {
    public static void main(String[] args) {
        DBUnitTransformerCLI cli = new DBUnitTransformerCLI(args);

        IDataSet dataSet = null;
        try (FileInputStream inFileStream = new FileInputStream(cli.getInFile())) {
            dataSet = new XmlDataSet(inFileStream);
        } catch (IOException | DataSetException e) {
            cli.exitOnError(
                    DBUnitTransformerExitCodes.ERROR_INFILE_READ,
                    String.format("Cannot read inFile %s", cli.getInFile().getAbsolutePath())
            );
        }

        try (FileOutputStream outFileStream = new FileOutputStream(cli.getOutFile())) {
            FlatXmlWriter outFileWriter = new FlatXmlWriter(outFileStream);
            outFileWriter.setPrettyPrint(true);
            outFileWriter.write(dataSet);
        } catch (IOException | DataSetException e) {
            cli.exitOnError(
                    DBUnitTransformerExitCodes.ERROR_OUTFILE_WRITE,
                    String.format("Cannot write to outFile %s", cli.getOutFile().getAbsolutePath())
            );
        }
    }
}
