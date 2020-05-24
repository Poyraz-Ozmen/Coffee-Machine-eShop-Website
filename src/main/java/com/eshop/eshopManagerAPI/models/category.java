package com.eshop.eshopManagerAPI.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "category", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "categoryName"),
		})

public class category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@NotBlank
	@Size(max = 60)
	private String categoryName;
	
	
	public category(@NotBlank @Size(max = 60) String categoryName) {
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