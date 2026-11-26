package sn.unchk.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.unchk.springproject.entity.Client;
import sn.unchk.springproject.entity.Orders;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsClientByEmail(String email);

}
