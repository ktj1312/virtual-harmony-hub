package tk.ktj1312.virtualhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbr_device")
@NoArgsConstructor
@Data
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "slug",nullable = false,unique = true)
    private String slug;
    @Column(name = "label",nullable = false)
    private String label;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    @JsonIgnore
    private List<CommandEntity> commands;

    public DeviceEntity(String slug, String label, List<CommandEntity> commands) {
        this.slug = slug;
        this.label = label;
        this.commands = commands;
    }
}
