package com.project.nasnav.rs.control;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE_Details")
public class ImageDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer imageId;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "STATUS")
	private String status;
	
	@ManyToOne
	private User user;
	
	@Column(length = 16000000) // This should generate a medium blob
    @Basic(fetch = FetchType.LAZY) 
	private byte[] data;
	
	@Column(name = "SIZE")
	 private Long size;

	@Column(name = "CONTENT_TYPE")
	private String contentType;
	
	public ImageDetails() {
		super();
	}
	
	public ImageDetails(String description, String category, Integer userId, byte[] data,
			Long size, String contentType) {
		super();
		this.description = description;
		this.category = category;
		this.user = new User(userId, "", "");
		this.data = data;
		this.size = size;
		this.contentType = contentType;
	}

	public ImageDetails(Integer imageId, String description, String category, String status, Integer userId, byte[] data,
			Long size, String contentType) {
		super();
		this.imageId = imageId;
		this.description = description;
		this.category = category;
		this.status = status;
		this.user = new User(userId, "", "");
		this.data = data;
		this.size = size;
		this.contentType = contentType;
	}
	
	public ImageDetails(Integer id) {
		this.user = new User(id, "", "");
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setUser(Integer id, String string, String string2) {
		// TODO Auto-generated method stub
		
	}

}
