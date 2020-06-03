package com.eshop.eshopManagerAPI.models;

import javax.persistence.*;
import java.time.LocalDate;
import com.eshop.eshopManagerAPI.models.product;
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
	    
	    
	    private int SelectProductPrice; // poyraz
	    
	    private boolean couponApplied; // poyraz
	    

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
			this.orderDate = LocalDate.now();
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


		public int getSelectProductPrice() {
			return SelectProductPrice;
		}

		public void setSelectProductPrice(int selectProductPrice) {
			SelectProductPrice = selectProductPrice;
		}

		public boolean isCouponApplied() {
			return couponApplied;
		}

		public void setCouponApplied(boolean couponApplied) {
			this.couponApplied = couponApplied;
		}
}