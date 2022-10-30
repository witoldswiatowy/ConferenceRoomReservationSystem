package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomEntity;

import java.util.List;

public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoomEntity, Long> {

    boolean existsByIdentifier(String identifier);

    List<ConferenceRoomEntity> findByNameContainingIgnoreCase(String name);

}
