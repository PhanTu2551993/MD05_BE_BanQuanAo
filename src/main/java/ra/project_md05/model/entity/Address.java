package ra.project_md05.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "district")
    private String district;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "priority")
    private Boolean priority;

    @Column(name = "province")
    private String province;

    @Column(name = "receive_name", nullable = false)
    private String receiveName;

    @Column(name = "streetAddress")
    private String streetAddress;

    @Column(name = "ward")
    private String ward;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

}
