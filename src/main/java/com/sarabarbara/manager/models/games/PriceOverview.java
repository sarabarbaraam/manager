package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * PriceOverview class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceOverview {

    /**
     * The currency
     */

    private String currency;

    /**
     * The initial
     */

    private int initial;

    /**
     * The finalPrice
     */

    @JsonProperty("final")
    private int finalPrice;

    /**
     * The discountPercent
     */

    @JsonProperty("discount_percent")
    private int discountPercent;

    /**
     * The initialFormatted
     */

    @JsonProperty("initial_formatted")
    private String initialFormatted;

    /**
     * The finalFormatted
     */

    @JsonProperty("final_formatted")
    private String finalFormatted;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PriceOverview that)) return false;
        return getInitial() == that.getInitial()
                && getFinalPrice() == that.getFinalPrice()
                && getDiscountPercent() == that.getDiscountPercent()
                && Objects.equals(getCurrency(), that.getCurrency())
                && Objects.equals(getInitialFormatted(), that.getInitialFormatted())
                && Objects.equals(getFinalFormatted(), that.getFinalFormatted());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getCurrency(), getInitial(), getFinalPrice(), getDiscountPercent(),
                getInitialFormatted(), getFinalFormatted());
    }

}
