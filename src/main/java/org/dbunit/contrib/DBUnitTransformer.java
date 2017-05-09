package org.dbunit.contrib;

/**
 * A utility class to transform a DBUnit XML file to another format.
 *
 * @author Allon Mureinik (amureini@redhat.com)
 */
public class DBUnitTransformer {
    public static void main(String[] args) {
        DBUnitTransformerCLI cli = new DBUnitTransformerCLI(args);
    }
}
