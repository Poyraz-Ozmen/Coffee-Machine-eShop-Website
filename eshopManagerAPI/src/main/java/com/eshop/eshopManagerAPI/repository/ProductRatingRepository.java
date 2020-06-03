package com.eshop.eshopManagerAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.eshopManagerAPI.models.ProductRating;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

	ProductRating findByproductId(long productId);
	
	List<ProductRating> findAll();
	
	List<ProductRating> findAllByOrderByAverageRatingAsc();

    List<ProductRating> findAllByOrderByAverageRatingDesc();
    
    //List<ProductRating> findAllByAverageRatingDesc();
}
