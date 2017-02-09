package com.crayons_2_0.service;

public final class CourseDisplay {
    private String release;
    private String author;
    private String status;
    private String title;


    public CourseDisplay(String release, String title, String author,
			String status) {
		this.release = release;
		this.author = author;
		this.title = title;
		this.status = status;		
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(final String release) {
        this.release = release;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
