package ra.project_md05.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "color")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long id;

    private String colorName;

    private Boolean status;
}
