package org.dbunit.contrib;

/**
 * Exit codes for the DBUnit Transformer program.
 *
 * @author Allon Mureinik (amureini@redhat.com)
 */
public enum DBUnitTransformerExitCodes {
    OK(0),
    ERROR_ARGS(1),
    ERROR_INFILE(2),
    ERROR_INFILE_READ(3),
    ERROR_OUTFILE_WRITE(4);

    private int rc;

    DBUnitTransformerExitCodes(int rc) {
        this.rc = rc;
    }

    public int getExitCode() {
        return rc;
    }

    public boolean isOK() {
        return this == OK;
    }

    public boolean isError() {
        return !isOK();
    }
}
