package au.com.nab.icommerce.inventory.dto.response;

import au.com.nab.icommerce.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private long id;
    private String name;
}
