package com.veiculo.api.repository;

import com.veiculo.api.domain.veiculo.Marca;
import com.veiculo.api.domain.veiculo.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface VeiculoRepository extends JpaRepository<Veiculo, String> {

    @Query("SELECT COUNT(v) FROM veiculos v WHERE v.vendido = true")
    long countVeiculosVendidos();

    @Query("SELECT COUNT(v) FROM veiculos v WHERE v.vendido = false")
    long countVeiculosNaoVendidos();

    @Query("SELECT (v.ano / 10) * 10 AS decada, COUNT(v) AS quantidade " +
            "FROM veiculos v " +
            "GROUP BY decada " +
            "ORDER BY decada")
    List<Map<String, Object>> countVeiculosPorDecada();

    @Query("SELECT v.marca AS marca, COUNT(v) AS quantidade " +
            "FROM veiculos v " +
            "GROUP BY v.marca " +
            "ORDER BY quantidade DESC")
    List<Map<String, Object>> countVeiculosPorMarca();

    @Query("SELECT v FROM veiculos v WHERE " +
            "(:marca IS NULL OR v.marca = :marca) AND " +
            "(:ano IS NULL OR v.ano = :ano) AND " +
            "(:cor IS NULL OR v.cor = :cor)")
    List<Veiculo> findByMarcaAnoCor(
            @Param("marca") Marca marca,
            @Param("ano") Integer ano,
            @Param("cor") String cor);
}
