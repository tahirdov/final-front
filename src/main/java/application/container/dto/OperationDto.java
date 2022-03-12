package application.container.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import application.container.enums.OperationStatuses;
import application.container.enums.OperationTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OperationDto {
    private String user_id;
    private OperationTypes operation_type;
    private OperationStatuses operation_status;
}
