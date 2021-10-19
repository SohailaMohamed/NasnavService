package com.project.nasnav.rs.control;

public class ImageResponse {
	
	private Integer imageId;
    private String name;
    private Long size;
    private String url;
    private String description;
    private String category;
    private User user;
	private boolean status;
	public ImageResponse() {
		super();
	}
	public ImageResponse(Integer imageId, String name, Long size, String url, String description, String category, User user,
			boolean status) {
		super();
		this.imageId = imageId;
		this.name = name;
		this.size = size;
		this.url = url;
		this.description = description;
		this.category = category;
		this.user = user;
		this.status = status;
	}
	public Integer getImageId() {
		return imageId;
	}
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

}
