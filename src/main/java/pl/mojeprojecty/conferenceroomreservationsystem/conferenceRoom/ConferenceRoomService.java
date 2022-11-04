package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomCreateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomDto;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomEntity;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomUpdateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.OrganizationMapper;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.OrganizationRepository;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.SortType;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConferenceRoomService {

    private final ConferenceRoomRepository conferenceRoomRepository;
    private final OrganizationRepository organizationRepository;

    List<ConferenceRoomDto> getList(SortType sortType) {
        if (sortType != null) {
            return conferenceRoomRepository.findAll(sortType.getSort("name")).stream()
                    .map(ConferenceRoomMapper::toConferenceRoomDto)
                    .collect(Collectors.toList());
        } else {
            return conferenceRoomRepository.findAll().stream()
                    .map(ConferenceRoomMapper::toConferenceRoomDto)
                    .collect(Collectors.toList());
        }
    }

    List<ConferenceRoomDto> getByName(String name) {
        return conferenceRoomRepository.findByNameContainingIgnoreCase(name).stream()
                .map(ConferenceRoomMapper::toConferenceRoomDto)
                .toList();
    }

    ConferenceRoomDto create(ConferenceRoomCreateRequest request) {
        if (conferenceRoomRepository.existsByIdentifier(request.getIdentifier())) {
            throw new IllegalArgumentException("Conference Room with this identifier already exists!");
        }

        ConferenceRoomEntity conferenceRoomEntity = ConferenceRoomMapper.createRequestToConferenceRoomEntity(request);

        OrganizationEntity organizationEntity = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new EntityNotFoundException("No organization to found!"));
        conferenceRoomEntity.setOrganization(organizationEntity);

        ConferenceRoomEntity savedConferenceRoomEntity = conferenceRoomRepository.save(conferenceRoomEntity);
        log.info("Create organization {}", savedConferenceRoomEntity);
        return ConferenceRoomMapper.toConferenceRoomDto(savedConferenceRoomEntity);
    }

    ConferenceRoomDto update(ConferenceRoomUpdateRequest request) {
        if (conferenceRoomRepository.existsByIdentifier(request.getIdentifier())) {
            throw new IllegalArgumentException("Conference Room with this identifier already exists!");
        }

        final String updateName = request.getName();
        final String updatedIdentifier = request.getIdentifier();
        final Integer updatedFloor = request.getFloor();
        final Boolean updatedAvailability = request.getAvailability();
        final Integer updatedNumberOfSeats = request.getNumberOfSeats();
        final Integer updatedNumberOfHammock = request.getNumberOfHammock();
        OrganizationEntity managedOrganization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new EntityNotFoundException("No organization to found!"));

        ConferenceRoomEntity conferenceRoomEntity = conferenceRoomRepository.findById(request.getId())
                .map(cr -> {
                    cr.setName(updateName != null ? updateName : cr.getName());
                    cr.setIdentifier(updatedIdentifier != null ? updatedIdentifier : cr.getIdentifier());
                    cr.setFloor(updatedFloor != null ? updatedFloor : cr.getFloor());
                    cr.setAvailability(updatedAvailability != null ? updatedAvailability : cr.isAvailability());
                    cr.setNumberOfSeats(updatedNumberOfSeats != null ? updatedNumberOfSeats : cr.getNumberOfSeats());
                    cr.setNumberOfHammock(updatedNumberOfHammock != null ? updatedNumberOfHammock : cr.getNumberOfHammock());
                    cr.setOrganization(managedOrganization != null ? managedOrganization : cr.getOrganization());
                    return cr;
                })
                .orElseThrow(() -> new EntityNotFoundException("No Conference Room to update found!"));

        ConferenceRoomEntity updatedOrganizationEntity = conferenceRoomRepository.save(conferenceRoomEntity);
        log.info("Update conference room {}", updatedOrganizationEntity);
        return ConferenceRoomMapper.toConferenceRoomDto(updatedOrganizationEntity);
    }

    ConferenceRoomDto delete(Long conferenceRoomId) {
        ConferenceRoomEntity conferenceRoomEntity = conferenceRoomRepository.findById(conferenceRoomId)
                .orElseThrow(() -> new EntityNotFoundException("Conference Room wit id: " + conferenceRoomId + " does not exist in DB, delete is not permitted!"));
        log.info("Deleting conference room with id {}", conferenceRoomId);
        conferenceRoomRepository.deleteById(conferenceRoomId);
        return ConferenceRoomMapper.toConferenceRoomDto(conferenceRoomEntity);
    }

}
