package com.eshop.eshopManagerAPI.models;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.text.StyleConstants.FontConstants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

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
	
	public static void createPDF (String dest) throws FileNotFoundException, DocumentException {
	
		dest = "C:\\Users\\poyraz\\Desktop\\cs308text\\file.pdf";
		
		Document document = new Document();
		
		PdfWriter.getInstance(document, new FileOutputStream(dest));
		
		//now open the document
		document.open();
		
		//create paragraph
		Paragraph p = new Paragraph("hello world");
		
		//add paragraph to pdf
		document.add(p);
		
		//close
		document.close();
		
	
	}
	
	
	 
	
}
