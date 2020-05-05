package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.NoteException;
import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.model.Labels;

public interface LabelService {

	public String saveNewLabel(LabelDTO labeldto, String jwt);

	public boolean deleteLabelByUser(Long id, String jwt) throws LabelNotFoundException;

	public boolean renameLabelForUser(String labelname, Long id, String jwt) throws LabelNotFoundException;

	public boolean displayNoteForLabel(Long id, String jwt) throws NoteException;

	public List<Labels> displayAllLabels(String jwt) throws LabelNotFoundException, JWTTokenException;

}
