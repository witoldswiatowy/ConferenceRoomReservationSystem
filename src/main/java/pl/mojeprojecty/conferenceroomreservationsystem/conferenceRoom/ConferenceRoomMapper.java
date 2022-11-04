package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom;

import lombok.NoArgsConstructor;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomCreateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomDto;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomEntity;
import pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model.ConferenceRoomUpdateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.OrganizationMapper;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

@NoArgsConstructor
public class ConferenceRoomMapper {

    public static ConferenceRoomDto toConferenceRoomDto (ConferenceRoomEntity conferenceRoomEntity){
        if (conferenceRoomEntity == null){
            return null;
        }

        OrganizationDto organizationDto = OrganizationMapper.toOrganizationDto(conferenceRoomEntity.getOrganization());

        return ConferenceRoomDto.builder()
                .id(conferenceRoomEntity.getId())
                .createDate(conferenceRoomEntity.getCreateDate())
                .updateDate(conferenceRoomEntity.getUpdateDate())
                .name(conferenceRoomEntity.getName())
                .identifier(conferenceRoomEntity.getIdentifier())
                .floor(conferenceRoomEntity.getFloor())
                .availability(conferenceRoomEntity.isAvailability())
                .numberOfSeats(conferenceRoomEntity.getNumberOfSeats())
                .numberOfHammock(conferenceRoomEntity.getNumberOfHammock())
                .organization(organizationDto)
                .build();
    }

    public static ConferenceRoomEntity toConferenceRoomEntity (ConferenceRoomDto conferenceRoomDto){
        if (conferenceRoomDto == null){
            return null;
        }

        OrganizationEntity organizationEntity = OrganizationMapper.toOrganizationEntity(conferenceRoomDto.getOrganization());

        ConferenceRoomEntity conferenceRoomEntity = new ConferenceRoomEntity();
        conferenceRoomEntity.setId(conferenceRoomDto.getId());
        conferenceRoomEntity.setCreateDate(conferenceRoomDto.getCreateDate());
        conferenceRoomEntity.setUpdateDate(conferenceRoomDto.getUpdateDate());
        conferenceRoomEntity.setName(conferenceRoomDto.getName());
        conferenceRoomEntity.setIdentifier(conferenceRoomDto.getIdentifier());
        conferenceRoomEntity.setFloor(conferenceRoomDto.getFloor());
        conferenceRoomEntity.setAvailability(conferenceRoomDto.isAvailability());
        conferenceRoomEntity.setNumberOfSeats(conferenceRoomDto.getNumberOfSeats());
        conferenceRoomEntity.setNumberOfHammock(conferenceRoomDto.getNumberOfHammock());
        conferenceRoomEntity.setOrganization(organizationEntity);

        return conferenceRoomEntity;
    }

    public static ConferenceRoomEntity createRequestToConferenceRoomEntity(ConferenceRoomCreateRequest request) {
        ConferenceRoomEntity conferenceRoomEntity = new ConferenceRoomEntity();
        conferenceRoomEntity.setName(request.getName());
        conferenceRoomEntity.setIdentifier(request.getIdentifier());
        conferenceRoomEntity.setFloor(request.getFloor());
        conferenceRoomEntity.setAvailability(request.getAvailability());
        conferenceRoomEntity.setNumberOfSeats(request.getNumberOfSeats());
        conferenceRoomEntity.setNumberOfHammock(request.getNumberOfHammock());
        return conferenceRoomEntity;
    }

    public static ConferenceRoomEntity updateRequestToConferenceRoomEntity(ConferenceRoomUpdateRequest request) {
        ConferenceRoomEntity conferenceRoomEntity = new ConferenceRoomEntity();
        conferenceRoomEntity.setId(request.getConferenceRoomId());
        conferenceRoomEntity.setName(request.getName());
        conferenceRoomEntity.setIdentifier(request.getIdentifier());
        conferenceRoomEntity.setFloor(request.getFloor());
        conferenceRoomEntity.setAvailability(request.getAvailability());
        conferenceRoomEntity.setNumberOfSeats(request.getNumberOfSeats());
        conferenceRoomEntity.setNumberOfHammock(request.getNumberOfHammock());
        return conferenceRoomEntity;
    }
}
