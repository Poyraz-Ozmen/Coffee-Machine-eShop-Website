package com.eshop.eshopManagerAPI.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopManagerAPI.models.MailUtil;
import com.eshop.eshopManagerAPI.models.User;
import com.eshop.eshopManagerAPI.models.UserSelectsProduct;
import com.eshop.eshopManagerAPI.models.category;
import com.eshop.eshopManagerAPI.models.product;
import com.eshop.eshopManagerAPI.payload.response.MessageResponse;
import com.eshop.eshopManagerAPI.repository.categoryRepository;
import com.eshop.eshopManagerAPI.repository.productRepository;
import com.eshop.eshopManagerAPI.repository.UserRepository;
import com.eshop.eshopManagerAPI.repository.userSelectsProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired 
	UserRepository UserRepository;

	
	@Autowired 
	productRepository productRepository;
	
	@Autowired
	categoryRepository categoryRepository;


	@Autowired 
	userSelectsProductRepository userSelectsProductRepository;

	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	/*
	@GetMapping("/search/{searchParameter}")
	public ResponseEntity<?> searchItem(@PathVariable("searchParameter") String searchParameter)
	{
		List<product> productList = productRepository.findByname(searchParameter);
		return new ResponseEntity<List<product>>(productList, HttpStatus.OK);
	}
	*/
	
	@GetMapping("/search/{searchParameter}")
	public boolean isProductExist(@PathVariable("searchParameter") String searchParameter)
	{
		return productRepository.existsByName(searchParameter);
	}	
	
	@GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories()
    {
        List<category> categoryList = categoryRepository.findAll();

        return new ResponseEntity<List<category>>(categoryList, HttpStatus.OK);
    }	
	
	@GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts()
    {
        List<product> productList = productRepository.findAll();

        return new ResponseEntity<List<product>>(productList, HttpStatus.OK);
    }

	@GetMapping("/findBydescriptionOrNameContaining/{searchParameter1}")//Main Search Function
    public ResponseEntity<?> findBydescriptionOrNameContaining(@PathVariable("searchParameter1") String searchParameter1)
    {
        List<product> productList = productRepository.findBydescriptionOrNameContaining(searchParameter1, searchParameter1);

        List<product> productList2 = productRepository.findBydescriptionContaining(searchParameter1);
        
        for(product product: productList2)
        {
        	productList.add(product);
        }

        return new ResponseEntity<List<product>>(productList, HttpStatus.OK);
    }
	
	
	@PostMapping("/removeQuantityFromCart/{userId}/{productId}")//Remove 1 item from selected userID and productID
    public void removeQuantityFromCart(@PathVariable("userId") String userIds,@PathVariable("productId")
    String productIds)
    {

        long userId = Long.parseLong(userIds);
        long productId = Long.parseLong(productIds);

        //List<UserSelectsProduct> qResult = userSelectsProductRepository.findByuserIdAndProductId(userId, productId);
        UserSelectsProduct myResult = userSelectsProductRepository.findByUserIdAndProductId(userId, productId);
        if (myResult.getQuantity() > 1) {

            myResult.setQuantity(myResult.getQuantity()-1);
            userSelectsProductRepository.saveAndFlush(myResult);
        }
        else 
        {
            userSelectsProductRepository.deleteByuserIdAndProductId(userId, productId);
            //userSelectsProductRepository.deleteByuserIdAndProductId(userId, productId);
            // if quantity was 1 and if it decrases to 0, remove item
        }/*
        else {
            //do nothing
        }
        */
    }
	
	@PostMapping("/removeQuantityFromCartByBox/{userId}/{productId}/{quantityS}")//Remove Quantity buy users enter into the BOX
    public void removeQuantityFromCartByQuantity(@PathVariable("userId") String userIds,@PathVariable("productId")
	String productIds, @PathVariable("quantityS") String quantityS)
    {
		
		long userId = Long.parseLong(userIds);		
		long productId = Long.parseLong(productIds);
		int quantity = Integer.parseInt(quantityS);
		
		//List<UserSelectsProduct> qResult = userSelectsProductRepository.findByuserIdAndProductId(userId, productId);
		UserSelectsProduct myResult = userSelectsProductRepository.findByUserIdAndProductId(userId, productId);
		if (myResult.getQuantity() > 0 	&& quantity > 0 ) {
			
			myResult.setQuantity(quantity);
			userSelectsProductRepository.saveAndFlush(myResult);
		}
			
		else
		{
			//do nothing
		}
    }
	
	@PostMapping("/incrementQuantityFromCart/{userId}/{productId}")//increment Quantity by 1 in cart
    public void incrementQuantityFromCart(@PathVariable("userId") String userIds,@PathVariable("productId")
	String productIds)
    {
		
		long userId = Long.parseLong(userIds);		
		long productId = Long.parseLong(productIds);
		
		//List<UserSelectsProduct> qResult = userSelectsProductRepository.findByuserIdAndProductId(userId, productId);
		UserSelectsProduct myResult = userSelectsProductRepository.findByUserIdAndProductId(userId, productId);
		if (myResult.getQuantity() >= 0) {
			
			myResult.setQuantity(myResult.getQuantity()+1);
			userSelectsProductRepository.saveAndFlush(myResult);
		}
			
		else
		{
			//do nothing
		}
    }
	

	@PostMapping("/addToCart/{userId}/{productId}/{quantityS}")//ADD TO CART
    public void addToCart(@PathVariable("userId") String userIds,@PathVariable("productId")
    		String productIds, @PathVariable("quantityS") String quantityS)
    {
		
		long userId = Long.parseLong(userIds);		
		long productId = Long.parseLong(productIds);
		int quantity = Integer.parseInt(quantityS);
	
		List<UserSelectsProduct> qResult = userSelectsProductRepository.findByuserIdAndProductId(userId, productId);
		if (qResult.isEmpty()) {
				
			LocalDate localDate = LocalDate.now();
			product product1 = productRepository.findByid(productId);
			User user1 = UserRepository.findByid(userId);
				
			UserSelectsProduct selectedProduct = new UserSelectsProduct(product1, user1, localDate, quantity);	
			System.out.println(selectedProduct.getQuantity());
			System.out.println(product1.getID());
			System.out.println(user1.getId());
			selectedProduct = userSelectsProductRepository.saveAndFlush(selectedProduct);
			product1.getID();
			user1.getId();
		}
			
		else
		{
			List<UserSelectsProduct> selectedProduct = userSelectsProductRepository.findByuserIdAndProductId(userId, productId);
			selectedProduct.get(0).setQuantity(selectedProduct.get(0).getQuantity() + quantity);
			userSelectsProductRepository.saveAndFlush(selectedProduct.get(0));
		}
    }
	
	@GetMapping("/fetchAllProductsInCarts/{userIds}")
    public ResponseEntity<?> fetchAllProductsInCarts(@PathVariable("userIds") String userIds)
    {
        long userId = Long.parseLong(userIds);
        List<product> productsList = new ArrayList<product>();
        List<UserSelectsProduct> cartItems = userSelectsProductRepository.findByuserId(userId);
        
        for(UserSelectsProduct selectedProduct: cartItems)
        {
        	product result = productRepository.findByid(selectedProduct.getProduct().getID());
        	productsList.add(result);
        }
        
        return new ResponseEntity<List<product>>(productsList, HttpStatus.OK);
    }
	
	
	@GetMapping("/fetchUserSelectsProducts/{userId}/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable("userId") String userIds,@PathVariable("productId")
           String productIds)
   {

       long userId = Long.parseLong(userIds);
       long productId = Long.parseLong(productIds);

       List<UserSelectsProduct> qResult = userSelectsProductRepository.findByuserIdAndProductId(userId, productId);
       return new ResponseEntity<List<UserSelectsProduct>>(qResult, HttpStatus.OK);

   }
	
	

	@PostMapping("/editQuantityStocks/{id}/{quantityS}")//Product Manager sets the quantity_stocks column in database, works fine not needs to be debugged!
    public void editQuantityStocks(@PathVariable("id") String productId, @PathVariable("quantityS") String quantityS)
    {
		
		long id = Long.parseLong(productId);
		int quantity = Integer.parseInt(quantityS);
		
		product myProduct = productRepository.findByid(id); //find the item with given id
		myProduct.setQuantityStocks(quantity); //set the quantity
		productRepository.saveAndFlush(myProduct);//Save to Database
    }
	
	@PostMapping("/removeItemFromCart/{userId}/{productId}")//Remove 1 item from selected userID and productID
    public void removeItemFromCart(@PathVariable("userId") String userIds,@PathVariable("productId")
	String productIds)
    {
		
		long userId = Long.parseLong(userIds);		
		long productId = Long.parseLong(productIds);
		
		userSelectsProductRepository.deleteByuserIdAndProductId(userId, productId);
		
    }
	
	@PostMapping("/sendMail/{recepient}")
    public void sendMail(@PathVariable("recepient") String recepient) throws Exception
	
    {
		
		MailUtil.sendMail(recepient);
		
    }
    

}
	
















