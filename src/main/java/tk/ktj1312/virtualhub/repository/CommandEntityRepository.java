package tk.ktj1312.virtualhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.ktj1312.virtualhub.entity.CommandEntity;
import tk.ktj1312.virtualhub.entity.DeviceEntity;

@Repository
public interface CommandEntityRepository extends JpaRepository<CommandEntity, Long > {
}
