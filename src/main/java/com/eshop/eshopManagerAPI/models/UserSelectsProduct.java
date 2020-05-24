package com.eshop.eshopManagerAPI.models;

import java.util.HashSet;


import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import com.eshop.eshopManagerAPI.models.product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.eshop.eshopManagerAPI.models.User;

@Entity
public class UserSelectsProduct {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long orderId;

	    @ManyToOne(fetch = FetchType.LAZY) // previously it was like that @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    @JoinColumn(name="productId")
	    private product product;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="userId")
	    private User user;

//	    private long userId;

	    private LocalDate orderDate;
	    
	    private int quantity;

		public long getOrderId() {
			return orderId;
		}

		public void setOrderId(long orderId) {
			this.orderId = orderId;
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

		public UserSelectsProduct(product product, User user, LocalDate orderDate,
				int quantity) {
			super();
			this.product = product;
			this.user = user;
			this.orderDate = orderDate;
			this.quantity = quantity;
		}
		
		public UserSelectsProduct()
		{
			super();
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
}