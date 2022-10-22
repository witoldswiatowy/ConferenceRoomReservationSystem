package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public List<OrganizationDto> getListOfOrganization (){
        log.info("Methode getListOfOrganization was called");
        return organizationService.getListOfOrganization();
    }

    @PostMapping
    public OrganizationDto createOrganization(@RequestBody OrganizationRequest request) {
        log.info("Methode createOrganization was called");
        return organizationService.createOrganization(request);
    }

    @PutMapping("/{id}")
    public OrganizationDto updateOrganization(
            @RequestBody OrganizationRequest request,
            @PathVariable(name = "id") Long organizationId) {
        log.info("Methode updateOrganization was called");
        return organizationService.updateOrganization(organizationId, request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganization(@PathVariable(name = "id") Long organizationId) {
        log.info("Methode updateOrganization was called");
        organizationService.deleteOrganization(organizationId);
    }
}
