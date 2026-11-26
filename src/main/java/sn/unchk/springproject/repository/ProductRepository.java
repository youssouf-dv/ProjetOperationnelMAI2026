package sn.unchk.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.unchk.springproject.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsProductByName(String name);
}
