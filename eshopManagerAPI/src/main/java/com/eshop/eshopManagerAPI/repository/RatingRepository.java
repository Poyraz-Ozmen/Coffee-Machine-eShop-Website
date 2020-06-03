package com.eshop.eshopManagerAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.eshopManagerAPI.models.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
	
	List<Rating> findAll(); 

    Rating findByratingId(long ratingId);

    List<Rating> findByproductId(Long productId);

    Rating findByproductId(long productId);

    List<Rating> findByuserIdAndProductId(Long userId, Long productId);


    Boolean existsByuserIdAndProductId(Long userId, Long productId);


	
}
