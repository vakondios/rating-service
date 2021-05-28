package gr.xe.rating.service.models.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo extends AbstractDto {
    private String domain;
    private String reason;
    private String message;
    private String trxId;
}
