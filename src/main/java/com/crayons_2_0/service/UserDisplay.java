package com.crayons_2_0.service;

public final class UserDisplay {
	private String email;
	private String name;
	private String role;

	private int createdCourses;
	private int visitedCourses;

	public UserDisplay(String email, String name, String role,
			int createdCourses, int visitedCourses) {
		this.email = email;
		this.name = name;
		this.role = role;
		this.createdCourses = createdCourses;
		this.visitedCourses = visitedCourses;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getCreatedCourses() {
		return createdCourses;
	}

	public void setCreatedCourses(int createdCourses) {
		this.createdCourses = createdCourses;
	}

	public int getVisitedCourses() {
		return visitedCourses;
	}

	public void setVisitedCourses(int visitedCourses) {
		this.visitedCourses = visitedCourses;
	}
}