package tk.ktj1312.virtualhub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbr_ircode")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IrCodeEntity {
    @Id
    private String id;
    private String type;
    private String data;
    private Integer length;
    private Integer repeat;
    private Integer rdelay;
    private Integer khz;
    private Integer pulse;
}
