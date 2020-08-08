package au.com.nab.icommerce.inventory.controller.rest.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCriteria {
    private Long startDate;
    private Long endDate;
    private String name;
    private String color;
}
