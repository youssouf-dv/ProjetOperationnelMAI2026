package sn.unchk.springproject.dto;

import sn.unchk.springproject.utils.Status;

import java.time.LocalDateTime;
import java.util.List;

public record OrderWithLinesDto(
        Long id,
        LocalDateTime date,
        Status status,
        Long clientId,
        List<DetailDto> lines,
        int total) {
}