package com.sarabarbara.manager.responses;

import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * SearchResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 04/10/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SearchResponse<T> {

    /**
     * The results
     */

    private List<T> results;

    /**
     * The totalResults
     */

    private int totalResults;

    /**
     * The currentPage
     */

    private int currentPage;

    /**
     * The totalPage
     */

    private int totalPage;

    /**
     * The message
     */

    private String message;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchResponse<?> that)) return false;
        return getTotalResults() == that.getTotalResults()
                && getCurrentPage() == that.getCurrentPage()
                && getTotalPage() == that.getTotalPage()
                && Objects.equals(getResults(), that.getResults())
                && Objects.equals(getMessage(), that.getMessage());
    }

    /**
     * The hasCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getResults(), getTotalResults(),
                getCurrentPage(), getTotalPage(), getMessage());
    }

}