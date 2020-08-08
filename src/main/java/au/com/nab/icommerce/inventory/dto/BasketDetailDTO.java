package au.com.nab.icommerce.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketDetailDTO {

    private Long id;
    @NotNull
    private Long prodId;
    @Min(value = 0)
    private Integer quantity;
    private Instant createdAt;
    private Instant updatedAt;

}
