package tk.ktj1312.virtualhub.vo;

import lombok.Data;

@Data
public class RegistCommandVo {
    private Long id;
    private String name;
    private String slug;
    private String label;
    private String type;
    private String data;
    private Integer length;
    private Integer khz;
}
