package com.capitole.inditex.v1.entity;

/**
 * Enumeration of currency
 *
 * @author Matias Scalerandi
 */
public enum CurrencyIsoType {
    EUR(0),
    USD(1);

    private Integer dbValue;

    CurrencyIsoType(Integer dbValue) {
        this.dbValue = dbValue;
    }

    public Integer getDbValue() {
        return dbValue;
    }

    public static CurrencyIsoType fromDbValue(Integer dbValue) {
        for (CurrencyIsoType currencyIsoType : values()) {
            if (currencyIsoType.getDbValue().equals(dbValue)) {
                return currencyIsoType;
            }
        }
        throw new IllegalArgumentException("Cannot map the db value " + dbValue + " to currency iso type");
    }
}
