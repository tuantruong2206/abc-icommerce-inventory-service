package au.com.nab.icommerce.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketDTO {


    private Long id;
    private String userid;
    private Boolean status;
    private Instant createdAt;
    private Instant updatedAt;
    private Set<BasketDetailDTO> basketDetails;
}
