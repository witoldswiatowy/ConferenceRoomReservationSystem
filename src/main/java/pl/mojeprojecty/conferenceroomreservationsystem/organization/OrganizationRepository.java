package pl.mojeprojecty.conferenceroomreservationsystem.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mojeprojecty.conferenceroomreservationsystem.organization.model.OrganizationEntity;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long>  {
}
