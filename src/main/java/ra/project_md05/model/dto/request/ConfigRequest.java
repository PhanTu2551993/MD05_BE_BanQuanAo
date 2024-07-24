package ra.project_md05.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConfigRequest {

    private Long id;
    @NotEmpty(message = "configName Name cannot be empty")
    private String configName;
    @NotEmpty(message = "configValue cannot be empty")
    private String configValue;
    private boolean status = true;

}
