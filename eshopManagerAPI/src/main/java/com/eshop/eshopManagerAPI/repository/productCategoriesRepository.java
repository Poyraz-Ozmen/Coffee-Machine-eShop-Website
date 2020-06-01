package com.eshop.eshopManagerAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eshop.eshopManagerAPI.models.UserSelectsProduct;
import com.eshop.eshopManagerAPI.models.checkedOutItems;
import com.eshop.eshopManagerAPI.models.product;
import com.eshop.eshopManagerAPI.models.User;
import com.eshop.eshopManagerAPI.models.ProductCategories;




@Repository
public interface productCategoriesRepository extends JpaRepository<ProductCategories, Long> {

	List<ProductCategories> findAll();
	
	List<ProductCategories> findBycategoryId(long categoryId);
	
	
}
