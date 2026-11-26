package sn.unchk.springproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.unchk.springproject.utils.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date = LocalDateTime.now();

    public Orders(Client client) {
        this.client = client;
    }

    @Enumerated(EnumType.STRING)
    private Status status = Status.EN_COURS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderDetails> orderDetails = new ArrayList<>();

    public void addDetail(OrderDetails details){
        orderDetails.add(details);
        details.setOrders(this);
    }

    public void removeDetail(OrderDetails details){
        orderDetails.remove(details);
        details.setOrders(null);
    }
}
