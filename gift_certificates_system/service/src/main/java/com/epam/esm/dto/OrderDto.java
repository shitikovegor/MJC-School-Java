package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class {@code Order} represents data of order.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class OrderDto extends RepresentationModel<OrderDto> {
    private long id;
    private UserDto user;
    private GiftCertificateDto giftCertificate;
    private BigDecimal cost;
    private LocalDateTime purchaseDate;

    /**
     * Instantiates a new Order DTO.
     */
    public OrderDto() {
    }

    /**
     * Instantiates a new Order dto.
     *
     * @param id              the id
     * @param user            the user
     * @param giftCertificate the gift certificate
     * @param cost            the cost
     * @param purchaseDate    the purchase date
     */
    public OrderDto(long id, UserDto user, GiftCertificateDto giftCertificate, BigDecimal cost, LocalDateTime purchaseDate) {
        this.id = id;
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public GiftCertificateDto getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificateDto giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDto orderDto = (OrderDto) o;
        return id == orderDto.id &&
                Objects.equals(user, orderDto.user) &&
                Objects.equals(giftCertificate, orderDto.giftCertificate) &&
                Objects.equals(cost, orderDto.cost) &&
                Objects.equals(purchaseDate, orderDto.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, giftCertificate, cost, purchaseDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDto{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", giftCertificate=").append(giftCertificate);
        sb.append(", cost=").append(cost);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append('}');
        return sb.toString();
    }
}
