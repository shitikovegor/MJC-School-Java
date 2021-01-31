package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, LocalDateTime> purchaseDate;
	public static volatile SingularAttribute<Order, BigDecimal> cost;
	public static volatile SingularAttribute<Order, Long> id;
	public static volatile SingularAttribute<Order, GiftCertificate> giftCertificate;
	public static volatile SingularAttribute<Order, User> user;

	public static final String PURCHASE_DATE = "purchaseDate";
	public static final String COST = "cost";
	public static final String ID = "id";
	public static final String GIFT_CERTIFICATE = "giftCertificate";
	public static final String USER = "user";

}

