package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.Exceptions.CollaboratorNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.EmailAlreadyExistException;
import com.bridgelabz.fundoonotes.Exceptions.NoteException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;

public interface CollaboratorService {

	public boolean addCollaborator(CollaboratorDTO collaboratordto, String jwt)
			throws EmailAlreadyExistException, UserException;

	public boolean deleteCollaborator(CollaboratorDTO collaboratordto, String jwt) throws CollaboratorNotFoundException;

	public List getCollaboratorByNoteId(Long id) throws NoteException, CollaboratorNotFoundException;

}
