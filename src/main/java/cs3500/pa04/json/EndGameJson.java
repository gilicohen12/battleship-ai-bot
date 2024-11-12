package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "result": "result",
 *   "reason": "reason"
 * }
 * </code>
 * </p>
 *
 * @param result The result of the game.
 * @param reason The reason for the end of the game.
 */
public record EndGameJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason
) {
}
