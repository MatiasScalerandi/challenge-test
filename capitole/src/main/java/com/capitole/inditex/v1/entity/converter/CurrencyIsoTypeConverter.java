package com.capitole.inditex.v1.entity.converter;

import com.capitole.inditex.v1.entity.CurrencyIsoType;
import jakarta.persistence.AttributeConverter;

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
