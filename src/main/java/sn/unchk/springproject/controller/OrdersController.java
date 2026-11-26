package sn.unchk.springproject.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.unchk.springproject.dto.DetailDto;
import sn.unchk.springproject.dto.OrderCreateDto;
import sn.unchk.springproject.dto.OrderDto;
import sn.unchk.springproject.dto.OrderWithLinesDto;
import sn.unchk.springproject.service.OrderService;
import sn.unchk.springproject.utils.Status;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final OrderService orderService;

    // Corps attendu pour changer le statut : { "status": "ANNULEE" }
    public record StatusUpdateDto(@NotNull Status status) {}

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        return new ResponseEntity<>(orderService.createOrder(orderCreateDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/details")
    public ResponseEntity<List<DetailDto>> getAllDetails() {
        return ResponseEntity.ok(orderService.getAllDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderWithLinesDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id,
                                                 @Valid @RequestBody StatusUpdateDto body) {
        return ResponseEntity.ok(orderService.updateStatus(id, body.status()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}