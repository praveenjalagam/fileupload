package com.example.jp.filesaveservice;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class FileUploadService {
	
	
	
	 //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "D://rough//upload//";
    private static String UPLOADED_FOLDER_WORK = "D://rough//work//";
    
    
    
    public boolean upLoadPDFFile(MultipartFile file){
    	
    	boolean fileStaus=true;
    	   
		try {
			
			   byte[] bytes;
			   bytes = file.getBytes();		
               Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());        
			   Files.write(path, bytes);
			   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fileStaus=false;
		}
    	
    	
    	return fileStaus;
    }
    
    
    public boolean upLoadTXTFile(MultipartFile file){
    	
    	boolean pdfStatus = true;
    	
    	 System.out.println("Inside FileUploadService:  upLoadTXTFile ....");
    	
    	  FileInputStream iStream=null;
    	  DataInputStream in=null;
    	  InputStreamReader is=null;
    	  BufferedReader br=null;
		  try {
			    		  
			    		File conMyFIle = saveFiletoWorkDir(file);
			    		
			    		if(conMyFIle.getParent()!=null) {
			    			
			    			
			    			String pdfFileName = conMyFIle.getName();
			    			
			    			
			    			pdfFileName= pdfFileName.substring(0,pdfFileName.indexOf("."));
			    		
			    			
			    			System.out.println("Name conMyFIle is 222  >> " + pdfFileName);
			    			
			    			System.out.println("Saved File loction >>> "  + conMyFIle.getParent());    		  
				    	    Document pdfDoc = new Document();
				    	       
				    	    String text_file_name =UPLOADED_FOLDER+"//"+pdfFileName+".pdf";
				    	    PdfWriter writer=PdfWriter.getInstance(pdfDoc,new FileOutputStream(text_file_name));
				    	    pdfDoc.open();
				    	    pdfDoc.setMarginMirroring(true);
				    	    pdfDoc.setMargins(36, 72, 108,180);
				    	    pdfDoc.topMargin();
				    	    Font normal_font = new Font();
				    	    Font bold_font = new Font();
				    	    bold_font.setStyle(Font.BOLD);
				    	    bold_font.setSize(10);
				    	    normal_font.setStyle(Font.NORMAL);
				    	    normal_font.setSize(10);
				    	    pdfDoc.add(new Paragraph("\n"));
				    	    if(conMyFIle.exists())
				    	    {
				    	    iStream = new FileInputStream(conMyFIle);
				    	     in = new DataInputStream(iStream);
				    	     is=new InputStreamReader(in);
				    	     br = new BufferedReader(is);
				    	    String strLine;
				    	    while ((strLine = br.readLine()) != null)   {
				    	     Paragraph para =new Paragraph(strLine+"\n",normal_font);
				    	     para.setAlignment(Element.ALIGN_JUSTIFIED);
				    	     pdfDoc.add(para);
				    	    }
				    	    }   
				    	    else
				    	    {
				    	     System.out.println("file does not exist");
				    	     pdfStatus = false;
				    	    }
				    	    pdfDoc.close(); 
			    			
			    		}else {
			    			
			    			pdfStatus = false;
			    		}
			    		  
			    		
			    	    
			    	    
			    	    
		 }catch(Exception e){
    	   System.out.println(" exception = " + e.getMessage());
    	 }
    	  finally
    	  {
    	    
    	    try {
    	     if(br!=null)
    	     {
    	     br.close(); 
    	     }
    	     if(is!=null)
    	     {
    	     is.close();
    	     }
    	     if(in!=null)
    	     {
    	     in.close();
    	     }
    	     if(iStream!=null)
    	     {
    	      iStream.close();
    	     }
    	    } catch (IOException e) {
    	     // TODO Auto-generated catch block
    	     e.printStackTrace();
    	    }
    	    
    	  }
    	       
		      return pdfStatus;
    	  
    	  
    	 }
    
    
    private File saveFiletoWorkDir(MultipartFile file ) throws IOException
    {
              
    	    File savedFile=null;
     		try {
			
			   byte[] bytes;
			   bytes = file.getBytes();	
			   
			   
			   System.out.println("file.getOriginalFilename() is >>>>>>>> " + file.getOriginalFilename());
			   
			   
               Path path = Paths.get(UPLOADED_FOLDER_WORK + file.getOriginalFilename());   
               
               
               System.out.println("Ptah is >>" + path);
			   Files.write(path, bytes);
			   
			   File tempFile = new File(UPLOADED_FOLDER_WORK + file.getOriginalFilename());
			   
			   System.out.println("Temp File Paretn" + tempFile.getParent());
			   
			   if(tempFile.exists()) {
				   
				   savedFile = tempFile;
			   }
			   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
			return savedFile;
    	    	
    	  
    }
        
        
   
 }


