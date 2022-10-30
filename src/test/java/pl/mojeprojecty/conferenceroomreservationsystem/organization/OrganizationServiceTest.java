package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationDto;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationRequest;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationUpdateRequest;

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
    @Captor
    ArgumentCaptor<Sort> sortArgumentCaptor;


    @Test
    void shouldCallMethodeFindAllFromRepository_withoutParams() {
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
    void shouldCallMethodeFindAllFromRepository_withParamsAsc() {
        //given
        String name_aaa = "aaa";
        String name_bbb = "bbb";
        SortType sortType = SortType.ASC;
        when(organizationRepositoryMock.findAll(sortType.getSort("name")))
                .thenReturn(List.of(
                        new OrganizationEntity(name_aaa),
                        new OrganizationEntity(name_bbb)));

        //when
        List<OrganizationDto> result = organizationService.getListOfOrganization(sortType);

        //then
        assertThat(result).hasSize(2);
        verify(organizationRepositoryMock, times(1)).findAll(sortArgumentCaptor.capture());
        assertThat(sortArgumentCaptor.getValue()).isEqualTo(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void shouldCallMethodeFindAllFromRepository_withParamsDesc() {
        //given
        String name_aaa = "aaa";
        String name_bbb = "bbb";
        SortType sortType = SortType.DESC;
        when(organizationRepositoryMock.findAll(sortType.getSort("name")))
                .thenReturn(List.of(
                        new OrganizationEntity(name_bbb),
                        new OrganizationEntity(name_aaa)));

        //when
        List<OrganizationDto> result = organizationService.getListOfOrganization(sortType);

        //then
        assertThat(result).hasSize(2);
        verify(organizationRepositoryMock, times(1)).findAll(sortArgumentCaptor.capture());
        assertThat(sortArgumentCaptor.getValue()).isEqualTo(Sort.by(Sort.Direction.DESC, "name"));
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
        String name = "Name";
        OrganizationEntity organizationEntity = new OrganizationEntity(name);
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

    @Test
    void shouldCallMethodeUpdateOrganization_organizationExistAndUniqueName(){
        //given
        Long id = 1L;
        String name = "Name";
        String newName = "newName";
        OrganizationEntity organizationEntity = new OrganizationEntity(name);
        organizationEntity.setId(id);
        OrganizationUpdateRequest request = new OrganizationUpdateRequest(id,newName);

        when(organizationRepositoryMock.findById(id)).thenReturn(Optional.of(organizationEntity));
        when(organizationRepositoryMock.existsByName(newName)).thenReturn(false);

        //when
        organizationService.updateOrganization(request);

        //then
        verify(organizationRepositoryMock).save(organizationCaptor.capture());
        assertThat(organizationCaptor.getValue().getId()).isEqualTo(id);
        assertThat(organizationCaptor.getValue().getName()).isEqualTo(newName);
    }

    @Test
    void shouldThrownException_whenOrganizationNotExist(){
        //given
        long id = -1L;
        String newName = "newName";
        OrganizationUpdateRequest request = new OrganizationUpdateRequest(id,newName);

        when(organizationRepositoryMock.findById(id)).thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> organizationService.updateOrganization(request))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrownException_whenOrganizationExistAndNotUniqueName(){
        //given
        Long id = 1L;
        String name = "Name";
        OrganizationEntity organizationEntity = new OrganizationEntity(name);
        organizationEntity.setId(id);
        OrganizationUpdateRequest request = new OrganizationUpdateRequest(id,name);

        when(organizationRepositoryMock.findById(id)).thenReturn(Optional.of(organizationEntity));
        when(organizationRepositoryMock.existsByName(name)).thenReturn(true);


        //when, then
        assertThatThrownBy(() -> organizationService.updateOrganization(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}