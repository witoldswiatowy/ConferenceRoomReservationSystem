package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

}