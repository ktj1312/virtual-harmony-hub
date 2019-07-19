package tk.ktj1312.virtualhub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbr_hub")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HubEntity {

    @Id
    private String id;
    @Column(name = "hub_name",nullable = false,unique = true)
    private String hubName;
    @Column(name = "hub_ip",nullable = false,unique = true)
    private String hubIp;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "hub_id")
    private List<DeviceEntity> devices;
}
