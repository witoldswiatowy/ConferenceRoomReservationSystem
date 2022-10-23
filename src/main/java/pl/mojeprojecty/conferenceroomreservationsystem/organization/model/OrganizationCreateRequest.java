package pl.mojeprojecty.conferenceroomreservationsystem.organization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCreateRequest {

    private long id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
}
