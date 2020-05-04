package com.slimsimapps.avotingapp.participant;


import com.slimsimapps.avotingapp.MainController;
import com.slimsimapps.avotingapp.meeting.Meeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class ParticipantController {


    Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ParticipantService participantService;

    @GetMapping("/meeting/{meetingId}/participants")
    public List<Participant> getAllParticipants( @PathVariable int meetingId){
        List<Participant> x = participantService.getAllParticipants( meetingId );

        List<Participant> HandRaisedParticipantList = new ArrayList<>();
        List<Participant> RequestToSpeakParticipantList = new ArrayList<>();
        List<Participant> CommentParticipantList = new ArrayList<>();
        List<Participant> InformationParticipantList = new ArrayList<>();
        List<Participant> BreakingQuestionParticipantList = new ArrayList<>();
        List<Participant> silentParticipantList = new ArrayList<>();

        List<Participant> sortedParticipantList = new ArrayList<>();

        x.forEach( p -> {
            if( p.isBreakingQuestion() ) { BreakingQuestionParticipantList.add( p ); }
            else if( p.isInformation() ) { InformationParticipantList.add( p ); }
            else if( p.isComment() ) { CommentParticipantList.add( p ); }
            else if( p.isRequestToSpeak() ) { RequestToSpeakParticipantList.add( p ); }
            else if( p.isHandRaised() ) { HandRaisedParticipantList.add( p ); }

            else { silentParticipantList.add( p ); }
        });


        HandRaisedParticipantList.sort( Comparator.comparing(Participant::getHandRaisedTime));
        RequestToSpeakParticipantList.sort( Comparator.comparing(Participant::getRequestToSpeakTime));
        CommentParticipantList.sort( Comparator.comparing(Participant::getCommentTime));
        InformationParticipantList.sort( Comparator.comparing(Participant::getInformationTime));
        BreakingQuestionParticipantList.sort( Comparator.comparing(Participant::getBreakingQuestionTime));
        //silentParticipantList.sort( Comparator.comparing( p -> p.getsilentTime()));

        sortedParticipantList.addAll(BreakingQuestionParticipantList);
        sortedParticipantList.addAll(InformationParticipantList);
        sortedParticipantList.addAll(CommentParticipantList);
        sortedParticipantList.addAll(RequestToSpeakParticipantList);
        sortedParticipantList.addAll(HandRaisedParticipantList);
        sortedParticipantList.addAll(silentParticipantList);

        /*
        x.sort( Comparator.comparing(p -> !p.isHandRaised()));
        x.sort( Comparator.comparing(p -> p.getHandRaisedTime()));
        x.sort( Comparator.comparing(p -> !p.isRequestToSpeak()));
        x.sort( Comparator.comparing(p -> p.getRequestToSpeakTime()));
        x.sort( Comparator.comparing(p -> !p.isComment()));
        x.sort( Comparator.comparing(p -> { return p.getCommentTime(); }));
        x.sort( Comparator.comparing(p -> !p.isInformation()));
        x.sort( Comparator.comparing(p -> p.getInformationTime()));
        x.sort( Comparator.comparing(p -> !p.isBreakingQuestion()));
        x.sort( Comparator.comparing(p -> p.getBreakingQuestionTime()));
        */
        return sortedParticipantList;
    }

    @GetMapping("/meeting/{meetingId}/participants/{id}")
    public Participant getParticipant( @PathVariable int meetingId, @PathVariable int id ) throws Exception {
        log.info( "getParticipant -> id = " + id);
        return participantService.getParticipant( id ); //TODO: lägg till meetingId här, för en extra koll :)
    }

    @PostMapping("/meeting/{meetingId}/participants")
    public Participant addParticipant( @PathVariable int meetingId, @RequestBody Participant participant ) throws Exception {
        System.out.println("addParticipant ->");
        return participantService.addParticipant(participant, meetingId);
    }

    @PutMapping( "/meeting/{meetingId}/participants/{id}" )
    public Participant updateParticipant( @PathVariable int meetingId, @PathVariable int id, @RequestBody Participant participant ) throws Exception {
        log.info( "updateParticipant -> id = " + id);
        log.info( "updateParticipant -> participant = " + participant);
        //participant.setMeeting( new Meeting( meetingId ) );

        return participantService.updateParticipant( id, meetingId, participant );
    }

    @PostMapping( "/meeting/{meetingId}/participants/{id}" )
    public Participant setParticipant( @PathVariable int meetingId, @PathVariable int id, @RequestBody Participant participant ) throws Exception {
        log.info( "setParticipant -> id = " + id);
        return participantService.updateParticipant( id, meetingId, participant );
    }

    @DeleteMapping( "/meeting/{meetingId}/participants/{id}" )
    public void deleteParticipant( @PathVariable int meetingId, @PathVariable int id ) throws Exception {
        participantService.deleteParticipant( id ); //TODO: lägg till meetingId här, för en extra koll :)
    }

/*
    @PostMapping("/participants2")
    public String createMe2(Participant participant ) {
        System.out.println("createMe ->");
        log.trace("createMe2 -> trace");
        log.info("createMe2 -> info");
        log.debug("createMe2 -> debug");
        log.warn("createMe2 -> warn");
        System.out.println("createMe2");
        Participant p2 = new Participant();
        try {
            p2 = participantService.addParticipant(participant);
        } catch ( Exception e ) {
            log.error("createMe could not add participant: " + e.getMessage() + " " + e.getCause());
        }

        //model.addAttribute("participant", p2 );

        return "me";
    }
    */

}
