package sn.unchk.springproject.dto;

public record DetailDto(
        Long id,
        Long productId,
        Long orderId,
        int quantity,
        int unitPrice
) {
}
