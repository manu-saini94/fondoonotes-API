package com.bridgelabz.fundoonotes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity	
public class Collaborator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String collaborator;
	
	@JsonIgnore
	@ManyToOne
	private Notes notes;
	
	public Collaborator() {
		super();
	}

	public Collaborator(String collaborator, Notes notes) {
		super();
		this.collaborator = collaborator;
		this.notes = notes;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(String collaborator) {
		this.collaborator = collaborator;
	}

	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
	}
	
	
}
