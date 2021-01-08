package tests;

import networker.NetworkController;
import networker.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringParsingTests {

    @Test()
    void parsingCommandWorks() {
        Request request = null;
        String query1 = "create?hbv&John&15.5";
        request = NetworkController.parseQueryString(query1);
        assertEquals("create", request.getCommand());

        String query2 = "";
        request = NetworkController.parseQueryString(query2);
        assertEquals("", request.getCommand());

        String query3 = "create?";
        request = NetworkController.parseQueryString(query3);
        assertEquals("create", request.getCommand());

        String query4 = "create";
        request = NetworkController.parseQueryString(query4);
        assertEquals("create", request.getCommand());
    }

    @Test
    void parsingArgumentsWorks() {
        Request request = null;
        String query1 = "create?0&John&15.5";
        request = NetworkController.parseQueryString(query1);
        assertArrayEquals(new String[]{"0", "John", "15.5"}, request.getArgs().toArray());

        String query2 = "create?";
        request = NetworkController.parseQueryString(query2);
        assertEquals(0, request.getArgs().size());

        String query3 = "create";
        request = NetworkController.parseQueryString(query3);
        assertEquals(0, request.getArgs().size());

        String query5 = "create?1&John&15.5&";
        request = NetworkController.parseQueryString(query5);
        assertArrayEquals(new String[]{"1", "John", "15.5"}, request.getArgs().toArray());
    }
}
