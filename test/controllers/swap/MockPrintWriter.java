package controllers.swap;

import java.io.PrintWriter;

/**
 * @author Nathan
 * @version 12/12/2014
 */
public class MockPrintWriter extends PrintWriter {

    String lastOut;

    public MockPrintWriter() {
        super(System.out);
    }

    public void println(String responseString) {
        lastOut = responseString;
    }
}
