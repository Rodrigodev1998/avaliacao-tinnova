package com.veiculo.api.domain.veiculo;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestVeiculo(
        @NotNull String veiculo,
        @NotNull Marca marca,
        @NotNull Integer ano,
        @NotNull String descricao,
        @NotNull String cor,
        @NotNull boolean vendido,
        LocalDateTime created,
        LocalDateTime update
) {
}
