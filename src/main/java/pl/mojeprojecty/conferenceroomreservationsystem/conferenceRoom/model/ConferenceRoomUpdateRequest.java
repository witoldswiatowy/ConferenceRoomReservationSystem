package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceRoomUpdateRequest {

    private long conferenceRoomId;

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
    @NotBlank
    @Size(min = 2, max = 20)
    private String identifier;
    @Min(0)
    @Max(10)
    private int floor;
    private boolean availability;
    private int numberOfSeats;
    private int numberOfHammock;
    private Long organizationId;
}
