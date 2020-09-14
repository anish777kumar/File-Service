package com.bunnu.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bunnu.entity.Documents;
import com.bunnu.service.DocumentsService;

// TODO: Auto-generated Javadoc
/**
 * The Class DocumentsServiceController.
 */
@RestController
@RequestMapping("file-utils")
public class DocumentsServiceController {

	/** The doc service. */
	@Autowired
	DocumentsService docService;

	/** The log. */
	Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @param username the username
	 * @return the response entity
	 */
	@PostMapping("/")
	public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, 
									 @RequestHeader(name = "username", required = false) String username
	    		 				     )
	{
		log.info("/uploadFile is invoked   :"+file);
	    Documents doc = docService.storeFile(file, username);
	    return new ResponseEntity(doc,HttpStatus.CREATED);
	 
	}
	
	/**
	 * Download file.
	 *
	 * @param documentNameOrId the document name or id
	 * @param userName the user name
	 * @param request the request
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@GetMapping("/{documentNameOrId}")
	public ResponseEntity downloadFile(@PathVariable("documentNameOrId")String documentNameOrId,
									   @RequestHeader(name ="username",required=false) String userName,
									  HttpServletRequest request) throws Exception
	{
		
				Optional<File> file = docService.getFileBy(documentNameOrId, userName,false);
		        Resource resource = null;
		       
		        if(file.isPresent()) 
		        {
		        	resource = docService.loadFileAsResource(file.get());
		        	
		        // Try to determine file's content type
		        	String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		        // Fallback to the default content type if type could not be determined
		        	if(contentType == null)
		        		contentType = "application/octet-stream";
		
		
		        return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                .body(resource);
		
		        }
		        else 
		        {
		        	return ResponseEntity.notFound().build();
		        }
	}
	
	/**
	 * Delete file.
	 *
	 * @param documentNameOrId the document name or id
	 * @param userName the user name
	 * @return the response entity
	 * @throws FileNotFoundException the file not found exception
	 */
	@DeleteMapping("/{documentNameOrId}")
	public ResponseEntity deleteFile(@PathVariable("documentNameOrId")String documentNameOrId,
			   						 @RequestHeader(name ="username") String userName
			   						 ) throws FileNotFoundException
	{
		Optional<File> file = docService.getFileBy(documentNameOrId, userName,true);
		
		if(file.isPresent())
		{
			boolean flag = file.get().delete();
			if(flag)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	

}
