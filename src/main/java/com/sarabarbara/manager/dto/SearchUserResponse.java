package com.sarabarbara.manager.dto;

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
public class SearchUserResponse<T> {

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
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchUserResponse<?> that)) return false;
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