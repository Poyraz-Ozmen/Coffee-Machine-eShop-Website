package com.eshop.eshopManagerAPI.models;

import java.util.Calendar;

import java.util.HashSet;



import java.util.List;
import java.util.Set;

import java.time.LocalDate;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.eshop.eshopManagerAPI.models.product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.eshop.eshopManagerAPI.models.User;

@Entity
public class checkedOutItems {
	

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long checkOutId;

    @ManyToOne(fetch = FetchType.LAZY) // previously it was like that @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="productId")
    private product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    private LocalDate orderDate;
    
    private int quantity;

    private Timestamp paymentDate;
    
    
    //DELIVERY
    private Boolean deliveryStatus = null;
    //Null ürün boşta
    //false in delivery
    //true item delivered

	public Boolean getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	//DELIVERY

	public long getCheckOutId() {
		return checkOutId;
	}

	public void setCheckOutId(long checkOutId) {
		this.checkOutId = checkOutId;
	}

	public product getProduct() {
		return product;
	}

	public void setProduct(product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Timestamp checkOutDateGen() {
		Calendar cal = Calendar.getInstance();
        Timestamp paymentTime = new Timestamp(cal.getTimeInMillis());
		return paymentTime;
	}
	
	public checkedOutItems() {
		super();
	}

	public checkedOutItems(product product, User user, LocalDate orderDate, int quantity) {
		super();
		this.product = product;
		this.user = user;
		this.orderDate = orderDate;
		this.quantity = quantity;
		this.paymentDate = checkOutDateGen();
		//DELIVERY
		this.deliveryStatus = null;
		//DELIVERY
	}



	
	
	
	
	

}
