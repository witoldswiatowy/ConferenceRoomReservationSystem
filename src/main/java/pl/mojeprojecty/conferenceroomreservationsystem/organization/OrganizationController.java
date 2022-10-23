package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationCreateRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public List<OrganizationDto> getListOfOrganization(@RequestParam (required = false) SortType sortType){
        log.info("Methode getListOfOrganization was called");
        return organizationService.getListOfOrganization(sortType);
    }

    @GetMapping("/{flag}") //todo zmienić to RequestParam
    public List<OrganizationDto> getSortingListOfOrganization(@PathVariable boolean flag){
        log.info("Methode getListOfOrganization was called");
        return organizationService.getSortingListOfOrganization(flag);
    }

    @GetMapping("/{name}")
    public List<OrganizationDto> getOrganizationByName(@RequestParam String name){
        log.info("Methode getListOfOrganization was called");
        return organizationService.getOrganizationByName(name);
    }

    @PostMapping
    public OrganizationDto createOrganization(@Valid @RequestBody OrganizationRequest request) {
        log.info("Methode createOrganization was called");
        return organizationService.createOrganization(request);
    }

    @PutMapping()
    public OrganizationDto updateOrganization(
            @RequestBody OrganizationCreateRequest request) {
        log.info("Methode updateOrganization was called");
        return organizationService.updateOrganization(request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganization(@PathVariable(name = "id") Long organizationId) {
        log.info("Methode updateOrganization was called");
        organizationService.deleteOrganization(organizationId);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
