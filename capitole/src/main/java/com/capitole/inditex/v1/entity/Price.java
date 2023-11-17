package com.capitole.inditex.v1.entity;

import com.capitole.inditex.v1.entity.converter.CurrencyIsoTypeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing the Price data
 *
 * @author Matias Scalerandi
 */
@Entity
@Table(name = "TBL_PRICES")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "price_id_sequence")
    @SequenceGenerator(name = "price_id_sequence", sequenceName = "SEQ_PRICE_ID", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @NotNull
    @Column(name = "BRAND_ID")
    private Long brandId;
    @NotNull
    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    @NotNull
    @Column(name = "PRICE_LIST")
    private Long priceListId;
    @NotNull
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean priority;
    @NotNull
    @Column(name = "PRICE")
    private BigDecimal finalPrice;
    @NotNull
    @Column(name = "CURR")
    @Convert(converter = CurrencyIsoTypeConverter.class)
    private CurrencyIsoType currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getPriceListId() {
        return priceListId;
    }

    public void setPriceListId(Long priceListId) {
        this.priceListId = priceListId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean isPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public CurrencyIsoType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyIsoType currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("id", id)
                .append("brandId", brandId)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .append("priceListId", priceListId)
                .append("productId", productId)
                .append("priority", priority)
                .append("finalPrice", finalPrice)
                .append("currency", currency)
                .toString();
    }
}
