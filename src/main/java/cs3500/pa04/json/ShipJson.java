package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Direction;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "coord": {
 *     "x": x,
 *     "y": y
 *   },
 *   "length": length,
 *   "direction": "direction"
 * }
 * </code>
 * </p>
 *
 * @param coord     The starting coordinate of the ship.
 * @param length    The length of the ship.
 * @param direction The direction of the ship.
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Direction direction
) {
}
