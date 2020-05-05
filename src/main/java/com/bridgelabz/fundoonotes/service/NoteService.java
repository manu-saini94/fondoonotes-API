package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.NoteException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Notes;

public interface NoteService {

	public Notes saveNewNote(NoteDTO notedto, String jwt) throws JWTTokenException, UserException;

	public boolean deleteNote(Long id, String jwt) throws UserException;

	public boolean updateLabelInNote(NoteDTO notedto, String jwt, Long id)
			throws JWTTokenException, NoteException;

	public boolean deleteLabelInsideNote(Long id, Long id1, String jwt) throws JWTTokenException, NoteException;

	public boolean updateTitleAndTakeanote(Long id, String jwt, NoteDTO notedto)
			throws JWTTokenException, NoteException;

	public boolean updatePinForNote(Long id, String jwt) throws NoteException, JWTTokenException;

	public boolean updateArchiveForNote(Long id, String jwt) throws NoteException, JWTTokenException;

	public List<Notes> displayAllNotesByUser(String jwt) throws JWTTokenException;

	public List<Notes> displayPinnedNotesByUser(String jwt) throws JWTTokenException;

	public boolean updateColorForNote(String jwt, Long id, String color) throws JWTTokenException, NoteException;

	List<Notes> displayTrashNotesByUser(String jwt) throws JWTTokenException;

	public boolean restoreNoteFromTrash(String jwt, Long id) throws JWTTokenException;

	public boolean deleteNoteFromTrash(String jwt, Long id) throws JWTTokenException;

	public boolean emptyTrashByUser(String jwt) throws JWTTokenException;

	public Object[] displaySortedByName(String jwt) throws JWTTokenException;

	public Object[] displaySortedById(String jwt) throws JWTTokenException;

	public Object[] displaySortedByDate(String jwt) throws JWTTokenException;

	public Notes updateToNote(NoteDTO notedto, String jwt)
			throws JWTTokenException, UserException, NoteException;


}
