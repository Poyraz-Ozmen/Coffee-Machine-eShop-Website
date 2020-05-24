package com.eshop.eshopManagerAPI.models;

import java.util.HashSet;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Cart {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long cartId;

	    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    @JoinColumn(name="orderId")
	    private UserSelectsProduct UserSelectsProduct;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="userId")
	    private User user;

		public long getCartId() {
			return cartId;
		}

		public void setCartId(long cartId) {
			this.cartId = cartId;
		}

		public Cart(long cartId, com.eshop.eshopManagerAPI.models.UserSelectsProduct userSelectsProduct, User user) {
			super();
			this.cartId = cartId;
			UserSelectsProduct = userSelectsProduct;
			this.user = user;
		}

		public Cart() {
			super();
		}
		

}