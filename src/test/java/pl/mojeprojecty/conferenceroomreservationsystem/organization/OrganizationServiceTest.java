package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationRequest;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrganizationServiceTest {

    @InjectMocks
    OrganizationService organizationService;

    @Mock
    OrganizationRepository organizationRepositoryMock;

    @Captor
    ArgumentCaptor<OrganizationEntity> organizationCaptor;


    @Test
    void shouldCallMethodeFindAllFromRepository() {
        //given
        String name = "Name";

        when(organizationRepositoryMock.findAll()).thenReturn(List.of(new OrganizationEntity(name)));

        //when
        List<OrganizationDto> result = organizationService.getListOfOrganization(null);

        //then
        assertThat(result).hasSize(1);
        verify(organizationRepositoryMock, times(1)).findAll();
    }

    @Test
    void shouldCallMethodeFindByNameContainingIgnoreCaseFromRepo() {
        //given
        String name = "Name";

        when(organizationRepositoryMock.findByNameContainingIgnoreCase(name))
                .thenReturn(List.of(
                        new OrganizationEntity(name),
                        new OrganizationEntity(name + "a")
                ));

        //when
        List<OrganizationDto> result = organizationService.getOrganizationByName(name);

        //then
        assertThat(result).hasSize(2);
        verify(organizationRepositoryMock, times(1)).findByNameContainingIgnoreCase(name);
    }

    @Test
    void shouldCallMethodeSaveFromRepo() {
        //given
        String name = "Name";
        OrganizationRequest request = new OrganizationRequest(name);

        when(organizationRepositoryMock.existsByName(name)).thenReturn(false);

        //when
        organizationService.createOrganization(request);

        //then
        verify(organizationRepositoryMock, times(1)).existsByName(name);
        verify(organizationRepositoryMock, times(1)).save(organizationCaptor.capture());
        assertThat(organizationCaptor.getValue().getName()).isEqualTo(request.getName());
    }

    @Test
    void shouldThrowException_whenNameOfNewOrganizationAlreadyExist() {
        //given
        String name = "Name";
        OrganizationRequest request = new OrganizationRequest(name);

        when(organizationRepositoryMock.existsByName(any())).thenReturn(true);

        //when, then
        assertThatThrownBy(() -> organizationService.createOrganization(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldCallMethodeDeleteFromRepository() {
        //given
        Long id = 1L;
        OrganizationEntity organizationEntity = new OrganizationEntity("Name");
        organizationEntity.setId(id);

        when(organizationRepositoryMock.findById(id)).thenReturn(Optional.of(organizationEntity));

        //when
        organizationService.deleteOrganization(id);

        //then
        verify(organizationRepositoryMock, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowException_whenOrganizationWithThisIdNotExist() {
        //given
        Long id = 1L;

        when(organizationRepositoryMock.findById(id)).thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> organizationService.deleteOrganization(id))
                .isInstanceOf(EntityNotFoundException.class);
    }
}