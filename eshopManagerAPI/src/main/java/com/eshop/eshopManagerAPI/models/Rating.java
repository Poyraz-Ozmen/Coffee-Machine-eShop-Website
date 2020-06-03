package com.eshop.eshopManagerAPI.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Rating {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ratingId;

	
	private double rating;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name="productId")
    private product product;

	public Rating() {
		super();
	}

	
	public Rating(User user, com.eshop.eshopManagerAPI.models.product product,long rating) {
		super();
		this.user = user;
		this.product = product;
		this.rating = rating;
	}


	public long getRatingId() {
		return ratingId;
	}

	public void setRatingId(long ratingId) {
		this.ratingId = ratingId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
}
