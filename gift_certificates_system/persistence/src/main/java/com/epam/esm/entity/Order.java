package com.epam.esm.entity;

import com.epam.esm.dao.audit.OrderAuditListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class {@code Order} represents order entity.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@EntityListeners(OrderAuditListener.class)
@Entity
@Table(name = "gift_certificate_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id_fk", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "gift_certificate_id_fk", referencedColumnName = "id")
    private GiftCertificate giftCertificate;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Instantiates a new Order.
     *
     * @param id              the id
     * @param user            the user
     * @param giftCertificate the gift certificate
     * @param cost            the cost
     * @param purchaseDate    the purchase date
     */
    public Order(long id, User user, GiftCertificate giftCertificate, BigDecimal cost, LocalDateTime purchaseDate) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificates) {
        this.giftCertificate = giftCertificates;
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
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(user, order.user) &&
                Objects.equals(giftCertificate, order.giftCertificate) &&
                Objects.equals(cost, order.cost) &&
                Objects.equals(purchaseDate, order.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, giftCertificate, cost, purchaseDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", giftCertificate=").append(giftCertificate);
        sb.append(", cost=").append(cost);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append('}');
        return sb.toString();
    }
}
