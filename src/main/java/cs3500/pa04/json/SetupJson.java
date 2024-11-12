package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "width": width,
 *   "height": height,
 *   "fleet-spec": {
 *     "CARRIER": carrierCount,
 *     "BATTLESHIP": battleshipCount,
 *     "DESTROYER": destroyerCount,
 *     "SUBMARINE": submarineCount
 *   }
 * }
 * </code>
 * </p>
 *
 * @param width      The width of the game board.
 * @param height     The height of the game board.
 * @param fleetSpec  The specification of the fleet for the game.
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetSpecJson fleetSpec
) {
}
