package com.sarabarbara.manager.responses.viewing;

import com.sarabarbara.manager.dto.viewing.ViewingSheetDTO;
import lombok.*;

import java.util.Objects;

/**
 * ViewingSheetResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 18/02/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ViewingSheetResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The movieSheet
     */

    private ViewingSheetDTO viewingSheet;

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
        if (!(o instanceof ViewingSheetResponse that)) return false;
        return isSuccess() == that.isSuccess()
                && Objects.equals(getViewingSheet(), that.getViewingSheet())
                && Objects.equals(getMessage(), that.getMessage());
    }

    /**
     * The hasCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess(), getViewingSheet(), getMessage());
    }

}
