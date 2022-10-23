package pl.mojeprojecty.conferenceroomreservationsystem.organization.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class OrganizationDto {

    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
//    private Long version;
    private String name;
}
