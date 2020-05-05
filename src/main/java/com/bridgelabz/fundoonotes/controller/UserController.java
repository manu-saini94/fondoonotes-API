package com.bridgelabz.fundoonotes.controller;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.MailIDNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.S3BucketException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.PasswordDTO;
import com.bridgelabz.fundoonotes.dto.RegisterDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.AmazonS3ClientService;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	Utility utility;

	@Autowired
	private AmazonS3ClientService amazonS3ClientService;

	@PostMapping("/register")
	public ResponseEntity<Response> registration(@Valid @RequestBody RegisterDTO userdto, BindingResult bindingresult) {

		if (bindingresult.hasErrors() || !userdto.getPassword().equals(userdto.getPasswordagain())) {
			return ResponseEntity.badRequest()
					.body(new Response(400, "Errors_found", utility.getErrors(bindingresult, userdto)));
		} else {
			if (userService.register(userdto))
				return ResponseEntity.ok().body(new Response(200, "Registered Successfully", true));
			else
				return ResponseEntity.badRequest().body(new Response(400, "User already registered", false));
		}
	}

	@PutMapping("/verifyemail/{jwt}")
	public ResponseEntity<Response> checkEmail(@PathVariable("jwt") String jwt) throws JWTTokenException {
		if (userService.verifyEmail(jwt))
			return ResponseEntity.ok().body(new Response(200, "Email Verified", userService.verifyEmail(jwt)));
		else
			return ResponseEntity.badRequest()
					.body(new Response(400, "problem in verification", userService.verifyEmail(jwt)));
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDTO logindto)
			throws LoginException, MailIDNotFoundException {
		if (utility.checkMail(logindto.getEmail())) {
			String token = userService.login(logindto);
			if (token != null)
				return ResponseEntity.ok().body(new Response(200, "Login success", token));
			else
				return ResponseEntity.badRequest().body(new Response(400, "Login Unsuccessfull", null));
		} else
			throw new MailIDNotFoundException("Not a valid mail id");
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotDTO forgotdto) throws MailIDNotFoundException {
		if (utility.checkMail(forgotdto.getEmail())) {
			userService.forgotPassword(forgotdto);
			return ResponseEntity.ok().body(new Response(200, "Mail sent to your id", forgotdto));
		} else
			throw new MailIDNotFoundException("Not a valid mail id");
	}

	@PutMapping("/resetpassword/{jwt}")
	public ResponseEntity<Response> newPassword(@RequestBody PasswordDTO password, @PathVariable("jwt") String jwt)
			throws JWTTokenException {
		if (password.getNewpassword().equals(password.getConfirmnewpassword())) {
			String resetresult = userService.resetPassword(password.getNewpassword(), jwt);
			if (resetresult == null)
				return ResponseEntity.ok().body(new Response(200, "password redefined", null));
			else
				return ResponseEntity.badRequest().body(new Response(400, "Token Expired", null));
		} else
			return ResponseEntity.badRequest().body(new Response(400, "Password not matching", null));

	}

	@PostMapping("/uploadProfilepic")
	public ResponseEntity<Response> uploadFile(@RequestPart(value = "file") MultipartFile file,
			@RequestHeader("jwt") String jwt) throws S3BucketException {
		String url = this.amazonS3ClientService.uploadFileToS3Bucket(file, jwt);
		return ResponseEntity.ok().body(new Response(200, "Image added successfully", url));
	}

	@GetMapping("/profilepicUrl")
	public ResponseEntity<Response> uploadFile(@RequestHeader("jwt") String jwt) throws S3BucketException {
		String url = amazonS3ClientService.getFileFromS3Bucket(jwt);
		if (url == null)
			return ResponseEntity.badRequest().body(new Response(400, "Image not Found", url));
		else
			return ResponseEntity.ok().body(new Response(200, "Image added successfully", url));
	}

	@DeleteMapping("/deleteProfilepic")
	public ResponseEntity<Response> deleteFile(@RequestHeader("jwt") String jwt) throws S3BucketException {
		if (amazonS3ClientService.deleteFileFromS3Bucket(jwt))
			return ResponseEntity.ok().body(new Response(200, "Image deleted successfully", null));
		else
			return ResponseEntity.badRequest().body(new Response(400, "Image not Found", null));
	}

}
