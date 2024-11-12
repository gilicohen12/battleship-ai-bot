package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.GameType;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "name": "name",
 *   "game-type": "gameType"
 * }
 * </code>
 * </p>
 *
 * @param name      The name of the player joining the game.
 * @param gameType  The type of the game being joined.
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType
) {
}
