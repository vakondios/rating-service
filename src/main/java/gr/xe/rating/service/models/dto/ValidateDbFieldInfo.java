package gr.xe.rating.service.models.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ValidateDbFieldInfo extends AbstractDto{
    private boolean isMandatory;
    private int len;
    private boolean lowerCase;
    private boolean upperCase;
}