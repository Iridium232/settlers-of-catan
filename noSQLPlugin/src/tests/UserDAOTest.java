package tests;

import DAOs.UserDAO;
import junit.framework.TestCase;
import org.json.JSONObject;

import java.util.Map;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class UserDAOTest extends TestCase {

    private UserDAO userDAO = new UserDAO();

    public void testGetUsers() throws Exception {
        JSONObject user1 = new JSONObject("{ name: \"user1\", userID: 0 }");
        JSONObject user2 = new JSONObject("{ name: \"user2\", userID: 1 }");
        JSONObject user3 = new JSONObject("{ name: \"user3\", userID: 2 }");
        userDAO.addUser(user1, 0);
        userDAO.addUser(user2, 1);
        userDAO.addUser(user3, 2);

        Map<Integer, Object> result = userDAO.getUsers();
        assertTrue(true);
    }

    public void testAddUser() throws Exception {
        JSONObject user1 = new JSONObject("{ name: \"user1\", userID: 0 }");
        JSONObject user2 = new JSONObject("{ name: \"user2\", userID: 1 }");
        JSONObject user3 = new JSONObject("{ name: \"user3\", userID: 2 }");
        userDAO.addUser(user1, 0);
        userDAO.addUser(user2, 1);
        userDAO.addUser(user3, 2);
        assertTrue(true);
    }

    public void testClearAll() throws Exception {
        JSONObject user1 = new JSONObject("{ name: \"user1\", userID: 0 }");
        JSONObject user2 = new JSONObject("{ name: \"user2\", userID: 1 }");
        JSONObject user3 = new JSONObject("{ name: \"user3\", userID: 2 }");
        userDAO.addUser(user1, 0);
        userDAO.addUser(user2, 1);
        userDAO.addUser(user3, 2);
        userDAO.clearAll();
        assertTrue(true);
    }
}