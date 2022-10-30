package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomEntity;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import java.util.List;

public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoomEntity, Long> {

    boolean existsByIdentifier(String identifier);

    List<ConferenceRoomEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByNameAndOrganization_Id(String name, Long organizationId);

    List<ConferenceRoomEntity> findByNameAndOrganization_Id(String name, Long organizationId);

}
