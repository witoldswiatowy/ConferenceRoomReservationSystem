package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long>  {
    boolean existsByName(String name);

    List<OrganizationEntity> findByNameContainingIgnoreCase(String name);

    List<OrganizationEntity> findAllByOrderByNameAsc();
    List<OrganizationEntity> findAllByOrderByNameDesc();
}
