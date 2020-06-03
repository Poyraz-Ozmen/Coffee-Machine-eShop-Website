package com.eshop.eshopManagerAPI.controllers;


import java.io.FileNotFoundException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

import com.eshop.eshopManagerAPI.models.Coupon;
import com.eshop.eshopManagerAPI.models.CreatePDF;
import com.eshop.eshopManagerAPI.models.DateUtil;
import com.eshop.eshopManagerAPI.models.MailUtil;
import com.eshop.eshopManagerAPI.models.ProductCategories;
import com.eshop.eshopManagerAPI.models.ProductRating;
import com.eshop.eshopManagerAPI.models.Rating;
import com.eshop.eshopManagerAPI.models.Review;
import com.eshop.eshopManagerAPI.models.User;
import com.eshop.eshopManagerAPI.models.UserSelectsProduct;
import com.eshop.eshopManagerAPI.models.category;
import com.eshop.eshopManagerAPI.models.checkedOutItems;
import com.eshop.eshopManagerAPI.models.product;
import com.eshop.eshopManagerAPI.models.Review;

import com.eshop.eshopManagerAPI.payload.response.MessageResponse;
import com.eshop.eshopManagerAPI.repository.categoryRepository;
import com.eshop.eshopManagerAPI.repository.checkedOutItemsRepository;
import com.eshop.eshopManagerAPI.repository.productRepository;
import com.eshop.eshopManagerAPI.repository.CouponRepository;
import com.eshop.eshopManagerAPI.repository.ProductRatingRepository;
import com.eshop.eshopManagerAPI.repository.RatingRepository;
import com.eshop.eshopManagerAPI.repository.UserRepository;
import com.eshop.eshopManagerAPI.repository.userSelectsProductRepository;
import com.eshop.eshopManagerAPI.repository.productCategoriesRepository;
import com.eshop.eshopManagerAPI.repository.reviewRepository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping("/api/test")
public class TestController {
	
	//netstat -ano |findstr 8080
	//taskkill /F /PID 

	
	@Autowired 
	UserRepository UserRepository;

	
	@Autowired 
	productRepository productRepository;
	
	@Autowired
	categoryRepository categoryRepository;


	@Autowired 
	userSelectsProductRepository userSelectsProductRepository;
	
	@Autowired 
	checkedOutItemsRepository checkedOutItemsRepository;
	
	@Autowired 
	productCategoriesRepository productCategoriesRepository;

	@Autowired 
	reviewRepository reviewRepository;
	
	@Autowired 
	CouponRepository CouponRepository;
	
	@Autowired 
	RatingRepository RatingRepository;
	
	@Autowired
	ProductRatingRepository ProductRatingRepository;
	
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
    public String addToCart(@PathVariable("userId") String userIds,@PathVariable("productId")
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
				selectedProduct.setSelectProductPrice(product1.getDiscountedPrice());// poyraz manual set price
				selectedProduct = userSelectsProductRepository.saveAndFlush(selectedProduct);
				return "";
			}
				
