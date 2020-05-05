package com.bridgelabz.fundoonotes.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Labels {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String labelname;

	@JsonIgnore
	@ManyToOne
	private Users usersDetails;

	@JsonIgnore
	@ManyToMany(mappedBy = "labels")
	private List<Notes> notes;

	public Labels() {
		super();
	}

	public Labels(String labelname, Users usersDetails) {
		super();
		this.labelname = labelname;
		this.usersDetails = usersDetails;
	}

	public Users getUsersdetails() {
		return usersDetails;
	}

	public void setUsersdetails(Users usersDetails) {
		this.usersDetails = usersDetails;
	}

	public List<Notes> getNotes() {
		return notes;
	}

	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabelname() {
		return labelname;
	}

	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}

	public String toString() {
		return "Label [id=" + id + ", labelname=" + labelname + "]";
	}

}
