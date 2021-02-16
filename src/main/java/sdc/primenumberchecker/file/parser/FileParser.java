package sdc.primenumberchecker.file.parser;

public interface FileParser {

    /** This method process entered file - load numbers from file and check, if they are prime numbers
     *
     * @param filename
     */
    void processFile(String filename);
}
