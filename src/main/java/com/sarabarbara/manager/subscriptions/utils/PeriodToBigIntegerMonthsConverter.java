package com.sarabarbara.manager.subscriptions.utils;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigInteger;
import java.time.Period;

/**
 * PeriodToBigIntegerMonthsConverter class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 09/01/2026
 */

@Converter
public class PeriodToBigIntegerMonthsConverter implements AttributeConverter<Period, BigInteger> {


    @Override
    public BigInteger convertToDatabaseColumn(Period period) {

        return period == null ? null : BigInteger.valueOf(period.toTotalMonths());
    }

    @Override
    public Period convertToEntityAttribute(BigInteger dbData) {

        if (dbData == null) return null;

        long monthsLong = dbData.longValueExact();
        return Period.ofMonths(Math.toIntExact(monthsLong));
    }
}
