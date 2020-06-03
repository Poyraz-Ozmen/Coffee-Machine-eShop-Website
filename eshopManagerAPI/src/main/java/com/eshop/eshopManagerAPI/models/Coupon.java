package com.eshop.eshopManagerAPI.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Coupon")
public class Coupon {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	
    private long couponId;
	
	
	private String coupon;
	
	private int discount;
	
	private long product_id;

	protected Coupon(){}

	public Coupon(String coupon) {
		super();
		this.coupon = coupon;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}
	
	

}
