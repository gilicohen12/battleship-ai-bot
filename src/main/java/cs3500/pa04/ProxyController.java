package cs3500.pa04;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.GameBoardInterface;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.FleetSpecJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.VolleyJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a proxy controller that communicates with a server using JSON messages.
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private Player player;
  private final ObjectMapper mapper = new ObjectMapper();
  private final String githubUsername;
  private final Random random;
  private GameResult result;

  /**
   * Instantiates a ProxyController object with the specified server and random generator.
   *
   * @param server The socket representing the server connection.
   * @param random The random object used for AI player actions.
   * @throws IOException if an I/O error occurs when creating the input or output stream.
   */
  public ProxyController(Socket server, Random random) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    githubUsername = "giliandnidhi";
    this.random = random;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);

        delegateMessage(message);
      }
    } catch (IOException e) {
      //  Disconnected from server or parsing exception
    }
    System.out.println(result);
  }

  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);

    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }


  /**
   * Handles the "join" message from the server.
   * Sends a "join" message to the server with the player's username and game type.
   */
  private void handleJoin() {
    GameType gameType = GameType.SINGLE;

    JoinJson joinJson = new JoinJson(githubUsername, gameType);
    JsonNode jsonResponse = JsonUtils.serializeRecord(joinJson);
    MessageJson messageJson = new MessageJson("join", jsonResponse);
    JsonNode messageResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(messageResponse);
  }

  /**
   * Handles the "setup" message from the server.
   * Sets up the player's game board and fleet based on the received setup arguments.
   *
   * @param arguments The JSON node containing the setup arguments.
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupArgs = this.mapper.convertValue(arguments, SetupJson.class);
    int height = setupArgs.height();
    int width = setupArgs.width();
    GameBoardInterface board = new GameBoard(height, width);
    player = new AiPlayer(githubUsername, random, board);

    List<Ship> fleet = getPlayerSetup(setupArgs);
    FleetJson fleetJson = turnFleetIntoFleetJson(fleet);
    JsonNode jsonResponse = JsonUtils.serializeRecord(fleetJson);
    MessageJson messageJson = new MessageJson("setup", jsonResponse);
    JsonNode messageResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(messageResponse);
  }

  /**
   * Retrieves the player's fleet setup based on the received setup arguments.
   *
   * @param setupArgs The setup arguments containing fleet specifications.
   * @return The list of ships representing the player's fleet.
   */
  private List<Ship> getPlayerSetup(SetupJson setupArgs) {
    FleetSpecJson fleetSpecArgs = this.mapper.convertValue(setupArgs.fleetSpec(),
        FleetSpecJson.class);

    // add a map for specifications
    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, fleetSpecArgs.carrierCount());
    fleet.put(ShipType.BATTLESHIP, fleetSpecArgs.battleshipCount());
    fleet.put(ShipType.DESTROYER, fleetSpecArgs.destroyerCount());
    fleet.put(ShipType.SUBMARINE, fleetSpecArgs.submarineCount());
    // should output the ships we want and convert them into a json fleet
    return this.player.setup(setupArgs.height(), setupArgs.width(), fleet);
  }

  /**
   * Handles the "take-shots" message from the server.
   * Takes shots on the opponent's game board and sends the volley as a JSON response.
   */
  private void handleTakeShots() {
    List<Coord> shotsTaken = player.takeShots();

    CoordJson[] coords = turnCoordsIntoCoordJsons(shotsTaken);

    VolleyJson volleyJson = new VolleyJson(coords);
    JsonNode jsonResponse = JsonUtils.serializeRecord(volleyJson);
    MessageJson messageJson = new MessageJson("take-shots", jsonResponse);
    JsonNode messageResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(messageResponse);
  }

  /**
   * Handles the "report-damage" message from the server.
   * Reports the opponent's shots and returns the coordinates of the player's hits as a JSON
   * response.
   *
   * @param arguments The JSON node containing the opponent's shot coordinates.
   */
  private void handleReportDamage(JsonNode arguments) {
    VolleyJson reportDamageArgs = this.mapper.convertValue(arguments, VolleyJson.class);
    CoordJson[] shotsTakenByOpponentJson = reportDamageArgs.coordinates();

    Coord[] shotsTakenByOpponent = turnCoordsJsonIntoCoords(shotsTakenByOpponentJson);

    List<Coord> shotsHit = player.reportDamage(new ArrayList<>(List.of(shotsTakenByOpponent)));

    CoordJson[] coordJsons = turnCoordsIntoCoordJsons(shotsHit);

    VolleyJson volleyJson = new VolleyJson(coordJsons);
    JsonNode jsonResponse = JsonUtils.serializeRecord(volleyJson);
    MessageJson messageJson = new MessageJson("report-damage", jsonResponse);
    JsonNode messageResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(messageResponse);
  }

  /**
   * Handles the "successful-hits" message from the server.
   * Reports the opponent's successful hits without any response.
   *
   * @param arguments The JSON node containing the opponent's successful hit coordinates.
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    VolleyJson reportDamageArgs = this.mapper.convertValue(arguments,
        VolleyJson.class);
    CoordJson[] shotsTakenByOpponentJson = reportDamageArgs.coordinates();
    Coord[] shotsTakenByOpponent = turnCoordsJsonIntoCoords(shotsTakenByOpponentJson);

    player.successfulHits(new ArrayList<>(List.of(shotsTakenByOpponent)));
    JsonNode emptyJsonNode = mapper.createObjectNode();
    MessageJson messageJson = new MessageJson("successful-hits", emptyJsonNode);
    JsonNode messageResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(messageResponse);
  }

  /**
   * Handles the "end-game" message from the server.
   * Updates the game result and reason based on the received arguments.
   *
   * @param arguments The JSON node containing the game result and reason.
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameArgs = this.mapper.convertValue(arguments,
        EndGameJson.class);
    result = endGameArgs.result();
    String reason = endGameArgs.reason();

    player.endGame(result, reason);
  }

  /**
   * Converts a list of ships to a FleetJson object.
   *
   * @param fleet The list of ships to be converted.
   * @return The FleetJson object representing the fleet.
   */
  private FleetJson turnFleetIntoFleetJson(List<Ship> fleet) {
    ShipJson[] ships = new ShipJson[fleet.size()];
    for (int i = 0; i < fleet.size(); i++) {
      Ship curr = fleet.get(i);
      Coord firstCoord = curr.getLocations().get(0);
      CoordJson startCoord = new CoordJson(firstCoord.getX(), firstCoord.getY());
      ships[i] = new ShipJson(startCoord, curr.getType().getSize(), curr.getDirection());
    }
    return new FleetJson(ships);
  }

  /**
   * Converts a list of coordinates to an array of CoordJson objects.
   *
   * @param coords The list of coordinates to be converted.
   * @return The array of CoordJson objects representing the coordinates.
   */
  private CoordJson[] turnCoordsIntoCoordJsons(List<Coord> coords) {
    CoordJson[] coordJsons = new CoordJson[coords.size()];

    for (int i = 0; i < coords.size(); i++) {
      Coord curr = coords.get(i);
      coordJsons[i] = new CoordJson(curr.getX(), curr.getY());
    }

    return coordJsons;
  }

  /**
   * Converts an array of CoordJson objects to an array of Coord objects.
   *
   * @param coordJsons The array of CoordJson objects to be converted.
   * @return The array of Coord objects representing the coordinates.
   */
  private Coord[] turnCoordsJsonIntoCoords(CoordJson[] coordJsons) {
    if (coordJsons == null) {
      return new Coord[0];
    }

    Coord[] coords = new Coord[coordJsons.length];

    for (int i = 0; i < coordJsons.length; i++) {
      CoordJson curr = coordJsons[i];
      coords[i] = new Coord(curr.x(), curr.y());
    }
    return coords;
  }
}
