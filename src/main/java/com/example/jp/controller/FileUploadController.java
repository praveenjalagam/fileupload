package com.example.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jp.filesaveservice.FileUploadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController{
	
	
	@Autowired
	FileUploadService fileUploadService;

   

    @GetMapping("/")
    public String index() {
        return "myfileupload";
    }

    @PostMapping("/upload") 
    public String fileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
    	
    	
    	System.out.println("Inside FileUpload........"+ file.isEmpty());

        if (file.isEmpty()) {
        	
        	System.out.println("Inside >>>>>>>>>>>>>>>>>>>>PJJJJ ");
        	
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/upload";
            
            
        }

     

            String orgFname = file.getOriginalFilename();        	
        	System.out.println("<<<<<<<<<<>>>>>>>>>>>>>> "+ orgFname);
        	int extVal = orgFname.indexOf(".");
        	String fileExtn = orgFname.substring(extVal+1);
        	
        	
        	
        	
        	if(fileExtn.equalsIgnoreCase("txt")) {
        		
		        		System.out.println("inside File extension is txt >>>> " + fileExtn);
		        		
		        		
		        		
		        		if(fileUploadService.upLoadTXTFile(file)) {
		        			
		        			 redirectAttributes.addFlashAttribute("message",
		                             "You successfully uploaded '" + file.getOriginalFilename() + "'");
		        			
		        		}else {
		        			
		        			 redirectAttributes.addFlashAttribute("message",
		                             "Error occured in file uploaded '" + file.getOriginalFilename() + "'");
		        		}
        		
        		 
        		
        	}else {
        		
        		
        		System.out.println("inside File extension is pdf  >>>> " + fileExtn);
        		
        		if(fileUploadService.upLoadPDFFile(file)) {
        			
        			redirectAttributes.addFlashAttribute("message",
                            "You successfully uploaded '" + file.getOriginalFilename() + "'");
        			
        		}else {
        			
        			redirectAttributes.addFlashAttribute("message",
                            "Error occured in file uploaded '" + file.getOriginalFilename() + "'");
        			
        		}
        		  
        		  
        	}
        		
          

        return "redirect:/upload";
    }

    @GetMapping("/upload")
    public String uploadStatus() {
        return "myfileupload";
    }

}
