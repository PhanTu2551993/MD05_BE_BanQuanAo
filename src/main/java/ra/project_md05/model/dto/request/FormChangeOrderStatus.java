package ra.project_md05.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.project_md05.constants.OrderStatus;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FormChangeOrderStatus {
    private OrderStatus orderStatusName;
}
