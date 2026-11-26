package sn.unchk.springproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.unchk.springproject.dto.DetailDto;
import sn.unchk.springproject.dto.ItemsRequestDto;
import sn.unchk.springproject.dto.OrderCreateDto;
import sn.unchk.springproject.dto.OrderDto;
import sn.unchk.springproject.dto.OrderWithLinesDto;
import sn.unchk.springproject.entity.Client;
import sn.unchk.springproject.entity.OrderDetails;
import sn.unchk.springproject.entity.Orders;
import sn.unchk.springproject.entity.Product;
import sn.unchk.springproject.exception.InsufficientStockException;
import sn.unchk.springproject.exception.ResourceNotFoundException;
import sn.unchk.springproject.repository.ClientRepository;
import sn.unchk.springproject.repository.OrderDetailsRepository;
import sn.unchk.springproject.repository.OrdersRepository;
import sn.unchk.springproject.repository.ProductRepository;
import sn.unchk.springproject.utils.Status;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;


    private Orders findOrder(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable : " + id));
    }

    private Client findClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : " + id));
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable : " + id));
    }

    private OrderDto toDto(Orders o) {
        return new OrderDto(o.getId(), o.getDate(), o.getStatus(), o.getClient().getId());
    }


    @Transactional
    public OrderDto createOrder(OrderCreateDto req) {
        Client client = findClient(req.clientId());
        Orders order = new Orders(client);

        for (ItemsRequestDto item : req.items()) {
            if (item.quantity() <= 0) {
                throw new IllegalArgumentException("La quantité doit être positive");
            }

            // 2. Chercher le produit
            Product product = findProduct(item.productId());

            // 3. Vérifier le stock
            if (product.getStock() < item.quantity()) {
                throw new InsufficientStockException("Stock insuffisant pour : " + product.getName());
            }
            product.setStock(product.getStock() - item.quantity());

            order.addDetail(new OrderDetails(product, item.quantity(), product.getPrice()));
        }

        return toDto(ordersRepository.save(order));
    }


    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        return ordersRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public OrderWithLinesDto getOrderById(Long id) {
        Orders order = findOrder(id);

        List<DetailDto> lines = order.getOrderDetails().stream()
                .map(d -> new DetailDto(d.getId(), d.getProduct().getId(), order.getId(),
                        d.getQuantity(), d.getUnitPrice()))
                .toList();

        int total = 0;
        for (OrderDetails d : order.getOrderDetails()) {
            total += d.getUnitPrice() * d.getQuantity();
        }

        return new OrderWithLinesDto(order.getId(), order.getDate(), order.getStatus(),
                order.getClient().getId(), lines, total);
    }

    @Transactional(readOnly = true)
    public List<DetailDto> getAllDetails() {
        return orderDetailsRepository.findAll().stream()
                .map(d -> new DetailDto(d.getId(), d.getProduct().getId(),
                        d.getOrders().getId(), d.getQuantity(), d.getUnitPrice()))
                .toList();
    }


    @Transactional
    public OrderDto updateStatus(Long id, Status newStatus) {
        Orders order = findOrder(id);
        Status oldStatus = order.getStatus();


        if (oldStatus == Status.ANNULEE && newStatus != Status.ANNULEE) {
            throw new IllegalStateException("Impossible de modifier une commande déjà annulée");
        }

        if (oldStatus != Status.ANNULEE && newStatus == Status.ANNULEE) {
            for (OrderDetails detail : order.getOrderDetails()) {
                Product product = detail.getProduct();
                product.setStock(product.getStock() + detail.getQuantity());
            }
        }

        order.setStatus(newStatus);
        return toDto(order);
    }


    @Transactional
    public void deleteOrder(Long id) {
        Orders order = findOrder(id);
        if (order.getStatus() != Status.ANNULEE) {
            for (OrderDetails detail : order.getOrderDetails()) {
                Product product = detail.getProduct();
                product.setStock(product.getStock() + detail.getQuantity());
            }
        }
        ordersRepository.delete(order);
    }
}