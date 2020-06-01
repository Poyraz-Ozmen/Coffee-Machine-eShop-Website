package com.eshop.eshopManagerAPI.models;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.swing.text.StyleConstants.FontConstants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.beans.factory.annotation.Autowired;

import com.eshop.eshopManagerAPI.repository.productRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePDF  {
	
	public static void createPDF (long original_price,long discounted_price) throws FileNotFoundException, DocumentException {
	
		
		
		//System.out.println("found product p:" + p.getID() +" " +p.getDescription());
		
		//long original_price = p.getPrice();
		//long discounted_price = p.getDiscountedPrice();
		
		
		long discount_ratio = (original_price - discounted_price) / (original_price);
	
		
		
		String s1 ="original price is" + Long.toString(original_price);
		String s2 ="discount price is" + Long.toString(discounted_price);
		String s3 ="discount ratio is" + Long.toString(discount_ratio);
		
		//Change to test.
		String dest = "C:\\Users\\poyraz\\Desktop\\cs308text\\discount.pdf";
		
		Document document = new Document();
		
		PdfWriter.getInstance(document, new FileOutputStream(dest));
		
		//now open the document
		document.open();
		
		//create paragraph
		Paragraph paragraph = new Paragraph(s1);
		Paragraph paragraph2 = new Paragraph(s2);
		Paragraph paragraph3 = new Paragraph(s3);
		//add paragraph to pdf
		document.add(paragraph);
		document.add(paragraph2);
		document.add(paragraph3);
		
		//close
		document.close();
		
	
	}

	
	public static void createInvoicePDF (String Message) throws FileNotFoundException, DocumentException {


        //List<checkedOutItems> Invoice = checkedOutItemsRepository.findByuserIdAndPaymentDate(userId, paymentDate);

        //String Message = "Order Date" + paymentDate.toString()+"\n"+"\n";
        /*
        for(checkedOutItems checkedOutItem: Invoice)
        {


            Message = Message + " " +"Check Out Id:" + Long.toString(checkedOutItem.getCheckOutId())+"\n";


        }
        */



        String dest = "C:\\Users\\poyraz\\Desktop\\cs308text\\invoice.pdf";

        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(dest));

        //now open the document
        document.open();

        //create paragraph
        Paragraph paragraph = new Paragraph(Message);
        //Paragraph paragraph2 = new Paragraph(s2);
        //add paragraph to pdf
        document.add(paragraph);
        //document.add(paragraph2);

        //close
        document.close();


    }

	
	
	 
	
}
