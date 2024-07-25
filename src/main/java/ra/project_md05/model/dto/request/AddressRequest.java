package ra.project_md05.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressRequest {
    private Long addressId;
    private String district;
    private String phone;
    private Boolean priority;
    private String province;
    private String receiveName;
    private String streetAddress;
    private String ward;
    private Long userId;
}

