package com.sarabarbara.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
public class SearchResponse<T> {

    /**
     * The results
     */

    List<T> results;

    /**
     * The totalResults
     */

    int totalResults;

    /**
     * The currentPage
     */

    int currentPage;

    /**
     * The totalPage
     */

    int totalPage;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchResponse<?> that)) return false;
        return getTotalResults() == that.getTotalResults() && getCurrentPage() == that.getCurrentPage()
                && getTotalPage() == that.getTotalPage() && Objects.equals(getResults(), that.getResults());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getResults(), getTotalResults(), getCurrentPage(), getTotalPage());
    }

}