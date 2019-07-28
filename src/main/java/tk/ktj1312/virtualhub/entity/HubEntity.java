package tk.ktj1312.virtualhub.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import tk.ktj1312.virtualhub.dto.HubDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbr_hub")
@NoArgsConstructor
@Data
public class HubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "hub_name",nullable = false,unique = true)
    private String hubName;
    @Column(name = "hub_ip",nullable = false,unique = true)
    private String hubIp;
    @Column(name = "hub_pass",nullable = false)
    private String hubPass;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "hub_id")
    private List<DeviceEntity> devices;

    public HubEntity(String hubName, String hubIp, String hubPass, List<DeviceEntity> devices) {
        this.hubName = hubName;
        this.hubIp = hubIp;
        this.hubPass = hubPass;
        this.devices = devices;
    }

    public HubEntity(String hubName, String hubIp, String hubPass) {
        this.hubName = hubName;
        this.hubIp = hubIp;
        this.hubPass = hubPass;
    }

    public void addDevice(DeviceEntity deviceEntity){
        this.devices.add(deviceEntity);
    }

    public void delDevice(DeviceEntity deviceEntity){
        this.devices.remove(deviceEntity);
    }
}
