package com.capitole.inditex.v1.entity.converter;

import com.capitole.inditex.v1.entity.CurrencyIsoType;
import jakarta.persistence.AttributeConverter;

/**
 * Converter between {@link CurrencyIsoType} and database value
 *
 * @author Matias Scalerandi
 */
public class CurrencyIsoTypeConverter implements AttributeConverter<CurrencyIsoType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CurrencyIsoType currencyIsoType) {
        return currencyIsoType == null ? null : currencyIsoType.getDbValue();
    }

    @Override
    public CurrencyIsoType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : CurrencyIsoType.fromDbValue(dbData);
    }
}
