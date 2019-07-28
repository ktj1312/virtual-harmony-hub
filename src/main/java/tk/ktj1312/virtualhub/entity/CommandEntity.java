package tk.ktj1312.virtualhub.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbr_command")
@Data
@NoArgsConstructor
public class CommandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String slug;
    private String label;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ir_code_id", referencedColumnName = "id")
    private IrCodeEntity ir_code;

    public CommandEntity(String name, String slug, String label, IrCodeEntity ir_code) {
        this.name = name;
        this.slug = slug;
        this.label = label;
        this.ir_code = ir_code;
    }

    public CommandEntity(Long id, String name, String slug, String label, IrCodeEntity ir_code) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.label = label;
        this.ir_code = ir_code;
    }
}

