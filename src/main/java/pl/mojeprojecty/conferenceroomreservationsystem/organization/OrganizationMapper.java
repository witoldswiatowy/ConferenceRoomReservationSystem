package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import lombok.NoArgsConstructor;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationCreateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

@NoArgsConstructor
public class OrganizationMapper {

    public static OrganizationDto toOrganizationDto (OrganizationEntity organizationEntity){
        if (organizationEntity == null){
            return null;
        }

        return OrganizationDto.builder()
                .id(organizationEntity.getId())
//                .version(organizationEntity.getVersion())
                .createDate(organizationEntity.getCreateDate())
                .updateDate(organizationEntity.getUpdateDate())
                .name(organizationEntity.getName())
                .build();
    }

    public static OrganizationEntity toOrganizationEntity (OrganizationDto organizationDto){
        if (organizationDto == null){
            return null;
        }

        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(organizationDto.getId());
//        organizationEntity.setVersion(organizationDto.getVersion());
        organizationEntity.setCreateDate(organizationDto.getCreateDate());
        organizationEntity.setUpdateDate(organizationDto.getUpdateDate());
        organizationEntity.setName(organizationDto.getName());

        return organizationEntity;
    }

    public static OrganizationEntity requestToOrganizationEntity(OrganizationRequest request) {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(request.getName());
        return organizationEntity;
    }

    public static OrganizationEntity createRequestToOrganizationEntity(OrganizationCreateRequest request) {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(request.getId());
        organizationEntity.setName(request.getName());
        return organizationEntity;
    }

    public static OrganizationEntity requestToOrganizationEntity(Long organizationId, OrganizationRequest request) {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(organizationId);
        organizationEntity.setName(request.getName());
        return organizationEntity;
    }
}