			else
			{
				List<UserSelectsProduct> selectedProduct = userSelectsProductRepository.findByuserIdAndProductId(userId, productId);
				selectedProduct.get(0).setQuantity(selectedProduct.get(0).getQuantity() + quantity);
				userSelectsProductRepository.saveAndFlush(selectedProduct.get(0));
				return "";
	
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
	


	
	@GetMapping("/finalizeCheckout/{userId}")
    public String finalizeCheckout(@PathVariable("userId") String userIds) throws Exception
   {

       long userId = Long.parseLong(userIds);
       
       List<UserSelectsProduct> qResult = userSelectsProductRepository.findByuserId(userId);
       
       
       // Before processing items, check all quantity stocks of each item
       for(UserSelectsProduct currentRow: qResult) {
    	   
    	   if(currentRow.getProduct().getQuantityStocks() >= currentRow.getQuantity()) 
    	   {
    		   // do nothing, will process below
    	   }
    	   else {
    		   	String currentStocks = Integer.toString(currentRow.getProduct().getQuantityStocks());
   		   		String currentItemName = currentRow.getProduct().getName();
   		   		return "The amount of " + currentItemName+ "in your cart cannot exceed the amount of item in Stocks (" + currentStocks + ").";
    	   }
    	   
    	   
       }
       
       
       
       /////////Invoice Oluştur, mail gönder
	  /////////Invoice Oluştur, mail gönder
	 /////////Invoice Oluştur, mail gönder
    /////////Invoice Oluştur, mail gönder
       // before deleting items from userSelectsProduct
       int total = 0;
       
       String Message = "Thank you for your purchase \n\n" + "Your purchase includes: " + "\n\n";
       
       for(UserSelectsProduct Item: qResult)
       {
           
    	   
    	   
           int total_price_of_one_product = Item.getSelectProductPrice() * (Item.getQuantity());
           
           total += total_price_of_one_product;
           
           // discount varsa da aynısı
           if(Item.isCouponApplied()==true) 
           {      	   
        	   if(Item.getProduct().isDiscounted()==true) 
        	   {
        		   Message = Message  + " " +"Product Name: " + (Item.getProduct().getName()) + " " +" - Quantity: " + Item.getQuantity() 
	               +" - Product price: "+Item.getSelectProductPrice() + " \n-Your coupon has been applied to this item original price was :" + Item.getProduct().getPrice() 
	               + "\n-Discounted price was :" +Item.getProduct().getDiscountedPrice()
	               + " \n- total price: "+total_price_of_one_product +"\n\n\n" ;
        	   }
        	   else 
        	   {
	        	   Message = Message  + " " +"Product Name: " + (Item.getProduct().getName()) + " " +" - Quantity: " + Item.getQuantity() 
	               +" - Product price: "+Item.getSelectProductPrice() + " \n-Your coupon has been applied to this item original price was :" + Item.getProduct().getPrice() 
	               + " \n- total price: "+total_price_of_one_product +"\n\n\n" ;
        	   }
        	   
           }
           else 
           {
        	   if(Item.getProduct().isDiscounted()==true) 
        	   {
        		   Message = Message  + " " +"Product Name: " + (Item.getProduct().getName()) + " " +" - Quantity: " + Item.getQuantity() 
	               +" - Product price: "+Item.getSelectProductPrice() + "original price was : " +Item.getProduct().getPrice() 
	               + "\nDiscounted price was :" +Item.getProduct().getDiscountedPrice()
	               + " \n- total price: "+total_price_of_one_product +"\n\n\n" ;
        	   }
        	   
        	   else {
		           Message = Message + " " +"Product Name: " + (Item.getProduct().getName()) + " " +" - Quantity: " + Item.getQuantity() 
		           +" - Product price: "+Item.getProduct().getPrice()
		           + " \n- total price: "+total_price_of_one_product +"\n\n" ;
        	   }
           }
       }
       Message = Message + "\n" +"Total price: " + total;
       CreatePDF.createInvoicePDF(Message);
       User user = UserRepository.findByid(userId);
       MailUtil.sendInvoiceMail(user.getEmail());
       
       /////////Invoice Oluştur, mail gönder
	  /////////Invoice Oluştur, mail gönder
	 /////////Invoice Oluştur, mail gönder
    /////////Invoice Oluştur, mail gönder
       

       for(UserSelectsProduct currentRow: qResult) {
    	   
    	   // We don't need any if condition because we checked it above.
    	   //if(currentRow.getProduct().getQuantityStocks() >= currentRow.getQuantity()) 
    	   
		    	   checkedOutItems checkedOutItem = new checkedOutItems(
		    			   												currentRow.getProduct(),
			    			   											currentRow.getUser(),
			    			   											currentRow.getOrderDate(),
			    			   											currentRow.getQuantity()
			    			   											);    	   
		    	   
		    	   userSelectsProductRepository.deleteByuserIdAndProductId(userId, currentRow.getProduct().getID());
		    	   
		    	   checkedOutItemsRepository.saveAndFlush(checkedOutItem);
		    	   
		    	   //STOCK
		    	   
		    	   product currentProduct = currentRow.getProduct();
		    	   
		    	   int currentStocks = currentProduct.getQuantityStocks();
		    	   
		    	   currentProduct.setQuantityStocks(currentStocks - currentRow.getQuantity());
		    	   
		    	   productRepository.saveAndFlush(currentProduct);
		   
    	   // There won't be any else because we checked it above
    	   //else {}	    	   
       }
              
       return "180";
     
   }

	
	@GetMapping("/discountItem/{productId}/{discountPercentage}")
    public String discountItem(@PathVariable("productId") String productIds, 
    						 @PathVariable("discountPercentage") String discountPercentages) throws Exception
   {
			if(Integer.parseInt(discountPercentages) < 101 && Integer.parseInt(discountPercentages) > 0) {
		       
				product product1 = productRepository.findByid(Long.parseLong(productIds));
				
				int original_price = product1.getPrice();
		       
				int discountedPrice = product1.getPrice() - ((product1.getPrice() * Integer.parseInt(discountPercentages)) / 100);
		
				product1.setDiscountedPrice(discountedPrice);
				
				product1.setDiscounted(true);
				
				productRepository.saveAndFlush(product1);
		       
				
				//SEND MAIL HERE 
				//productID
				////////////////////////////////////// poyraz mail başlangıç
				
				List<User> userList = UserRepository.findAll(); // gets all the users in a list
		        String message_str = "The product " + "'" + product1.getName() + "'" +" priced at "+ original_price 
		        		+ " is discounted by " + discountPercentages + "% and is now priced at " +  discountedPrice;

		        for(User User: userList)
		        {
		            // If user has an email then send discount message.
		            if(User.getEmail()!=null) 
		            {
		                MailUtil.sendDiscountMail(User.getEmail(),message_str);
		                //mailList.add(User.getEmail());
		            }

		        }
				////////////////////////////////////// poyraz mail bitiş

		       return "The item " + product1.getName() + " is discounted by " + discountPercentages 
		    		   + "% and is now priced at " + product1.getDiscountedPrice() + " dollars.";
		       
			}
			
			else if(Integer.parseInt(discountPercentages) >= 101) {
			    return "The item " + productRepository.findByid(Long.parseLong(productIds)).getName() 
			    		+ " cannot be discounted by " + discountPercentages + "%. The maximum discount amount is 100%";			
			}
			
			else if(Integer.parseInt(discountPercentages) < 1) {
	
				  return "The item " + productRepository.findByid(Long.parseLong(productIds)).getName() 
				    		+ " cannot be discounted by " + discountPercentages + "%. The minimum discount amount is 1%";			
	   }
			return "180";
   }
		
	
	@GetMapping("/removeDiscountItem/{productId}")
    public String removeDiscountItem(@PathVariable("productId") String productIds)
   {
			product product1 = productRepository.findByid(Long.parseLong(productIds));

			if(product1.isDiscounted() == true) {
		       		
				product1.setDiscountedPrice(product1.getPrice());
				
				product1.setDiscounted(false);
				
				productRepository.saveAndFlush(product1);
		       
		       return "The item " + product1.getName() + " is no longer discounted and is now priced at " 
		       + product1.getPrice() + " dollars.";
			}
			
			else if(product1.isDiscounted() == false) {
			    return "The item " + product1.getName() + " is not discounted and therefore cannot be undiscounted.";
			
			}
			
			return "180";
   }
	
	@GetMapping("/fetchAllCheckedOutItems")//fetches datamap rows for sales manager.
    public ResponseEntity<?> fetchAllCheckedOutItems()
    {
        
        List<checkedOutItems> allCheckedOutItems = checkedOutItemsRepository.findAllByOrderByUserIdAsc();

        
        return new ResponseEntity<List<checkedOutItems>>(allCheckedOutItems, HttpStatus.OK);
    }

	
	
	@GetMapping("/fetchAllCheckedOutItemsForUser/{userIds}")//Fetches datamap rows for profile page.
    public ResponseEntity<?> fetchAllCheckedOutItemsForUser(@PathVariable("userId") String userIds)
    {
	

		
        List<checkedOutItems> usersItems = checkedOutItemsRepository.findAllByUserIdOrderByUserIdAsc(Long.parseLong(userIds));

        
        return new ResponseEntity<List<checkedOutItems>>(usersItems, HttpStatus.OK);
    }
	
	
	//FURKANIN PRODUCT MANAGER METHODLARI BURADAN BASLIYOR
	//FURKANIN PRODUCT MANAGER METHODLARI BURADAN BASLIYOR
	//FURKANIN PRODUCT MANAGER METHODLARI BURADAN BASLIYOR
	//FURKANIN PRODUCT MANAGER METHODLARI BURADAN BASLIYOR

	
	@PostMapping("/updateProduct/{id}/{description}/{distributor}/{model}/{name}/{price}/{quantity}/{warranty}")//Product Manager updates details of a product using it's id
    public void updateProduct(@PathVariable("id") String productId, @PathVariable("description") String description
    		, @PathVariable("distributor") String distributor, @PathVariable("model") String model
    		, @PathVariable("name") String name, @PathVariable("price") String price
    		, @PathVariable("quantity") String quantity, @PathVariable("warranty") String warranty)
    {
		
		long id = Long.parseLong(productId);
		int quantityNew = Integer.parseInt(quantity);
		int priceNew = Integer.parseInt(price);
		int modelNew = Integer.parseInt(model);
		
		

		
		product myProduct = productRepository.findByid(id);
		
		myProduct.setDescription(description);
		myProduct.setDistributorInfo(distributor);
		myProduct.setModelNumber(modelNew);
		myProduct.setName(name);
		myProduct.setPrice(priceNew);
		myProduct.setQuantityStocks(quantityNew);
		myProduct.setWarrantyStatus(warranty);
		productRepository.saveAndFlush(myProduct);//Save to Database
    }
	
	
	@PostMapping("/insertProduct/{description}/{distributor}/{model}/{name}/{price}/{quantity}/{warranty}")//Product Manager inserts new product
    public void insertProduct(@PathVariable("description") String description
    		, @PathVariable("distributor") String distributor, @PathVariable("model") String model
    		, @PathVariable("name") String name, @PathVariable("price") String price
    		, @PathVariable("quantity") String quantity, @PathVariable("warranty") String warranty)
    {
		
		int quantityNew = Integer.parseInt(quantity);
		int priceNew = Integer.parseInt(price);
		int modelNew = Integer.parseInt(model);
		

		
		product myProduct = new product();
		
		myProduct.setDescription(description);
		myProduct.setDistributorInfo(distributor);
		myProduct.setModelNumber(modelNew);
		myProduct.setName(name);
		myProduct.setPrice(priceNew);
		myProduct.setQuantityStocks(quantityNew);
		myProduct.setWarrantyStatus(warranty);
		myProduct.setDiscounted(false);
		myProduct.setDiscountedPrice(priceNew);
		productRepository.save(myProduct);//Save to Database
		
		
		ProductRating pr = new ProductRating(myProduct);
		pr.setAverageRating(0); // 0 rating means no rating
		pr.setCounter(0);
		ProductRatingRepository.saveAndFlush(pr);
		
    }
	
	@PostMapping("/deleteProductById/{id}")//Product Manager deletes a row from product table using id
    public void deleteProduct(@PathVariable("id") String productId)
    {
		
		long id = Long.parseLong(productId);
		
		
		product myProduct = productRepository.findByid(id);
		

		productRepository.deleteById(id);//Save to Database
    }
	
	
	//FURKANIN PRODUCT MANAGER METHODLARI BURDA BITIYOR
	//FURKANIN PRODUCT MANAGER METHODLARI BURDA BITIYOR
	//FURKANIN PRODUCT MANAGER METHODLARI BURDA BITIYOR
	//FURKANIN PRODUCT MANAGER METHODLARI BURDA BITIYOR
	//FURKANIN PRODUCT MANAGER METHODLARI BURDA BITIYOR
	
	
	
	//POYTAZIN MAIL MANAGER METHODLARI
	//POYTAZIN MAIL MANAGER METHODLARI
	//POYTAZIN MAIL MANAGER METHODLARI
	//POYTAZIN MAIL MANAGER METHODLARI
	//POYTAZIN MAIL MANAGER METHODLARI
	
	@PostMapping("/createPDF/{productid}")
    public void createPDF(@PathVariable("productid") long productid) throws FileNotFoundException, DocumentException

    {
        product p = productRepository.findByid(productid);
        System.out.println("found product p:" + p.getID() +" " +p.getDescription());
        CreatePDF.createPDF(p.getPrice(),p.getDiscountedPrice());

    }

    
  //POYTAZIN MAIL MANAGER METHODLARI
  //POYTAZIN MAIL MANAGER METHODLARI
  //POYTAZIN MAIL MANAGER METHODLARI
  //POYTAZIN MAIL MANAGER METHODLARI
  //POYTAZIN MAIL MANAGER METHODLARI
    @GetMapping("/getProductsByCat/{categoryId}")
    public ResponseEntity<?> getAllsomething(@PathVariable("categoryId") String categoryId)
    {
		long id = Long.parseLong(categoryId);

        List<ProductCategories> categoryList = productCategoriesRepository.findBycategoryId(id);

        return new ResponseEntity<List<ProductCategories>>(categoryList, HttpStatus.OK);
    }	
    
    
    @GetMapping("/testfunc/{paymentdate}")
    public ResponseEntity<?> testfunc(@PathVariable("paymentdate") String paymentdate)
    {
    
    	Timestamp t = null;
		try {
			t = new Timestamp(DateUtil.provideDateFormat().parse(paymentdate).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        List<checkedOutItems> checkedlist = checkedOutItemsRepository.findByPaymentDate(t);

        return new ResponseEntity<List<checkedOutItems>>(checkedlist, HttpStatus.OK);
    }	

    @GetMapping("/testfunc2")
    public ResponseEntity<?> testfunc2()
    {

        List<checkedOutItems> checkedlist = checkedOutItemsRepository.findAll();

        return new ResponseEntity<List<checkedOutItems>>(checkedlist, HttpStatus.OK);
    }	
    
    /* 
    // we dont use this one, poyraz
    @PostMapping("/createInvoice_PDF/{userId}/{paymentDate}")
    public void createInvoice_PDF(@PathVariable("userId") long userId, 
            @PathVariable("paymentDate") String paymentDate) throws FileNotFoundException, DocumentException

    {
        Timestamp t = null;
        try {
            t = new Timestamp(DateUtil.provideDateFormat().parse(paymentDate).getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // if below repository line written in CreatePDF class it could not find anything so it returns NULL.
        List<checkedOutItems> Invoice = checkedOutItemsRepository.findByuserIdAndPaymentDate(userId, t); 

        String invoice_date = paymentDate.toString(); // 2020-05-25T13:23:24.000+0000

        String[] parts = invoice_date.split("T");
        String part1 = parts[0]; //  2020-05-25
        String part2 = parts[1]; //  13:23:24.000+0000

        part2 = part2.substring(0,8); //13:23:24


        String Message ="Thank you for your purchase at: " + part1 + " " + part2 +"\n" +"\n" + "Your purchase includes: " + "\n";
        int total = 0;


        for(checkedOutItems Item: Invoice)
        {
            //System.out.println(Item.getUser().getFullname()); // for debugging
        	
            int total_price_of_one_product = Item.getProduct().getPrice() * (Item.getQuantity());
            total += total_price_of_one_product;
            
            
            
            Message = Message + " " +"Product Name: " + (Item.getProduct().getName()) + " " +" - Quantity: " + Item.getQuantity() 
            +" - Product price: "+Item.getProduct().getPrice()
            + " - total price: "+total_price_of_one_product +"\n\n" ;


        }
        Message = Message + "\n" +"Total price: " + total;

        CreatePDF.createInvoicePDF(Message);

    }
    */
    
    //REVIEW
    //REVIEW
    //REVIEW
    //REVIEW
    //REVIEW
    @GetMapping("/returnAllReviews")
    public ResponseEntity<?> returnAllReviews()
    {
    	
        List<Review> allReviews = reviewRepository.findAll();

        return new ResponseEntity<List<Review>>(allReviews, HttpStatus.OK);
    }	
    
    @GetMapping("/returnAllPendingReviews")
    public ResponseEntity<?> returnAllPendingReviews()
    {
    	
        List<Review> allPendingReviews = reviewRepository.findByReviewStatus(null);
        		
        return new ResponseEntity<List<Review>>(allPendingReviews, HttpStatus.OK);
    }
    
    @GetMapping("/returnReviewByUserId/{userId}")
    public ResponseEntity<?> returnReviewByUserId(@PathVariable("userId") String userId)
    {
		long id = Long.parseLong(userId);

        List<Review> usersReviews = reviewRepository.findByuserId(id);

        return new ResponseEntity<List<Review>>(usersReviews, HttpStatus.OK);
    }	
    
    
    @GetMapping("/returnReviewByProductId/{productId}")
    public ResponseEntity<?> returnReviewByProductId(@PathVariable("productId") String productId)
    {
		long id = Long.parseLong(productId);

        List<Review> productsReviews = reviewRepository.findByproductIdAndReviewStatus(id, true);

        return new ResponseEntity<List<Review>>(productsReviews, HttpStatus.OK);
    }	

    @PostMapping("/createReview/{productIds}/{userIds}/{reviewText}")
    public void createReview(@PathVariable("productIds") String productIds, 
    		@PathVariable("userIds") String userIds, 
    		@PathVariable("reviewText") String reviewText) 
    {
    	
        product p = productRepository.findByid(Long.parseLong(productIds));
        User u = UserRepository.findByid(Long.parseLong(userIds));
		
		Review review = new Review(p, u, reviewText);

		reviewRepository.saveAndFlush(review);

    }

    	
    @GetMapping("/reviewStatusChange/{reviewId}/{reviewStatuss}")
    public void reviewStatusChange(@PathVariable("reviewId") String reviewId, @PathVariable("reviewStatuss") String reviewStatuss)
    {
        Review review = reviewRepository.findByReviewId(Long.parseLong(reviewId));
        
        review.setReviewStatus(Boolean.parseBoolean(reviewStatuss));
        //FALSE OLURSA SILINEBILIR

    }	
    	

    
    //REVIEW
    //REVIEW
    //REVIEW
    //REVIEW
    //REVIEW
    
    //Delivery
  	
    @GetMapping("/deliveryStatusChange/{checkOutId}/{deliveryStatus}")
    public void deliveryStatusChange(@PathVariable("checkOutId") String checkOutId,
    		@PathVariable("deliveryStatus") String deliveryStatus) throws Exception
    {
        checkedOutItems checkedOutItem = checkedOutItemsRepository.findBycheckOutId(Long.parseLong(checkOutId));
        
        //////////////////POYRAZ///////////////////////////////////
        Boolean bool_deliveryStatus = Boolean.parseBoolean(deliveryStatus);
        if(bool_deliveryStatus == true) {
        	//send mail
        	String message = "Your purchase of " + checkedOutItem.getProduct().getName() + " product delivery has been shipped\n" 
        	+ "Product Description:" + checkedOutItem.getProduct().getDescription() +", Quantity: "+checkedOutItem.getQuantity()+"";
        	MailUtil.sendDeliveryMail(checkedOutItem.getUser().getEmail(), message);
        }
        //////////////////POYRAZ///////////////////////////////////
        
        checkedOutItem.setDeliveryStatus(Boolean.parseBoolean(deliveryStatus));
    }	
    
    @GetMapping("/returnAllPendingDelivery")
    public ResponseEntity<?> returnAllPendingDelivery()
    {
    	
        List<checkedOutItems> allPendingDelivery = checkedOutItemsRepository.findByDeliveryStatus(null);
        		
        return new ResponseEntity<List<checkedOutItems>>(allPendingDelivery, HttpStatus.OK);
    }

    //Delivery
    
    // tüm userlara gönderilecek
    // gives spesific user a coupon
    // and sends that coupon to user's email
    
    
    @PostMapping("/addCoupon/{coupon_string}/{dicount_ratio}/{productId}/{userIds}")
    public void addCoupon(@PathVariable("coupon_string") String coupon_string,@PathVariable("dicount_ratio") int dicount_ratio,
    		 @PathVariable("productId") String productId, @PathVariable("userIds") String userIds) 
    
    {   	
    
    	if( 0 < dicount_ratio && dicount_ratio < 100) {
    		
    		long product_id = Long.parseLong(productId);
    		Coupon c = new Coupon(coupon_string);
        	c.setDiscount(dicount_ratio);
        	c.setProduct_id(product_id);
        	
        	CouponRepository.saveAndFlush(c);; // Save to Database
    		
        	product p = productRepository.findByid(Long.parseLong(productId));
        	
        	
        	
        	User u = UserRepository.findByid(Long.parseLong(userIds));
        	try {
				MailUtil.sendDiscountMail(u.getEmail(),"\n You can use this coupon code in your cart to apply to this item : "+ p.getName()+"\n couponString: "  + coupon_string);
			} catch (Exception e) {
				System.out.println("User does not have mail");
				e.printStackTrace();
			}
        	
        	
        	
        	
        	//List<User> userList = UserRepository.findAll(); // gets all the users in a list
        	

        	/*
	        for(User User: userList)
	        {
	            // If user has an email then send discount message.
	            if(User.getEmail()!=null) 
	            {
	                try {
	                	
						
	                	MailUtil.sendDiscountMail(User.getEmail(),"\n You can use this coupon code in your cart to apply discount: "+ coupon_string);
					} 
	                
	                catch (Exception e) {
						
						System.out.println("user does not have  email");
						e.printStackTrace();
					
					}
	            }

	        }
	        */
        	

    	
    	}
    	
    	else {	System.out.println("discount ratio must be between 0 and 100");	}
    	
    }
    
    @PostMapping("/testApplyCoupon/{coupon_string}")
    public String applyCoupon(@PathVariable("coupon_string") String coupon_string)
    {
    	
       	
    	Boolean exists = CouponRepository.existsBycoupon(coupon_string);
    	// if this coupon does not exist (NULL) then return coupon is not valid.
		if(exists == false) 
		{
			return "Coupon does not exist."; 
			// invoice * 1 = invoice which means no discount.
			// we can pop-up some kind of message says:
			//"this coupon code does not exist"
		}
        
		//else it exists
		Coupon c =  CouponRepository.findBycoupon(coupon_string);
		
		UserSelectsProduct row = userSelectsProductRepository.findByProductId(c.getProduct_id());
    	row.setCouponApplied(true);
    	// int discountedPrice = product1.getPrice() - ((product1.getPrice() * Integer.parseInt(discountPercentages)) / 100);
    	//int discounted_price = (int) ( (100-c.getDiscount()) / 100 ) * row.getSelectProductPrice();
    	int discounted_price =  row.getSelectProductPrice() - (row.getSelectProductPrice() * (c.getDiscount()) / 100);
    	row.setSelectProductPrice(discounted_price);
		
		
        return "Coupon applied.";
    }
    
    
    
    @PostMapping("/createRating/{productIds}/{userIds}/{rating}")
    public String createRating(@PathVariable("productIds") String productIds, 
    		@PathVariable("userIds") String userIds, 
    		@PathVariable("rating") long rating) 
    {
    	
    	List<Rating> list_rating = RatingRepository.findByuserIdAndProductId(Long.parseLong(userIds), Long.parseLong(productIds));
    	
    	if(!list_rating.isEmpty()) {
    		return "you already submitted a rating";
    	}
    	
    	
        product p = productRepository.findByid(Long.parseLong(productIds));
        User u = UserRepository.findByid(Long.parseLong(userIds));
		
        ProductRating pr = ProductRatingRepository.findByproductId(p.getID());
        
        
        
		Rating r = new Rating(u,p,rating);
		
		int counter = pr.getCounter();
		
		pr.setAverageRating(	((pr.getAverageRating()*counter) + r.getRating())  /  (counter + 1)	   );
		
		
		
		pr.setCounter(counter + 1);
		ProductRatingRepository.saveAndFlush(pr);
		RatingRepository.saveAndFlush(r);
		return "180";

    }
    
    
    @GetMapping("/getAllOrders/{userIds}")
    public ResponseEntity<?> getAllOrders(@PathVariable("userIds") String userIds) 
    {
    	
    	List<checkedOutItems> list_checkout = checkedOutItemsRepository.findByuserId(Long.parseLong(userIds));		
        return new ResponseEntity<List<checkedOutItems>>(list_checkout, HttpStatus.OK);

    }
    
    @GetMapping("/showRating/{productIds}")
    public ResponseEntity<?> showRating(@PathVariable("productIds") String productIds) 
    {

        product p = productRepository.findByid(Long.parseLong(productIds));
        
		
        ProductRating pr = ProductRatingRepository.findByproductId(p.getID());
        
        return new ResponseEntity<ProductRating>(pr, HttpStatus.OK);
        
    }
    
    @GetMapping("/showUsersRating/{userIds}/{productIds}")
    public ResponseEntity<?> showUsersRating(@PathVariable("userIds") String userIds
                                            ,@PathVariable("productIds") String productIds) 
    {
        Long userId = Long.parseLong(userIds);

        Long productId = Long.parseLong(productIds);
        
        if(RatingRepository.existsByuserIdAndProductId(userId, productId)) {
            List<Rating> rating = RatingRepository.findByuserIdAndProductId(userId, productId);

             return new ResponseEntity<List<Rating>>(rating, HttpStatus.OK);
        }

        return null;
    }
    
    @GetMapping("/fetchProductsRatingAsc")
    public ResponseEntity<?> fetchProductsRatingAsc() 
    {
        List<ProductRating> pr = ProductRatingRepository.findAllByOrderByAverageRatingAsc();

        return new ResponseEntity<List<ProductRating>>(pr, HttpStatus.OK);

    }

    @GetMapping("/fetchProductsRatingDsc")
    public ResponseEntity<?> fetchProductsRatingDsc() 
    {
        List<ProductRating> pr = ProductRatingRepository.findAllByOrderByAverageRatingDesc();

        return new ResponseEntity<List<ProductRating>>(pr, HttpStatus.OK);

    }

}


	
















