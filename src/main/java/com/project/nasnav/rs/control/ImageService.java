package com.project.nasnav.rs.control;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	
	
	private final ImageReository imageReository;
	
	@Autowired
	public ImageService(ImageReository imageReository) {
		this.imageReository = imageReository;
	}

	@Autowired 
	UserRepository userRepository;
	
	public void addImage(MultipartFile image, String email, String description, String category) throws IOException{
		User user = userRepository.findByEmail(email);
		
		ImageDetails imageDetail = new ImageDetails(user.getId());
		imageDetail.setDescription(description);
		imageDetail.setCategory(category); 
		imageDetail.setData(image.getBytes());
		imageDetail.setSize(image.getSize());
		imageDetail.setContentType(image.getContentType());
		
		imageReository.save(imageDetail);
	}
	
	public void udateImage(ImageDetails imageDetails) {
		imageReository.saveAndFlush(imageDetails);
	}
	
	public List<ImageDetails> getAllApprovedImages() {
        return imageReository.findAllByStatus("Accept");
    }
	
	public List<ImageDetails> getAllUploadedImages() {
        return imageReository.findAllByStatus(null);
    }
	
	public Optional<List<ImageDetails>> getAllUserImages(User user) {
		if (user.getEmail().equalsIgnoreCase("admin") 
				&& user.getPassword().equalsIgnoreCase("admin123")) {
			return Optional.of(imageReository.findAll());
		}
		else {
			return Optional.of(imageReository.findAllByUserAndStatus(user, "Accept"));
		}
    }
	
	public Optional<ImageDetails> getImageById(Integer imageId) {
		return imageReository.findById(imageId);
	}

}
