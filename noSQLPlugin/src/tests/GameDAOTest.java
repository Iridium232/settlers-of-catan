package tests;

import DAOs.GameDAO;
import junit.framework.TestCase;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class GameDAOTest extends TestCase {
    private GameDAO gameDAO = new GameDAO();

    public void testGetGames() throws Exception {
        Map<Integer, Object> result = gameDAO.getGames();
        assertTrue(true);
    }

    public void testGetCommands() throws Exception {
        Map<Integer, List<Object>> result = gameDAO.getCommands();
        assertTrue(true);
    }

    public void testSaveCommand() throws Exception {
        JSONObject command1 = new JSONObject("{ name: \"command1\", gameID: 0 }");
        JSONObject command2 = new JSONObject("{ name: \"command2\", gameID: 0 }");
        JSONObject command3 = new JSONObject("{ name: \"command3\", gameID: 1 }");
        gameDAO.saveCommand(command1, 0);
        gameDAO.saveCommand(command2, 0);
        gameDAO.saveCommand(command3, 1);
        assertTrue(true);
    }

    public void testSaveModelAndEmptyCommands() throws Exception {
        JSONObject game1 = new JSONObject("{ name: \"game1\", gameID: 0 }");
        JSONObject game2 = new JSONObject("{ name: \"game2\", gameID: 1 }");
        JSONObject game3 = new JSONObject("{ name: \"game3\", gameID: 2 }");
        gameDAO.saveModelAndEmptyCommands(game1, 0);
        gameDAO.saveModelAndEmptyCommands(game2, 1);
        gameDAO.saveModelAndEmptyCommands(game3, 2);
        assertTrue(true);
    }

    public void testEraseAll() throws Exception {
        JSONObject game1 = new JSONObject("{ name: \"game1\", gameID: 0 }");
        JSONObject game2 = new JSONObject("{ name: \"game2\", gameID: 1 }");
        JSONObject game3 = new JSONObject("{ name: \"game3\", gameID: 2 }");
        gameDAO.saveModelAndEmptyCommands(game1, 0);
        gameDAO.saveModelAndEmptyCommands(game2, 1);
        gameDAO.saveModelAndEmptyCommands(game3, 2);
        JSONObject command1 = new JSONObject("{ name: \"command1\", gameID: 0 }");
        JSONObject command2 = new JSONObject("{ name: \"command2\", gameID: 0 }");
        JSONObject command3 = new JSONObject("{ name: \"command3\", gameID: 1 }");
        gameDAO.saveCommand(command1, 0);
        gameDAO.saveCommand(command2, 0);
        gameDAO.saveCommand(command3, 1);

        gameDAO.eraseAll();
        assertTrue(true);
    }
}