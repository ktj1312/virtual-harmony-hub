package tk.ktj1312.virtualhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbr_device")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEntity {
    @Id
    private String id;
    private String slug;
    private String label;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    @JsonIgnore
    private List<CommandEntity> commands;
}
