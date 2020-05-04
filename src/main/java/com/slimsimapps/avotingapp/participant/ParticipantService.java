package com.slimsimapps.avotingapp.participant;

import com.slimsimapps.avotingapp.MainController;
import com.slimsimapps.avotingapp.meeting.Meeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;


    Logger log = LoggerFactory.getLogger(MainController.class);
    /*
    List<Participant> participants = new ArrayList<>( Arrays. asList(
            new Participant(1, "Adam", false, false, false),
            new Participant(2, "Berit", true, false, false),
            new Participant(3, "Cesar", false, false, false),
            new Participant(4, "Doris", false, false, true),
            new Participant(5, "Erika", false, true, false),
            new Participant(6, "Filippa", false, false, true)
    ) );
    */



    public List<Participant> getAllParticipants( int meetingId ) {
        /*
        List<Participant> participants = new ArrayList<>();
        Iterable<Participant> meetingIterable = participantRepository.findByMeetingId( meetingId );
        meetingIterable.forEach(participants::add);
        return participants;
        */
        return participantRepository.findByMeetingId( meetingId );
    }

    public Participant getParticipant(int id) throws Exception {
        //return participants.stream().filter( participant -> participant.getId() == id ).findFirst().orElseThrow();
        return participantRepository.findById(id).orElseThrow(
                () -> new Exception("No Participant found with id " + id) );
    }

    public Participant addParticipant(Participant participant, int meetingId) throws Exception {
        log.info("createMe: participant = " + participant);
        if( participant == null ) {
            throw new Exception("No body found, participant is null");
        }
        participant.setMeeting(new Meeting( meetingId ) );
        return participantRepository.save(participant);
    }

    public Participant updateParticipant(int participantId, int meetingId, Participant updatedParticipantData) throws Exception {
        if( !participantRepository.existsById(participantId) ) {
            throw new Exception("No Participant found with id " + participantId);
        }
        updatedParticipantData.setMeeting( new Meeting( meetingId ) );
        return participantRepository.save( updatedParticipantData );
    }

    public void deleteParticipant(int id) throws Exception {
        if( !participantRepository.existsById(id) ) {
            throw new Exception("No Participant found with id " + id);
        }
        participantRepository.deleteById( id );
    }
}
