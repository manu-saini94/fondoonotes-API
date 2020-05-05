package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.NoteException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
@RequestMapping("/note")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	Utility utility;

	@Autowired
	private ElasticSearchService elasticService;

	@GetMapping("/search")
	public ResponseEntity<Response> searchTitle(@RequestHeader String title) throws IOException {
		List<Notes> data = null;
		try {
			data = elasticService.getNoteByTitleAndDescription(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data != null)
			return ResponseEntity.ok().body(new Response(200, "Notes Searched and found", data));
		else
			return ResponseEntity.ok().body(new Response(200, "Notes Searched and not found", data));
	
	}

	@PostMapping("/create/{jwt}")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO notedto, @PathVariable("jwt") String jwt)
			throws JWTTokenException, UserException {
		Notes note = noteService.saveNewNote(notedto, jwt);
		if (note != null)
			return ResponseEntity.ok().body(new Response(200, "Note Created", note));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in creating note", notedto));
	}

	@PutMapping("/updateAll/{jwt}")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO notedto, @PathVariable("jwt") String jwt)
			throws JWTTokenException, UserException, NoteException {
		Notes note = noteService.updateToNote(notedto, jwt);
		if (note != null)
			return ResponseEntity.ok().body(new Response(200, "Note Updated", note));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Updating note", notedto));
	}

	@GetMapping("/displayAll/{jwt}")
	public ResponseEntity<Response> displayAllNotesForUser(@PathVariable("jwt") String jwt) throws JWTTokenException {
		List<Notes> list = noteService.displayAllNotesByUser(jwt);
		if (list != null)
			return ResponseEntity.ok().body(new Response(200, "Notes Displayed ", list));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Displaying note", list));
	}

	@GetMapping("/displaypinned/{jwt}")
	public ResponseEntity<Response> displayPinnedNotesForUser(@PathVariable("jwt") String jwt)
			throws JWTTokenException {
		List<Notes> list = noteService.displayPinnedNotesByUser(jwt);
		if (list != null)
			return ResponseEntity.ok().body(new Response(200, "Notes Displayed ", list));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Displaying note", list));
	}

	@PutMapping("/delete/{id}")
	public ResponseEntity<Response> deleteNoteById(@PathVariable("id") Long id, @RequestHeader("jwt") String jwt)
			throws JWTTokenException, UserException {
		if (noteService.deleteNote(id, jwt))
			return ResponseEntity.ok().body(new Response(200, "Note Deleted", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Deleting note", id));
	}

	@PutMapping("/update/label/{id}")
	public ResponseEntity<Response> updateNoteById(@PathVariable("id") Long id, @RequestBody NoteDTO updatedto,
			@RequestHeader("jwt") String jwt) throws JWTTokenException, NoteException {
		boolean b = noteService.updateLabelInNote(updatedto, jwt, id);
		if (b == true)
			return ResponseEntity.ok().body(new Response(200, "Label created inside note", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in creating label", id));
	}

	@DeleteMapping("/delete/label/{id}")
	public ResponseEntity<Response> deleteLabelFromNote(@PathVariable("id") Long id, @RequestHeader("id1") Long id1,
			@RequestHeader("jwt") String jwt) throws LabelNotFoundException, JWTTokenException, NoteException {
		if (noteService.deleteLabelInsideNote(id, id1, jwt))
			return ResponseEntity.ok().body(new Response(200, "Label deleted inside note", id1));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in deleting label", id));
	}

	@PutMapping("/update/title&takeanote/{id}")
	public ResponseEntity<Response> updateNoteWithTitleAndTakeanote(@RequestBody NoteDTO notedto,
			@PathVariable("id") Long id, @RequestHeader("jwt") String jwt)
			throws JWTTokenException, NoteException {
		if (noteService.updateTitleAndTakeanote(id, jwt, notedto))
			return ResponseEntity.ok().body(new Response(200, "Note title and description updated", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in updating title and description", id));
	}

	@PutMapping("/update/pin/{id}")
	public ResponseEntity<Response> updateNoteWithPin(@PathVariable("id") Long id, @RequestHeader("jwt") String jwt)
			throws NoteException, JWTTokenException {
		if (noteService.updatePinForNote(id, jwt))
			return ResponseEntity.ok().body(new Response(200, "Note pin updated", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in updating pin", id));
	}

	@PutMapping("/update/archive/{id}")
	public ResponseEntity<Response> updateNoteWithArchieve(@PathVariable("id") Long id, @RequestHeader("jwt") String jwt)
			throws NoteException, JWTTokenException {
		if (noteService.updateArchiveForNote(id, jwt))
			return ResponseEntity.ok().body(new Response(200, "Note Archive updated", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in updating Archive", id));
	}

	@PutMapping("/update/color/{id}")
	public ResponseEntity<Response> updateColorForNote(@PathVariable("id") Long id, @RequestHeader("jwt") String jwt,
			@RequestHeader("color") String color) throws JWTTokenException, NoteException {
		if (noteService.updateColorForNote(jwt, id, color))
			return ResponseEntity.ok().body(new Response(200, "Color updated for note", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in updating color", id));
	}

	@GetMapping("/displaytrash/{jwt}")
	public ResponseEntity<Response> displayTrashedNotesForUser(@PathVariable("jwt") String jwt)
			throws JWTTokenException {
		List<Notes> list = noteService.displayTrashNotesByUser(jwt);
		if (list != null)
			return ResponseEntity.ok().body(new Response(200, "Trash Notes Displayed ", list));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Displaying Trash notes", list));
	}

	@PutMapping("/restore/{jwt}")
	public ResponseEntity<Response> restoreFromTrashForUser(@PathVariable("jwt") String jwt,
			@RequestHeader("id") Long id) throws JWTTokenException {
		if (noteService.restoreNoteFromTrash(jwt, id))
			return ResponseEntity.ok().body(new Response(200, "Notes Restored ", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Restoring Trash notes", id));
	}

	@PutMapping("/deleteforever/{jwt}")
	public ResponseEntity<Response> deleteFromTrashForUser(@PathVariable("jwt") String jwt, @RequestHeader("id") Long id)
			throws JWTTokenException {
		if (noteService.deleteNoteFromTrash(jwt, id))
			return ResponseEntity.ok().body(new Response(200, "Note Deleted from trash", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Deleting Trash notes", id));
	}

	@PutMapping("/emptytrash/{jwt}")
	public ResponseEntity<Response> emptyTrashForUser(@PathVariable("jwt") String jwt) throws JWTTokenException {
		if (noteService.emptyTrashByUser(jwt))
			return ResponseEntity.ok().body(new Response(200, "Trash is empty", null));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Emptying Trash", null));
	}

	@GetMapping("/sortbyname/{jwt}")
	public ResponseEntity<Response> displaySortedNotesByName(@PathVariable("jwt") String jwt) throws JWTTokenException {
		Object[] notes = noteService.displaySortedByName(jwt);
		if (notes != null)
			return ResponseEntity.ok().body(new Response(200, "Sorted Notes By Name Displayed ", notes));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Displaying Sorted notes", notes));
	}

	@GetMapping("/sortbyid/{jwt}")
	public ResponseEntity<Response> displaySortedNotesById(@PathVariable("jwt") String jwt) throws JWTTokenException {
		Object[] notes = noteService.displaySortedById(jwt);
		if (notes != null)
			return ResponseEntity.ok().body(new Response(200, "Sorted Notes By Id Displayed ", notes));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Displaying Sorted notes", notes));
	}

	@GetMapping("/sortbydate/{jwt}")
	public ResponseEntity<Response> displaySortedNotesByDate(@PathVariable("jwt") String jwt) throws JWTTokenException {
		Object[] notes = noteService.displaySortedByDate(jwt);
		if (notes != null)
			return ResponseEntity.ok().body(new Response(200, "Sorted Notes By Date Displayed ", notes));
		else
			return ResponseEntity.badRequest().body(new Response(400, "problem in Displaying Sorted notes", notes));
	}

}
