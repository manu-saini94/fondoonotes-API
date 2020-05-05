package com.bridgelabz.fundoonotes.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.CollaboratorNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.EmailAlreadyExistException;
import com.bridgelabz.fundoonotes.Exceptions.NoteException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.Users;
import com.bridgelabz.fundoonotes.repository.CollaboratorRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.CollaboratorService;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {

	@Autowired
	private CollaboratorRepository collaboratorRepository;

	private Utility utility;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ElasticSearchService elasticService;

	@Autowired
	public CollaboratorServiceImpl(CollaboratorRepository collaboratorRepository, Utility utility) {
		this.collaboratorRepository = collaboratorRepository;
		this.utility = utility;
	}

	@Override
	public boolean addCollaborator(CollaboratorDTO collaboratordto, String jwt)
			throws EmailAlreadyExistException, UserException {
		Users user = utility.getUser(jwt);
		if (collaboratorRepository.findByEmail(collaboratordto.getCollaborator()) != null
				&& !user.getEmailId().equals(collaboratordto.getCollaborator())) {
			Notes note = collaboratorRepository.getNotes(collaboratordto.getNoteId());
			if (utility.checkCollaborator(note, collaboratordto.getCollaborator())) {
				Collaborator collaborator = new Collaborator(collaboratordto.getCollaborator(), note);
				collaboratorRepository.save(collaborator);

				Notes note1 = noteRepository.getNoteByNoteId(collaboratordto.getNoteId(), user.getId());
				try {
					elasticService.updateNote(note1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			} else
				return false;
		} else
			throw new UserException("Enter a Valid Email Id");

	}

	@Override
	public boolean deleteCollaborator(CollaboratorDTO collaboratordto, String jwt)
			throws CollaboratorNotFoundException {
		Users user = utility.getUser(jwt);
		if (collaboratorRepository.getCollaborator(collaboratordto.getCollaborator(),
				collaboratordto.getNoteId()) != null) {
			collaboratorRepository.deleteCollaboratorFromNote(collaboratordto.getCollaborator(),
					collaboratordto.getNoteId());
			Notes note1 = noteRepository.getNoteByNoteId(collaboratordto.getNoteId(), user.getId());
			try {
				elasticService.updateNote(note1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else
			throw new CollaboratorNotFoundException("No collaborator found");

	}

	@Override
	public List getCollaboratorByNoteId(Long id) throws NoteException, CollaboratorNotFoundException {
		Notes note = collaboratorRepository.getNotes(id);
		if (note == null)
			throw new NoteException(HttpStatus.NOT_FOUND,"Note not Found Exception",LocalDateTime.now());
		if (note.getCollaborator() != null)
			return note.getCollaborator();
		else
			throw new CollaboratorNotFoundException("No collaborator found");
	}

}
