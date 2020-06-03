package com.eshop.eshopManagerAPI.repository;

import java.util.List;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.eshopManagerAPI.models.product;

@Repository
public interface productRepository extends JpaRepository<product, Long> {
	List<product> findByname(String name);
	
	Boolean existsByName(String name);
	
	List<product> findBynameContaining(String name);
	
	product findByid(String id);

	List<product> findBydescriptionContaining(String description);
	
	List<product> findAll();
	
	List<product> findBydescriptionOrNameContaining(String searchString, String descriptionSearch);

	product findByid(long productId);
	
	List<product> findAllByOrderByNameAsc();

    List<product> findAllByOrderByNameDesc();

}