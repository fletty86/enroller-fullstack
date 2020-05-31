package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/meetings")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findMeetings(@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "sort", defaultValue = "") String sortMode,
			@RequestParam(value = "participantLogin", defaultValue = "") String participantLogin) {

		Participant foundParticipant = null;
		if (!participantLogin.isEmpty()) {
			foundParticipant = participantService.findByLogin(participantLogin);
			if (foundParticipant == null) {
	            return new ResponseEntity("Cannot find participant with ID = " + participantLogin, HttpStatus.NOT_FOUND);
	        }
		}
		
		Collection<Meeting> meetings = meetingService.findMeetings(title, description, foundParticipant, sortMode);
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}
}