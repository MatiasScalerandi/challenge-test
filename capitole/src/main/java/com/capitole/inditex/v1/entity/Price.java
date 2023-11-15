package com.capitole.inditex.v1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_PRICES")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "price_id_sequence")
    @SequenceGenerator(name = "price_id_sequence", sequenceName = "SEQ_PRICE_ID", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Column(name = "BRAND_ID")
    private Long brandId;
    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    @Column(name = "PRICE_LIST")
    private Long priceListId;
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean priority;
    @Column(name = "PRICE")
    private BigDecimal finalPrice;
    @Column(name = "CURR")
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
        return "Price{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceListId=" + priceListId +
                ", productId=" + productId +
                ", priority=" + priority +
                ", finalPrice=" + finalPrice +
                ", currency=" + currency +
                '}';
    }
}
