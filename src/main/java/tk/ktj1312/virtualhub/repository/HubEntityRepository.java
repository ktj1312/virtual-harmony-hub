package tk.ktj1312.virtualhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.ktj1312.virtualhub.entity.HubEntity;

import java.util.Optional;

@Repository
public interface HubEntityRepository extends JpaRepository<HubEntity, String > {
    Optional<HubEntity> findByHubName(String hubName);
    Optional<HubEntity> findByHubNameAndDevices_slug(String HubName,String deviceSlug);
}
