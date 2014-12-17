package controllers.swap;

import org.junit.Test;

public class ConnectionExceptionTest {

    @Test(expected = ConnectionException.class)
    public void testConnectionException() throws Exception, ConnectionException {
        throw new ConnectionException("error");
    }

    @Test(expected = InvalidSwapException.class)
    public void testInvalidSwapException() throws Exception, InvalidSwapException {
        throw new InvalidSwapException("error");
    }

}