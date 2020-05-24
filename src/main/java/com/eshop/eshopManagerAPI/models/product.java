package com.eshop.eshopManagerAPI.models;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(	name = "product", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "modelNumber"),
		})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // RESOLVES HIBERNATE LAZYINITIALIZER PROBLEM
public class product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
		
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "product_category", 
				joinColumns = @JoinColumn(name = "product_id"), 
				inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<category> roles = new HashSet<>();
	
	@Size(max = 60)
	private String name;
	
	private int modelNumber;
	
	@Size(max = 600)
	private String description;

	private int quantityStocks;
	
	@Size(max = 60)
	private String warrantyStatus;
	
	@Size(max = 60)
	private String distributorInfo;
	
	private int price;

	
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	//JOIN LAZIM SONRASI IÇIN
	public product() {
		super();
	}

	public product(@Size(max = 60) String name, int modelNumber,
			@Size(max = 600) String description, int quantityStocks,
			@Size(max = 60) String warrantyStatus, @Size(max = 60) String distributorInfo, int price) {
		super();
		this.name = name;
		this.modelNumber = modelNumber;
		this.description = description;
		this.quantityStocks = quantityStocks;
		this.warrantyStatus = warrantyStatus;
		this.distributorInfo = distributorInfo;
		this.price = price;
	}
	
	public Long getID() {
		return id;
	}

	public void setID(Long iD) {
		id = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(int modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantityStocks() {
		return quantityStocks;
	}

	public void setQuantityStocks(int quantityStocks) {
		this.quantityStocks = quantityStocks;
	}

	public String getWarrantyStatus() {
		return warrantyStatus;
	}

	public void setWarrantyStatus(String warrantyStatus) {
		this.warrantyStatus = warrantyStatus;
	}

	public String getDistributorInfo() {
		return distributorInfo;
	}

	public void setDistributorInfo(String distributorInfo) {
		this.distributorInfo = distributorInfo;
	}
	
}