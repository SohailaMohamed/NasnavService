package com.project.nasnav.rs.control;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/nasnav")
public class Controller {

	UserService userService;

	ImageService imageService;

	@Autowired
	public Controller(UserService userService, ImageService imageService) {
		this.userService = userService;
		this.imageService = imageService;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public User getUser(@RequestParam String email, @RequestParam String password) {
		return userService.getUser(email, password);
	}

	@RequestMapping(value = "/login/{email}/upload", method = RequestMethod.POST)
	public ResponseEntity<String> uploadImage(@PathVariable String email, @RequestParam("image") MultipartFile image,
			@RequestParam String description, @RequestParam String category) {
		try {

			if (!image.getContentType().isEmpty()) {
				if (image.getContentType().contains("png") || image.getContentType().contains("gif")
						|| image.getContentType().contains("jpeg")) {

					imageService.addImage(image, email, description, category);
					return ResponseEntity.status(HttpStatus.OK)
							.body(String.format("File uploaded successfully: %s", image.getOriginalFilename()));
				} else {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
							.body(String.format("Invalid Content type : %s!", image.getContentType()));
				}
			}

			else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(String.format("Please attache Image ."));
			}

		} catch (Exception e) {
			e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(String.format("Could not upload the file: %s!", image.getOriginalFilename()));
		}

	}

	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public List<ImageResponse> getAllPics() {
		return imageService.getAllApprovedImages().stream().map(this::mapToImageResponse).collect(Collectors.toList());
	}

	private ImageResponse mapToImageResponse(ImageDetails imageDetail) {
		String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/nasnav/images/")
				.path(imageDetail.getImageId().toString()).toUriString();
		ImageResponse imageResponse = new ImageResponse();
		imageResponse.setImageId(imageDetail.getImageId());
		imageResponse.setCategory(imageDetail.getCategory());
		imageResponse.setDescription(imageDetail.getDescription());
		imageResponse.setSize(imageDetail.getSize());
		imageResponse.setUrl(downloadURL);

		return imageResponse;
	}

	@RequestMapping(value = "/login/needApproves", method = RequestMethod.GET)
	public ResponseEntity<List<ImageResponse>> adminImage(@RequestParam String email, @RequestParam String password) {
		if (email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin123")) {
			return ResponseEntity.status(HttpStatus.OK).body(imageService.getAllUploadedImages().stream()
					.map(this::mapToAdminResponse).collect(Collectors.toList()));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	private ImageResponse mapToAdminResponse(ImageDetails imageDetail) {
		String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/nasnav/admin/images/")
				.path(imageDetail.getImageId().toString()).toUriString();
		ImageResponse imageResponse = new ImageResponse();
		imageResponse.setImageId(imageDetail.getImageId());
		imageResponse.setCategory(imageDetail.getCategory());
		imageResponse.setDescription(imageDetail.getDescription());
		imageResponse.setSize(imageDetail.getSize());
		imageResponse.setUrl(downloadURL);

		return imageResponse;
	}

	@RequestMapping(value = "/login/{email}/userImages", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@RequestParam("email") String email) {
		User user = userService.getUserByEmail(email);
		Optional<List<ImageDetails>> imageDetailsOptional = imageService.getAllUserImages(user);

		if (!imageDetailsOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		ImageDetails imageDetail = (ImageDetails) imageDetailsOptional.get();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + imageDetail.getDescription() + "\"")
				.contentType(MediaType.valueOf(imageDetail.getContentType())).body(imageDetail.getData());
	}

	@RequestMapping(value = "/images/{imageId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("imageId") Integer imageId) {
		Optional<ImageDetails> imageDetailsOptional = imageService.getImageById(imageId);

		if (!imageDetailsOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		ImageDetails imageDetail = (ImageDetails) imageDetailsOptional.get();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + imageDetail.getDescription() + "\"")
				.contentType(MediaType.valueOf(imageDetail.getContentType())).body(imageDetail.getData());
	}

	@RequestMapping(value = "/admin/images/{imageId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> updateImage(@PathVariable("imageId") Integer imageId, @RequestParam String email,
			@RequestParam String password) {
		if (email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin123")) {
			Optional<ImageDetails> imageDetailsOptional = imageService.getImageById(imageId);

			if (!imageDetailsOptional.isPresent()) {
				return ResponseEntity.notFound().build();
			}

			ImageDetails imageDetail = (ImageDetails) imageDetailsOptional.get();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"" + imageDetail.getDescription() + "\"")
					.contentType(MediaType.valueOf(imageDetail.getContentType())).body(imageDetail.getData());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

	}

	@RequestMapping(value = "/admin/images/{imageId}", method = RequestMethod.PUT)
	public ResponseEntity<byte[]> updateImage(@PathVariable("imageId") Integer imageId, @RequestParam String status,
			@RequestParam String email, @RequestParam String password) {
		if (email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin123")) {
			Optional<ImageDetails> imageDetailsOptional = imageService.getImageById(imageId);

			if (!imageDetailsOptional.isPresent()) {
				return ResponseEntity.notFound().build();
			}

			ImageDetails imageDetail = (ImageDetails) imageDetailsOptional.get();
			if (status.equalsIgnoreCase("Accept")) {
				imageDetail.setStatus("Accept");
				imageService.udateImage(imageDetail);
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION,
								"attachment; filename=\"" + imageDetail.getDescription() + "\"")
						.contentType(MediaType.valueOf(imageDetail.getContentType())).body(imageDetail.getData());
			} else if (status.equalsIgnoreCase("Reject")) {
				imageDetail.setStatus("Reject");
				imageDetail.setData(null);
				imageDetail.setSize(null);
				imageService.udateImage(imageDetail);
				return ResponseEntity.status(HttpStatus.OK).body(null);

			} else {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
}
