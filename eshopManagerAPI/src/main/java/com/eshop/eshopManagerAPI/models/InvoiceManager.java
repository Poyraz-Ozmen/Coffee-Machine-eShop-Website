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

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.eshop.eshopManagerAPI.models.User;
import com.eshop.eshopManagerAPI.models.product;
import com.eshop.eshopManagerAPI.repository.checkedOutItemsRepository;
import com.eshop.eshopManagerAPI.models.checkedOutItems;


public class InvoiceManager {
	
	@Autowired 
	checkedOutItemsRepository checkedOutItemsRepository;

	
	private List<checkedOutItems> checkedOutItem;
	
	private User user;
	
	private long userId;
	
	private Timestamp timeStamp;

	
	public String invoiceLine(long userId, Timestamp timeStamp) {
		
		checkedOutItem = checkedOutItemsRepository.findByuserIdAndPaymentDate(userId, timeStamp);
		
		
		return null;
	}
	
	public InvoiceManager() {
		super();
	}

	public InvoiceManager(List<checkedOutItems> checkedOutItem, User user, long userId, Timestamp timeStamp) {
		super();
		this.checkedOutItem = checkedOutItem;
		this.user = user;
		this.userId = userId;
		this.timeStamp = timeStamp;
	}

	public List<checkedOutItems> getCheckedOutItem() {
		return checkedOutItem;
	}

	public void setCheckedOutItem(List<checkedOutItems> checkedOutItem) {
		this.checkedOutItem = checkedOutItem;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	}
