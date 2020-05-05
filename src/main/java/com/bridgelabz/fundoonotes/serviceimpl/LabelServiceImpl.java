package com.bridgelabz.fundoonotes.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.NoteException;
import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.Users;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class LabelServiceImpl implements LabelService {

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private Utility utility;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ElasticSearchService elasticService;

	@Override
	public String saveNewLabel(LabelDTO labeldto, String jwt) {
		Users user = utility.getUser(jwt);
		if (user == null)
			return "Invalid User";
		else if (labelRepository.getLabelByName(labeldto.getLabelname(), user.getId()) == null) {
			Labels label = new Labels(labeldto.getLabelname(), user);
			labelRepository.save(label);
			return "success";
		} else
			return "existed";
	}

	@Override
	public boolean deleteLabelByUser(Long id, String jwt) throws LabelNotFoundException {
		Users user = utility.getUser(jwt);
		if (utility.validateToken(jwt)) {
			int i = labelRepository.deleteLabelInUser(id, user.getId());
			if (i != 0) {
				List<Notes> notes = noteRepository.getNotesByUser(user.getId());
				for (Notes n : notes) {
					Notes note1 = noteRepository.getNoteByNoteId(n.getId(), user.getId());
					try {
						elasticService.updateNote(note1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return true;
			} else
				return false;
		} else
			throw new LabelNotFoundException("Label Not Found Exception");
	}

	@Override
	public boolean renameLabelForUser(String labelname, Long id, String jwt) throws LabelNotFoundException {
		Users user = utility.getUser(jwt);
		if (utility.validateToken(jwt)) {
			int i = labelRepository.renameLabel(labelname, id, user.getId());
			if (i != 0) {
				List<Notes> notes = noteRepository.getNotesByUser(user.getId());
				for (Notes n : notes) {
					Notes note1 = noteRepository.getNoteByNoteId(n.getId(), user.getId());
					try {
						elasticService.updateNote(note1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return true;
			} else
				return false;
		} else
			throw new LabelNotFoundException("Label Not Found Exception");

	}

	@Override
	public boolean displayNoteForLabel(Long id, String jwt) throws NoteException {
		Users user = utility.getUser(jwt);
		if (utility.validateToken(jwt)) {
			Labels label = labelRepository.getLabelByUser(id, user.getId());
			Object[] notes = labelRepository.displayNotes(label.getId());
			System.out.println(notes);
			if (notes != null)
				return true;
			else
				return false;
		} else
			throw new NoteException(HttpStatus.NOT_FOUND, "Note not Found Exception", LocalDateTime.now());
	}

	@Override
	public List<Labels> displayAllLabels(String jwt) throws LabelNotFoundException, JWTTokenException {
		Users user = utility.getUser(jwt);
		if (utility.validateToken(jwt)) {
			List<Labels> list = labelRepository.getLabelNamesByUser(user.getId());
			if (list != null)
				return list;
			else
				throw new LabelNotFoundException("Labels not found for user");
		} else
			throw new JWTTokenException(HttpStatus.FORBIDDEN, "Token not Found Exception", LocalDateTime.now());

	}

}
