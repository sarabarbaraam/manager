package com.sarabarbara.manager.utils;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GamesUtils class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Component
public class GamesUtils {

    /**
     * The private constructor
     */
    private GamesUtils() {

    }

    /**
     * Method to paginate
     *
     * @param gameList the gameList
     * @param page the page
     * @param size the size of the page
     * @param <T>  the <T>
     *
     * @return List<T> paginated
     */

    public static <T> @NonNull Page<T> paginate(@NonNull List<T> gameList, int page, int size) {


        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, gameList.size());

        // Make sure that the indexes are not out of range.
        if (startIndex >= gameList.size()) {

            return Page.empty(); // Returns an empty gameList if the requested page is out of range.
        }

        List<T> pageContent = gameList.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, PageRequest.of(page, size), gameList.size());
    }

    /**
     * Method to clean the invalid string characters
     *
     * @param string the string
     *
     * @return the cleaned string
     */

    public static String cleanInvalidJsonCharacters(@NonNull String string) {

        return string.replaceAll("[\\x00-\\x1F\\x7F\\uFFFD]", "");
    }

}
