package com.designops.model;

import java.util.List;

public class Preview {
	private String lockTitle;
	
	private String summdescription;
	
	private List<PreviewImage> previewImages;
	
	private List<CodeSection> codeSection;

	public String getLockTitle() {
		return lockTitle;
	}

	public void setLockTitle(String lockTitle) {
		this.lockTitle = lockTitle;
	}

	public String getSummdescription() {
		return summdescription;
	}

	public void setSummdescription(String summdescription) {
		this.summdescription = summdescription;
	}

	public List<PreviewImage> getPreviewImages() {
		return previewImages;
	}

	public void setPreviewImages(List<PreviewImage> previewImages) {
		this.previewImages = previewImages;
	}

	public List<CodeSection> getCodeSection() {
		return codeSection;
	}

	public void setCodeSection(List<CodeSection> codeSection) {
		this.codeSection = codeSection;
	}
	
	

}
