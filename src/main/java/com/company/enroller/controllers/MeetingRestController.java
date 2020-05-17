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


import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/meetings")

public class MeetingRestController {
	@Autowired
	MeetingService meetingService;
	ParticipantService participantService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findById(id);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerMeeting(@RequestBody Meeting meetingData) {
	    Meeting meeting = meetingService.findById(meetingData.getId());
	    if (meeting == null) { 
	    	meetingService.add(meetingData);
	    	return new ResponseEntity(HttpStatus.CREATED);
	    }
	    else {
	    	return new ResponseEntity("Unable to create. A meeting with id " + meetingData.getId() + " already exist.", HttpStatus.CONFLICT);
	    }
	} 

	@RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetingParticipants(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findById(id);
	    if (meeting == null) { 
	    	return new ResponseEntity("Unable to list participants. A meeting with id " + (id) + " doesn't exist.", HttpStatus.CONFLICT);
	    }
	    else {
			Collection<Participant> participants = meeting.getParticipants();
			return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	    }
	}
	

}

