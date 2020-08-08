package au.com.nab.icommerce.inventory.domain.repository;

import au.com.nab.icommerce.inventory.domain.model.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
}
