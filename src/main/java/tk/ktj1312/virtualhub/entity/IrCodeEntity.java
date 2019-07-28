package tk.ktj1312.virtualhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbr_ircode")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"id","command"})
public class IrCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer length;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer repeat;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer rdelay;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer khz;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pulse;

    @OneToOne(mappedBy = "ir_code")
    private CommandEntity command;

    public IrCodeEntity(String type, String data, Integer length, Integer repeat, Integer rdelay, Integer khz, Integer pulse) {
        this.type = type;
        this.data = data;
        this.length = length;
        this.repeat = repeat;
        this.rdelay = rdelay;
        this.khz = khz;
        this.pulse = pulse;
    }

    public IrCodeEntity(String type, String data, Integer length, Integer khz) {
        this.type = type;
        this.data = data;
        this.length = length;
        this.khz = khz;
    }
}
