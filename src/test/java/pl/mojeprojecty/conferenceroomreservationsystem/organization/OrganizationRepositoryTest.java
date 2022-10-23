package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrganizationRepositoryTest {

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void shouldReturnEmptyList_whenNoElementExistWithThisParams() {
        //given
        String name = "Name";

        //when
        List<OrganizationEntity> result = organizationRepository.findByNameContainingIgnoreCase(name);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnListWithTwoElement_whenTwoElementExist() {
        //given
        String name = "Name";
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(name);
        testEntityManager.persist(organizationEntity);
        String otherName = "OtherName";
        OrganizationEntity organizationEntitySecond = new OrganizationEntity();
        organizationEntitySecond.setName(otherName);
        testEntityManager.persist(organizationEntitySecond);

        //when
        List<OrganizationEntity> result = organizationRepository.findByNameContainingIgnoreCase(null);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnListWithTwoElement_whenTwoElementExistWithThisParam() {
        //given
        String name = "Name";
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(name);
        testEntityManager.persist(organizationEntity);
        String otherName = "OtherName";
        OrganizationEntity organizationEntitySecond = new OrganizationEntity();
        organizationEntitySecond.setName(otherName);
        testEntityManager.persist(organizationEntitySecond);

        //when
        List<OrganizationEntity> result = organizationRepository.findByNameContainingIgnoreCase(name);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnOrderedAscList() {
        //given
        String name = "AName";
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(name);
        testEntityManager.persist(organizationEntity);
        String otherName = "BOtherName";
        OrganizationEntity organizationEntitySecond = new OrganizationEntity();
        organizationEntitySecond.setName(otherName);
        testEntityManager.persist(organizationEntitySecond);
        List<OrganizationEntity> expectedList = List.of(organizationEntity, organizationEntitySecond);

        //when
        List<OrganizationEntity> result = organizationRepository.findAllByOrderByNameAsc();

        //then
        assertThat(result).hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    void shouldReturnOrderedDescList() {
        //given
        String name = "AName";
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(name);
        testEntityManager.persist(organizationEntity);

        String otherName = "BOtherName";
        OrganizationEntity organizationEntitySecond = new OrganizationEntity();
        organizationEntitySecond.setName(otherName);
        testEntityManager.persist(organizationEntitySecond);

        List<OrganizationEntity> expectedList = List.of(organizationEntitySecond, organizationEntity);

        //when
        List<OrganizationEntity> result = organizationRepository.findAllByOrderByNameDesc();

        //then
        assertThat(result)
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    void shouldReturnListWithOneElement_whenOneElementExistWithThisParam_fromTwoExistingObject() {
        //given
        String name = "Name";
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(name);
        testEntityManager.persist(organizationEntity);
        String otherName = "OtherName";
        OrganizationEntity organizationEntitySecond = new OrganizationEntity();
        organizationEntitySecond.setName(otherName);
        testEntityManager.persist(organizationEntitySecond);

        List<OrganizationEntity> expectedList = List.of(organizationEntitySecond);

        //when
        List<OrganizationEntity> result = organizationRepository.findByNameContainingIgnoreCase("other");

        //then
        assertThat(result).hasSize(1)
                .isEqualTo(expectedList);
    }

    @ParameterizedTest
    @ArgumentsSource(FindByNameArgumentProvider.class)
    void given_list_of_organizations_when_find_by_organization_name_then_organization_should_be_returned(
            List<OrganizationEntity> organizationInTheDb,
            String organizationToFind,
            boolean exists
    ) {
        //given
        organizationInTheDb.forEach(o -> {
            testEntityManager.persist(o);
        });

        //when
        List<OrganizationEntity> result = organizationRepository.findByNameContainingIgnoreCase(organizationToFind);

        //then
        assertEquals(exists, !result.isEmpty());
    }

    @ParameterizedTest
    @ArgumentsSource(SortByNameArgumentProvider.class)
    void given_list_of_organizations_when_sort_by_name_then_sorted_organization_list_should_be_returned(
            List<OrganizationEntity> organizationInTheDb,
            Sort sortByName,
            List<OrganizationEntity> expectedSortedOrganizationList
    ) {
        //given
        organizationInTheDb.forEach(o -> {
            testEntityManager.persist(o);
        });

        //when
        List<OrganizationEntity> results = organizationRepository.findAll(sortByName);

        //then
        for (int i=0;i<expectedSortedOrganizationList.size();i++) {
            assertEquals(expectedSortedOrganizationList.get(i).getName(), results.get(i).getName());
        }
    }
}