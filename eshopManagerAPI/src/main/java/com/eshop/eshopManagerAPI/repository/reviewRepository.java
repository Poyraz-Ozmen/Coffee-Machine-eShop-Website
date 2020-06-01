package com.eshop.eshopManagerAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.eshopManagerAPI.models.Review;

import java.util.List;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eshop.eshopManagerAPI.models.UserSelectsProduct;
import com.eshop.eshopManagerAPI.models.product;


public interface reviewRepository extends JpaRepository<Review, Long> {
	
	Review findByUserIdAndProductId(Long userId, Long productId);

	Review findByReviewId(Long reviewId);
	
	List<Review> findByuserId(Long userId);
	
	List<Review> findByproductId(Long productId);
		
	List<Review> findByproductIdAndReviewStatus(Long userId, Boolean reviewStatus);
	
	List<Review> findByReviewStatus(Boolean reviewStatus);
		
	List<Review> deleteByuserIdAndProductId(long userId, long productId);

	Review deleteByUserIdAndProductId(long userId, long productId);


}
