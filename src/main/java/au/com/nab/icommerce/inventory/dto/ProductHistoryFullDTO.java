package au.com.nab.icommerce.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistoryFullDTO {

    private Long id;

    @NotNull
    @NotEmpty(message = "Please enter name")
    private String name;

    @NotNull
    @Size(max = 50, min = 3, message = "Invalid length")
    private String description;

    @Min(value = 1, message = "Min is 1")
    private Double price;

    @NotNull(message = "Color is not null")
    @NotEmpty(message = "Color is not empty")
    private String color;

    private Long belongToProdId;

    @NotNull
    @Min(value = 0, message = "Min is 0")
    private Integer quantity;

    private Instant createdAt;

    private Instant updatedAt;
}
