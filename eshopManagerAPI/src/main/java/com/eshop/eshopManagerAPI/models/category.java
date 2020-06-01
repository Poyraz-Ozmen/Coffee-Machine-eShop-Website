package com.eshop.eshopManagerAPI.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(	name = "category", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "categoryName"),
		})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // RESOLVES HIBERNATE LAZYINITIALIZER PROBLEM
public class category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	private String categoryName;
	
	
	public category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}


	//JOIN LAZIM SONRASI IÇIN
	public category() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}