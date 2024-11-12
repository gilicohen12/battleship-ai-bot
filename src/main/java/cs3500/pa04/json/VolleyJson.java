package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "coordinates": [
 *     {"x": x1, "y": y1},
 *     {"x": x2, "y": y2},
 *     ...
 *   ]
 * }
 * </code>
 * </p>
 *
 * @param coordinates The array of coordinates for the volley.
 */
public record VolleyJson(
    @JsonProperty("coordinates") CoordJson[] coordinates
) {
}
