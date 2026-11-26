package sn.unchk.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.unchk.springproject.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
