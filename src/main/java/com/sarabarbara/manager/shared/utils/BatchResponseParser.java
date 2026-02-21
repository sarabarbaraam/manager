package com.sarabarbara.manager.shared.utils;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

/**
 * BatchResponseParser class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/02/2026
 */

public interface BatchResponseParser<T>{

    Map<Integer, T> parse(JsonNode root, List<Integer> ids);

}
