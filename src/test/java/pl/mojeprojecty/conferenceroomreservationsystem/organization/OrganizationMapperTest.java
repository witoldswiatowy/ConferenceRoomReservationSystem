package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class OrganizationMapperTest {

    public static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    void shouldReturnCorrectDtoObject() {
        //given
        OrganizationEntity organization = new OrganizationEntity();
        organization.setName(randomUUID().toString());
        organization.setId(1L);
//        organization.setVersion(1L);
        organization.setCreateDate(NOW);
        organization.setUpdateDate(NOW);

        //when
        OrganizationDto organizationDto = OrganizationMapper.toOrganizationDto(organization);

        //then
        assertThat(organizationDto.getName()).isEqualTo(organization.getName());
        assertThat(organizationDto.getId()).isEqualTo(organization.getId());
    }

}