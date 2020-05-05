package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.Exceptions.CollaboratorNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.EmailAlreadyExistException;
import com.bridgelabz.fundoonotes.Exceptions.NoteException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.CollaboratorService;

@RestController
@RequestMapping("/collab")
public class CollaboratorController {

	@Autowired
	private CollaboratorService collaboratorService;

	@PostMapping("/add")
	public ResponseEntity<Response> addCollaboratorForNote(@RequestBody CollaboratorDTO collaboratordto,
			@RequestHeader("jwt") String jwt) throws EmailAlreadyExistException, UserException {
		if (collaboratorService.addCollaborator(collaboratordto, jwt))
			return ResponseEntity.ok()
					.body(new Response(200, "Collaborator Added to Note", collaboratordto.getNoteId()));
		else
			return ResponseEntity.badRequest()
					.body(new Response(400, "Already collaborated", collaboratordto.getNoteId()));
	}

	@PostMapping("/delete")
	public ResponseEntity<Response> deleteCollaboratorForNote(@RequestBody CollaboratorDTO collaboratordto,
			@RequestHeader("jwt") String jwt) throws CollaboratorNotFoundException {
		if (collaboratorService.deleteCollaborator(collaboratordto, jwt))
			return ResponseEntity.ok().body(new Response(200, "Collaborator deleted For Note", collaboratordto));
		else
			return ResponseEntity.badRequest()
					.body(new Response(400, "Problem in deleting Collaborator", collaboratordto.getNoteId()));
	}

	@GetMapping("/get")
	public ResponseEntity<Response> getCollaboratorForNote(@RequestHeader("id") Long id,
			@RequestHeader("jwt") String jwt) throws NoteException, CollaboratorNotFoundException {
		List collaborators = collaboratorService.getCollaboratorByNoteId(id);
		return ResponseEntity.ok().body(new Response(200, "Collaborators are retrieved", collaborators));
	}

}
