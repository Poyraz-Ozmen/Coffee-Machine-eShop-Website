package com.eshop.eshopManagerAPI.repository;

import java.util.List;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eshop.eshopManagerAPI.models.UserSelectsProduct;
import com.eshop.eshopManagerAPI.models.product;


@Repository
public interface userSelectsProductRepository extends JpaRepository<UserSelectsProduct, Long> {
	
	List<UserSelectsProduct> findByuserIdAndProductId(Long searchString, Long descriptionSearch);
	
	List<UserSelectsProduct> findByuserId(Long userId);
	
	List<UserSelectsProduct> findByuserId(long userId);
	
	UserSelectsProduct findByUserIdAndProductId(Long searchString, Long descriptionSearch);
	
	UserSelectsProduct findByProductId(Long ProductId);
		
	List<UserSelectsProduct> deleteByuserIdAndProductId(long userId, long productId);

	UserSelectsProduct deleteByUserIdAndProductId(long userId, long productId);
}

