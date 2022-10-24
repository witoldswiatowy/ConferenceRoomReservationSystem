package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationUpdateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
class OrganizationService {

    private final OrganizationRepository organizationRepository;

    /**
     * Method to get all organization with ordering when params is not Null.
     *
     * @param sortType - type of order
     * @return found {@link List<OrganizationDto>} of all organization
     */
    List<OrganizationDto> getListOfOrganization(SortType sortType) {
        if (sortType != null) {
            return organizationRepository.findAll(sortType.getSort("name")).stream()
                    .map(OrganizationMapper::toOrganizationDto)
                    .collect(Collectors.toList());
        } else {
            return organizationRepository.findAll().stream()
                    .map(OrganizationMapper::toOrganizationDto)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Methode to get all organization with comparing name's fragment
     *
     * @param name - searching name's fragment
     * @return found {@link List<OrganizationDto>} of all matching organization
     */
    List<OrganizationDto> getOrganizationByName(String name){
        return organizationRepository.findByNameContainingIgnoreCase(name).stream()
                .map(OrganizationMapper::toOrganizationDto)
                .toList();
    }


    /**
     * Persists the passed organization.
     * If the organization has already DB ID assigned, then the implementation might throw an {@link IllegalArgumentException}.
     *
     * @param request - params of organization to create
     * @return created {@link OrganizationDto}
     */
    OrganizationDto createOrganization(OrganizationRequest request) {
        if (organizationRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Organization already exists!");
        }
//        organizationRepository.findByName(request.getName())
//                        .ifPresent(o -> {
//                            throw new IllegalArgumentException("Organization already exists!");
//                        });
        OrganizationEntity organizationEntity = OrganizationMapper.requestToOrganizationEntity(request);

        OrganizationEntity savedOrganizationEntity = organizationRepository.save(organizationEntity);
        log.info("Create organization {}", savedOrganizationEntity);
        return OrganizationMapper.toOrganizationDto(savedOrganizationEntity);
    }

    /**
     * Update the passed organization.
     * If the organization does not exist in DB, then the implementation might throw an {@link IllegalArgumentException}.
     *
     * @param request - id and params of organization to update
     * @return updated {@link OrganizationDto}
     */
    OrganizationDto updateOrganization(OrganizationUpdateRequest request) {
        OrganizationEntity organizationEntity = organizationRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("No organization to update found!"));
        organizationEntity.setName(request.getName());
        OrganizationEntity updatedOrganizationEntity = organizationRepository.save(organizationEntity);
        log.info("Update organization {}", updatedOrganizationEntity);
        return OrganizationMapper.toOrganizationDto(updatedOrganizationEntity);
    }

    /**
     * Delete the passed organization.
     * If the organization does not exist in DB, then the implementation might throw an {@link EntityNotFoundException}.
     *
     * @param organizationId - id of organization to delete
     *                       return deleted {@link OrganizationDto}
     */
    OrganizationDto deleteOrganization(Long organizationId) {
        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Organization wit id: " + organizationId + " does not exist in DB, delete is not permitted!"));
        log.info("Deleting organization with id {}", organizationId);
        organizationRepository.deleteById(organizationId);
        return OrganizationMapper.toOrganizationDto(organization);
    }
}
