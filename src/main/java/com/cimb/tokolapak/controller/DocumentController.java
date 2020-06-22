package com.cimb.tokolapak.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.cimb.tokolapak.dao.UserRepo;

@RestController
@RequestMapping("/documents")
@CrossOrigin
public class DocumentController {
	private String uploadPath = System.getProperty("user.dir") +
			"\\src\\main\\resources\\static\\images\\";
	
	@Autowired //global satu folder
	private UserRepo userRepo;
	
	@GetMapping("/testing")
	public void testing() {
		System.out.println(uploadPath);
	}

	@PostMapping //190620
	public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userData") String userString) throws JsonMappingException, JsonProcessingException {
		Date date = new Date();
		
		User user = new ObjectMapper().readValue(userString, User.class); //satu function
		
		String fileExtension = file.getContentType().split("/")[1];
		System.out.println(fileExtension);
		String newFileName = "PROD-" + date.getTime() + "." + fileExtension;

		// Get files original name || able to be generate by our own
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String fileName = StringUtils.cleanPath(newFileName);
		
		// Create Path to upload destination + new file name
		Path path = Paths.get(uploadPath + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/documents/download/")
				.path(fileName).toUriString();
		//show data after upload in download file
		
		user.setProfilePicture(fileDownloadUri);
		
		userRepo.save(user);
		
		return fileDownloadUri;
//		return fileName + " has been uploaded!";
	}
	
	@PostMapping("/login/{username}")
	public User loginWithProfilePicture(@RequestBody User user) {
		return userRepo.findByUsername(user.getUsername()).get();
	}
	
	/*
	 * Axios.post(API/documents, file)
	 * .then(res => { window.open(res.data) })
	 * */

	
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName){
		Path path = Paths.get(uploadPath + fileName);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
//		System.out.println("DOWNLOAD");
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename()+ "\"")
				.body(resource);
	}
}
