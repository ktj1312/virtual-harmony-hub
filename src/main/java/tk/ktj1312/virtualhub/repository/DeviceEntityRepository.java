package tk.ktj1312.virtualhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.ktj1312.virtualhub.entity.DeviceEntity;
import tk.ktj1312.virtualhub.entity.HubEntity;

import java.util.Optional;

@Repository
public interface DeviceEntityRepository extends JpaRepository<DeviceEntity, Long > {
}
