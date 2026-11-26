package sn.unchk.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.unchk.springproject.dto.OrderWithLinesDto;
import sn.unchk.springproject.entity.OrderDetails;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {
}
