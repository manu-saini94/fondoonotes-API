package com.bridgelabz.fundoonotes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	// id should be wrapper Long
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mobileNumber;

	private String firstName;

	private String lastName;

	private String emailId;

	private String password;

	@Column(columnDefinition = "boolean default false")
	private boolean isEmailVerified;

	@Column(columnDefinition = "timestamp default current_timestamp")
	private Date createdDate;

	private String profilePicUrl;

	@JsonIgnore
	@OneToMany(mappedBy = "usersDetails")
	private List<Notes> notes;

	@JsonIgnore
	@OneToMany(mappedBy = "usersDetails")
	private List<Labels> labels;

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<Notes> getNotes() {
		return notes;
	}

	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	public List<Labels> getLabels() {
		return labels;
	}

	public void setLabels(List<Labels> labels) {
		this.labels = labels;
	}

}
