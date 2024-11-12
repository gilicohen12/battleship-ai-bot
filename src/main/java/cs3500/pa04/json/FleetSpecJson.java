package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "CARRIER": carrierCount,
 *   "BATTLESHIP": battleshipCount,
 *   "DESTROYER": destroyerCount,
 *   "SUBMARINE": submarineCount
 * }
 * </code>
 * </p>
 *
 * @param carrierCount   The count of carrier ships.
 * @param battleshipCount The count of battleship ships.
 * @param destroyerCount  The count of destroyer ships.
 * @param submarineCount  The count of submarine ships.
 */
public record FleetSpecJson(
    @JsonProperty("CARRIER") int carrierCount,
    @JsonProperty("BATTLESHIP") int battleshipCount,
    @JsonProperty("DESTROYER") int destroyerCount,
    @JsonProperty("SUBMARINE") int submarineCount
) {
}
