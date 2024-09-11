package com.veiculo.api.controller;

import com.veiculo.api.domain.veiculo.Marca;
import com.veiculo.api.domain.veiculo.RequestVeiculo;
import com.veiculo.api.domain.veiculo.Veiculo;
import com.veiculo.api.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class VeiculoControllerTest {

    @Mock
    private VeiculoRepository repository;

    @InjectMocks
    private VeiculoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrar() {
        RequestVeiculo request = new RequestVeiculo("Carro", Marca.CHEVROLET, 2000,
                "Veículo", "preto", true, LocalDateTime.now(), LocalDateTime.now());
        Veiculo veiculo = new Veiculo(request);

        when(repository.save(any(Veiculo.class))).thenReturn(veiculo);

        ResponseEntity response = controller.cadastrar(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).save(any(Veiculo.class));
    }

    @Test
    void testBuscarTodosVeiculos() {
        List<Veiculo> veiculos = List.of(new Veiculo());
        when(repository.findAll()).thenReturn(veiculos);

        ResponseEntity response = controller.buscarTodosVeiculos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculos, response.getBody());
    }

    @Test
    void testBuscarVeiculoPorIdFound() {
        Veiculo veiculo = new Veiculo();
        when(repository.findById(anyString())).thenReturn(Optional.of(veiculo));

        ResponseEntity<Veiculo> response = controller.buscarVeiculoPorId("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculo, response.getBody());
    }

    @Test
    void testBuscarVeiculoPorIdNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> controller.buscarVeiculoPorId("1"));
    }

    @Test
    void testContarVeiculosVendidosENaoVendidos() {
        when(repository.countVeiculosVendidos()).thenReturn(10L);
        when(repository.countVeiculosNaoVendidos()).thenReturn(5L);

        ResponseEntity<Map<String, Long>> response = controller.contarVeiculosVendidosENaoVendidos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, response.getBody().get("vendidos"));
        assertEquals(5L, response.getBody().get("naoVendidos"));
    }

    @Test
    void testContarVeiculosPorDecada() {
        List<Map<String, Object>> veiculosPorDecada = List.of(Map.of("decada", 1990, "count", 10));
        when(repository.countVeiculosPorDecada()).thenReturn(veiculosPorDecada);

        ResponseEntity<List<Map<String, Object>>> response = controller.contarVeiculosPorDecada();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculosPorDecada, response.getBody());
    }

    @Test
    void testContarVeiculosPorMarca() {
        List<Map<String, Object>> veiculosPorMarca = List.of(Map.of("marca", "Marca X", "count", 10));
        when(repository.countVeiculosPorMarca()).thenReturn(veiculosPorMarca);

        ResponseEntity<List<Map<String, Object>>> response = controller.contarVeiculosPorMarca();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculosPorMarca, response.getBody());
    }

    @Test
    void testAtualizarTodoVeiculoFound() {
        RequestVeiculo request = new RequestVeiculo("Carro", Marca.CHEVROLET, 2000,
                "Veículo", "preto", true, LocalDateTime.now(), LocalDateTime.now());
        Veiculo veiculo = new Veiculo();
        when(repository.findById(anyString())).thenReturn(Optional.of(veiculo));
        when(repository.save(any(Veiculo.class))).thenReturn(veiculo);

        ResponseEntity response = controller.atualizarTodoVeiculo("1", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).save(any(Veiculo.class));
    }

    @Test
    void testAtualizarTodoVeiculoNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> controller.atualizarTodoVeiculo("1", new RequestVeiculo("Carro", Marca.CHEVROLET, 2000,
                "Veículo", "preto", true, LocalDateTime.now(), LocalDateTime.now())));
    }

    @Test
    void testBuscarPorMarcaAnoCor() {
        List<Veiculo> veiculos = List.of(new Veiculo());
        when(repository.findByMarcaAnoCor(any(Marca.class), anyInt(), anyString())).thenReturn(veiculos);

        ResponseEntity<List<Veiculo>> response = controller.buscarPorMarcaAnoCor(Marca.CHEVROLET, 2020, "Vermelho");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculos, response.getBody());
    }

    @Test
    void testAtualizarParcialVeiculoFound() {
        RequestVeiculo request = new RequestVeiculo("Carro", Marca.CHEVROLET, 2000,
                "Veículo", "preto", true, LocalDateTime.now(), LocalDateTime.now());
        Veiculo veiculo = new Veiculo();
        when(repository.findById(anyString())).thenReturn(Optional.of(veiculo));
        when(repository.save(any(Veiculo.class))).thenReturn(veiculo);

        ResponseEntity response = controller.atualizarParcialVeiculo("1", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).save(any(Veiculo.class));
    }

    @Test
    void testAtualizarParcialVeiculoNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> controller.atualizarParcialVeiculo("1", new RequestVeiculo("Carro", Marca.CHEVROLET, 2000,
                "Veículo", "preto", true, LocalDateTime.now(), LocalDateTime.now())));
    }

    @Test
    void testDeletarVeiculoFound() {
        Veiculo veiculo = new Veiculo();
        when(repository.findById(anyString())).thenReturn(Optional.of(veiculo));
        doNothing().when(repository).delete(any(Veiculo.class));

        ResponseEntity<Void> response = controller.deletarVeiculo("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(repository, times(1)).delete(any(Veiculo.class));
    }

    @Test
    void testDeletarVeiculoNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> controller.deletarVeiculo("1"));
    }
}
