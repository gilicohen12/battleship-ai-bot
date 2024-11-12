package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "method-name": "messageName",
 *   "arguments": {}
 * }
 * </code>
 * </p>
 *
 * @param messageName The name of the message.
 * @param arguments   The JSON node containing the arguments for the message.
 */
public record MessageJson(
    @JsonProperty("method-name") String messageName,
    @JsonProperty("arguments") JsonNode arguments) {
}
