package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model;

import lombok.*;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ConferenceRoomDto {

    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private String name;
    private String identifier;
    private int floor;
    private boolean availability;
    private int numberOfSeats;
    private int numberOfHammock;
    private OrganizationDto organization;

}
