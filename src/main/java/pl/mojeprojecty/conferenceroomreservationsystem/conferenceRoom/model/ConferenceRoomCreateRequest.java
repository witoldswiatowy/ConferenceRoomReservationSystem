package pl.mojeprojecty.conferenceroomreservationsystem.conferenceRoom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceRoomCreateRequest {

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
    @NotBlank
    @Size(min = 2, max = 20)
    private String identifier;
    @Min(0)
    @Max(10)
    private Integer floor;
    private Boolean availability;
    private Integer numberOfSeats;
    private Integer numberOfHammock;
    private Long organizationId;
}
