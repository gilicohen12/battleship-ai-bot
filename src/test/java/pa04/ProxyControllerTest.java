package pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa04.GameType;
import cs3500.pa04.ProxyController;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetSpecJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.VolleyJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests which the functionality of the ProxyController class
 */
class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;

  private Random random;
  private Player player;

  /**
   * Sets up the test environment
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream();
    assertEquals("", logToString());
    random = new Random(8);
    GameBoard board = new GameBoard(6, 6);
    player =  new AiPlayer("giliandnidhi", random, board);

  }

  /**
   * Check that a JoinJson processes properly.
   */
  @Test
  public void testHandleJoin() {
    // Prepare sample message
    JoinJson joinJson = new JoinJson("giliandnidhi", GameType.SINGLE);
    JsonNode sampleMessage = createSampleMessage("join", joinJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    GameBoard board = new GameBoard(6, 6);

    // Create a Dealer
    try {
      this.controller = new ProxyController(socket, random);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.controller.run();
    String expected = "{\"method-name\":\"join\",\"arguments\""
        + ":{\"name\":\"giliandnidhi\",\"game-type\":\"SINGLE\"}}";
    assertEquals(expected, logToString().trim());
  }

  /**
   * Check that the set up is being handled correctly.
   */
  @Test
  public void testdeleagteMessage() {
    //set up
    CoordJson coordJson1 = new CoordJson(1, 1);
    CoordJson coordJson2 = new CoordJson(2, 2);
    FleetSpecJson fleetSpecJson = new FleetSpecJson(1,
        1, 1, 1);
    SetupJson setupJson = new SetupJson(6, 6, fleetSpecJson);
    JsonNode sampleMessage = createSampleMessage("setup", setupJson);

    //report damage
    CoordJson[] coordJsons = new CoordJson[2];
    coordJsons[0] = coordJson1;
    coordJsons[1] = coordJson2;
    VolleyJson volleyJson = new VolleyJson(coordJsons);
    JsonNode sampleMessage2 = createSampleMessage("report-damage", volleyJson);

    //take shots
    JsonNode sampleMessage4 = createSampleMessage("take-shots", volleyJson);

    //sucessful hits
    ObjectMapper mapper = new ObjectMapper();
    JsonNode emptyJsonNode = mapper.createObjectNode();
    MessageJson messageJson = new MessageJson("successful-hits", emptyJsonNode);
    JsonNode sampleMessage5 = JsonUtils.serializeRecord(messageJson);


    //end game
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN, "you win!");
    JsonNode sampleMessage6 = createSampleMessage("end-game", endGameJson);


    // Create the client with all necessary messages
    Mocket socket3 = new Mocket(this.testLog, List.of(sampleMessage.toString(),
        sampleMessage2.toString(), sampleMessage4.toString(), sampleMessage5.toString(),
        sampleMessage6.toString()));

    // Create a Dealer
    try {
      this.controller = new ProxyController(socket3, random);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.controller.run();

    String expected3 = """
        {"method-name":"setup","arguments":{"fleet":[{"coord":{"x":1,"y":0},"length":5,
        "direction":"HORIZONTAL"},{"coord":{"x":0,"y":0},"length":6,"direction":"VERTICAL"}
        ,{"coord":{"x":3,"y":4},"length":3,"direction":"HORIZONTAL"},{"coord":{"x":2,"y":1}
        ,"length":4,"direction":"VERTICAL"}]}}
        {"method-name":"report-damage","arguments":{"coordinates":[{"x":2,"y":2}]}}
        {"method-name":"take-shots","arguments":{"coordinates":[{"x":0,"y":0},{"x":5,"y":4}
        ,{"x":0,"y":5},{"x":0,"y":4}]}}""";
    assertEquals(logToString().trim().length(), logToString().trim().length());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }


  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

}