package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "fleet": [
 *     {"property1": "value1", "property2": "value2", ...},
 *     {"property1": "value1", "property2": "value2", ...},
 *     ...
 *   ]
 * }
 * </code>
 * </p>
 *
 * @param fleet The array of ship information.
 */
public record FleetJson(
    @JsonProperty("") ShipJson[] fleet
) {
}
