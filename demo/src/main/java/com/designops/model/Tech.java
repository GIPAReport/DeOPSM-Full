package com.designops.model;

import java.util.List;

public class Tech {

	private List<Preview> previews;
	
	private List<RichTextBlock> richTextBlock;

	public List<Preview> getPreviews() {
		return previews;
	}

	public void setPreviews(List<Preview> previews) {
		this.previews = previews;
	}

	public List<RichTextBlock> getRichTextBlock() {
		return richTextBlock;
	}

	public void setRichTextBlock(List<RichTextBlock> richTextBlock) {
		this.richTextBlock = richTextBlock;
	}
	
	
}
