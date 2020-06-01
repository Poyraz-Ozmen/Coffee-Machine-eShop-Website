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
public class ProductCategories {

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productCategoriesId;
	
    @ManyToOne(fetch = FetchType.LAZY) // previously it was like that @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="productId")
    private product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId")
    private category category;

	public long getProductCategoriesId() {
		return productCategoriesId;
	}

	public void setProductCategoriesId(long productCategoriesId) {
		this.productCategoriesId = productCategoriesId;
	}

	public product getProduct() {
		return product;
	}

	public void setProduct(product product) {
		this.product = product;
	}

	public category getCategory() {
		return category;
	}

	public void setCategory(category category) {
		this.category = category;
	}

	public ProductCategories(com.eshop.eshopManagerAPI.models.product product,
			com.eshop.eshopManagerAPI.models.category category) {
		super();
		this.product = product;
		this.category = category;
	}

	public ProductCategories() {
		super();
	}

}
