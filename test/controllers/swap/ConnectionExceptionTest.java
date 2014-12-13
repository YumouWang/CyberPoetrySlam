package controllers.swap;

import org.junit.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;

import static org.junit.Assert.*;

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