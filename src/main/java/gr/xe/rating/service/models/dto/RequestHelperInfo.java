package gr.xe.rating.service.models.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RequestHelperInfo extends AbstractDto {
    private String requestURI;
    private String methodName;
    private String transactionID;
}
