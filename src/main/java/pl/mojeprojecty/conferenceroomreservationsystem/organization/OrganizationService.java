package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    /**
     * Method to get all organization.
     *
     * @return found {@link List<OrganizationDto>} of all organization
     */
    public List<OrganizationDto> getListOfOrganization() {
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
    public OrganizationDto createOrganization(OrganizationRequest request) {
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
     * @param organizationId - id of organization to update
     * @param request - params of organization to update
     * @return updated {@link OrganizationDto}
     */
    public OrganizationDto updateOrganization(Long organizationId, OrganizationRequest request) {
        validateCorrectRequest(organizationId, request);
        OrganizationEntity organizationEntity = OrganizationMapper.requestToOrganizationEntity(organizationId, request);

        OrganizationEntity updatedOrganizationEntity = organizationRepository.save(organizationEntity);
        log.info("Update organization {}", updatedOrganizationEntity);
        return OrganizationMapper.toOrganizationDto(updatedOrganizationEntity);
    }

    /**
     * Delete the passed organization.
     * If the organization does not exist in DB, then the implementation might throw an {@link EntityNotFoundException}.
     *
     * @param organizationId - id of organization to delete
     */
    public void deleteOrganization(Long organizationId) {
        if (organizationRepository.findById(organizationId).isEmpty()) {
            log.error("Organization does not exist in DB, delete is not permitted!");
            throw new EntityNotFoundException("Organization wit id: " + organizationId + " does not exist in DB, delete is not permitted!");
        }
        log.info("Deleting organization with id {}", organizationId);
        organizationRepository.deleteById(organizationId);
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
