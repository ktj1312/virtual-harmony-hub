package tk.ktj1312.virtualhub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbr_command")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandEntity {
    @Id
    private String id;
    private String name;
    private String slug;
    private String label;
}