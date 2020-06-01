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
import com.eshop.eshopManagerAPI.models.Review;
import com.eshop.eshopManagerAPI.models.User;;



@Repository
public interface checkedOutItemsRepository extends JpaRepository<checkedOutItems, Long> {
	
	List<checkedOutItems> findByuserIdAndProductId(Long searchString, Long descriptionSearch);
	
	List<checkedOutItems> findByuserId(Long userId);
	
	List<checkedOutItems> findByuserId(long userId);
	
	List<checkedOutItems> findAllByOrderByUserIdAsc();
	
	List<checkedOutItems> findAllByUserIdOrderByUserIdAsc(Long userId);

	checkedOutItems findBycheckOutId(Long checkOutId);

	checkedOutItems findByUserIdAndProductId(Long searchString, Long descriptionSearch);
	
	List<checkedOutItems> findByuserIdAndPaymentDate(long userId, Timestamp paymentDate);

	List<checkedOutItems> deleteByuserIdAndProductId(long userId, long productId);

	checkedOutItems deleteByUserIdAndProductId(long userId, long productId);
	
	List<checkedOutItems> findByPaymentDate(Timestamp paymentDate);
	
	List<checkedOutItems> findByDeliveryStatus(Boolean deliveryStatus);


	
}

