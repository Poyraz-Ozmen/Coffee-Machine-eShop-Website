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
public class Review {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reviewId;

    @ManyToOne(fetch = FetchType.LAZY) // previously it was like that @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="productId")
    private product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    private LocalDate reviewDate;
    
    private Boolean reviewStatus = null;
    
    private String reviewText;

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public long getreviewId() {
		return reviewId;
	}

	public void setreviewId(long reviewId) {
		this.reviewId = reviewId;
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

	public LocalDate getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(LocalDate reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Boolean getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Boolean reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Review() {
		super();
	}

	public Review(product product, User user, String reviewText) {
		super();
		this.product = product;
		this.user = user;
		this.reviewDate = LocalDate.now();
		this.reviewStatus = null;
		this.reviewText = reviewText;

	}


}
