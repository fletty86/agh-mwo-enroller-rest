package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {
		Collection<Participant> participants = participantService.getAll();
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") String login) {
	    Participant participant = participantService.findByLogin(login);
	    if (participant == null) { 
	    	return new ResponseEntity(HttpStatus.NOT_FOUND);
	    } 

	    return new ResponseEntity<Participant>(participant, HttpStatus.OK); 
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST) 
	public ResponseEntity<?> registerParticipant(@RequestBody Participant participantData) {
	    Participant participantLogin = participantService.findByLogin(participantData.getLogin());
	    if (participantLogin == null) { 
	    	participantService.add(participantData);
	    	return new ResponseEntity(HttpStatus.CREATED);
	    }
	    else {
	    	return new ResponseEntity("Unable to create. A participant with login " + participantData.getLogin() + " already exist.", HttpStatus.CONFLICT);
	    }
	 }
	
	@RequestMapping(value = "", method = RequestMethod.DELETE) 
	public ResponseEntity<?> deleteParticipant(@RequestBody Participant participantData) {
	    Participant participantLogin = participantService.findByLogin(participantData.getLogin());
	    if (participantLogin == null) {
	    	return new ResponseEntity("Unable to delete. A participant with login " + participantData.getLogin() + " doesn't exist.", HttpStatus.CONFLICT);
	    }
	    else {
	    	participantService.delete(participantLogin);
		    return new ResponseEntity(HttpStatus.OK);
	    }
	 }

}
