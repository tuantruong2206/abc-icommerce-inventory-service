package au.com.nab.icommerce.inventory.domain.repository;

import au.com.nab.icommerce.inventory.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT prod FROM Product prod WHERE prod.name LIKE CONCAT('%',:name,'%') and prod.color LIKE CONCAT('%',:color,'%') and prod.createdAt >= :startDate AND prod.createdAt <= :endDate")
    public Page<Product> findProductByCriteria(@Param("name") String name, @Param("color") String color, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate, Pageable pageable);

    List<Product> findAllByIdIn(List<Long> ids);
}
