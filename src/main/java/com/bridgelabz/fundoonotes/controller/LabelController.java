package com.bridgelabz.fundoonotes.controller;

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
import com.bridgelabz.fundoonotes.Exceptions.LabelExistException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.NoteException;
import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

@RestController
@RequestMapping("/label")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LabelController {

	@Autowired
	private LabelService labelService;

	@PostMapping("edit/create")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDTO labeldto, @RequestHeader String jwt)
			throws LabelExistException {
		String result = labelService.saveNewLabel(labeldto, jwt);
		if (result.equals("success")) {
			return ResponseEntity.ok().body(new Response(200, "Label Created", labeldto));
		} else if (result.equals("Invalid User")) {
			return ResponseEntity.badRequest().body(new Response(400, "Invalid User", labeldto));
		} else if (result.equals("existed")) {
			throw new LabelExistException("Label with this name already Exist");
		} else
			return ResponseEntity.badRequest().body(new Response(400, "Some Problem has occured", labeldto));
	}

	@DeleteMapping("edit/delete/{jwt}")
	public ResponseEntity<Response> deleteLabelForUser(@RequestHeader("id") Long id, @PathVariable("jwt") String jwt)
			throws LabelNotFoundException {
		if (labelService.deleteLabelByUser(id, jwt))
			return ResponseEntity.ok().body(new Response(200, "Label Deleted For User", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "Some Problem has occured", id));
	}

	@PutMapping("edit/rename/{id}")
	public ResponseEntity<Response> editLabelForUser(@RequestBody LabelDTO labeldto, @PathVariable("id") Long id,
			@RequestHeader("jwt") String jwt) throws LabelNotFoundException {
		if (labelService.renameLabelForUser(labeldto.getLabelname(), id, jwt))
			return ResponseEntity.ok().body(new Response(200, "Label name For User edited", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "Some Problem has occured", id));
	}

	@GetMapping("display/note/{id}")
	public ResponseEntity<Response> displayNotesByLabel(@PathVariable("id") Long id, @RequestHeader("jwt") String jwt)
			throws NoteException {
		if (labelService.displayNoteForLabel(id, jwt))
			return ResponseEntity.ok().body(new Response(200, "Note For Label displayed", id));
		else
			return ResponseEntity.badRequest().body(new Response(400, "Some Problem has occured", id));
	}

	@GetMapping("displaylabels/{jwt}")
	public ResponseEntity<Response> displayAllLabelsForUser(@PathVariable("jwt") String jwt)
			throws LabelNotFoundException, JWTTokenException {
		List<Labels> list = labelService.displayAllLabels(jwt);
		if (list != null)
			return ResponseEntity.ok().body(new Response(200, "Labels for User displayed", list));
		else
			return ResponseEntity.badRequest().body(new Response(400, "Some Problem has occured", list));
	}

}
