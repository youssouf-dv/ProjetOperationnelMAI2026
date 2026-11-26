package sn.unchk.springproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateDto(
        @NotNull Long clientId,
        @NotEmpty @Valid List<ItemsRequestDto> items
) {}
