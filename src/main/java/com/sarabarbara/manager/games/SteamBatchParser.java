package com.sarabarbara.manager.games;


import com.fasterxml.jackson.databind.JsonNode;
import com.sarabarbara.manager.shared.utils.BatchResponseParser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BatchParser class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/02/2026
 */

@Component
public class SteamBatchParser implements BatchResponseParser<JsonNode> {

    @Override
    public Map<Integer, JsonNode> parse(JsonNode root, @NotNull List<Integer> ids) {

        Map<Integer, JsonNode> result = new HashMap<>();

        for (Integer id : ids) {
            JsonNode data = root.path(String.valueOf(id)).path("data");
            if (!data.isMissingNode() && !data.isNull()) {
                result.put(id, data);
            }
        }

        return result;
    }

}
