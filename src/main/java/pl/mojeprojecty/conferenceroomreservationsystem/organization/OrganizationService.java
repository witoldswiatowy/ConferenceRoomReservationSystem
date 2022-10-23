package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationCreateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
class OrganizationService {

    private final OrganizationRepository organizationRepository;

    /**
     * Method to get all organization.
     *
     * @return found {@link List<OrganizationDto>} of all organization
     */
    List<OrganizationDto> getListOfOrganization() {
        return organizationRepository.findAll().stream()
                .map(OrganizationMapper::toOrganizationDto)
                .collect(Collectors.toList());
    }

    /**
     * Persists the passed organization.
     * If the organization has already DB ID assigned, then the implementation might throw an {@link IllegalArgumentException}.
     *
     * @param request  - params of organization to create
     * @return created {@link OrganizationDto}
     */
    OrganizationDto createOrganization(OrganizationRequest request) {
        if (organizationRepository.existsByName(request.getName())){
            throw new IllegalArgumentException("Organization already exists!");
        }
//        organizationRepository.findByName(request.getName())
//                        .ifPresent(o -> {
//                            throw new IllegalArgumentException("Organization already exists!");
//                        });
        validateCorrectRequest(request);
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
    OrganizationDto updateOrganization(OrganizationCreateRequest request) {
        OrganizationEntity organizationEntity = OrganizationMapper.createRequestToOrganizationEntity(request);

        OrganizationEntity updatedOrganizationEntity = organizationRepository.save(organizationEntity);
        log.info("Update organization {}", updatedOrganizationEntity);
        return OrganizationMapper.toOrganizationDto(updatedOrganizationEntity);
    }

    /**
     * Delete the passed organization.
     * If the organization does not exist in DB, then the implementation might throw an {@link EntityNotFoundException}.
     *
     * @param organizationId - id of organization to delete
     * return deleted {@link OrganizationDto}
     */
    OrganizationDto deleteOrganization(Long organizationId) {
        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Organization wit id: " + organizationId + " does not exist in DB, delete is not permitted!"));
        log.info("Deleting organization with id {}", organizationId);
        organizationRepository.deleteById(organizationId);
        return OrganizationMapper.toOrganizationDto(organization);
    }

    private void validateCorrectRequest(OrganizationRequest request) {
        if (request == null){
            log.error("Request what you want to use is null!");
            throw new IllegalArgumentException();
        }
        if (request.getName() != null){
            if (request.getName().isEmpty()){
                log.error("Name of organization can not be empty");
                throw new IllegalArgumentException();
            } else if(request.getName().isBlank()){
                log.error("Name of organization can not be blank");
                throw new IllegalArgumentException();
            }
        }
    }

    private void validateCorrectRequest(Long organizationId, OrganizationRequest request) {
        if(organizationRepository.findById(organizationId).isEmpty()){
            throw new EntityNotFoundException("Organization wit id: " + organizationId + " does not exist in DB, delete is not permitted!");
        }
        if (request == null){
            log.error("Request what you want to use is null!");
            throw new IllegalArgumentException();
        }
        if (request.getName() != null){
            if (request.getName().isEmpty()){
                log.error("Name of organization can not be empty");
                throw new IllegalArgumentException();
            } else if(request.getName().isBlank()){
                log.error("Name of organization can not be blank");
                throw new IllegalArgumentException();
            }
        }
    }
}
