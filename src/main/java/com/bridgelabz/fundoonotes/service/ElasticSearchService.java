package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.model.Notes;

@Service
public interface ElasticSearchService {

	public String createNote(Notes note) throws IOException;
	public String updateNote(Notes note) throws Exception;
	public Notes findById(String id) throws Exception;
	public String deleteNote(Notes note) throws IOException;
	List<Notes> getNoteByTitleAndDescription(String text);
}
