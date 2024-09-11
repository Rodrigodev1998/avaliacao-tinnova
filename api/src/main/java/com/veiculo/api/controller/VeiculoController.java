package com.veiculo.api.controller;

import com.veiculo.api.domain.veiculo.Marca;
import com.veiculo.api.domain.veiculo.RequestVeiculo;
import com.veiculo.api.domain.veiculo.Veiculo;
import com.veiculo.api.repository.VeiculoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
@CrossOrigin(origins = "*")
@Tag(name = "Veículo", description = "APIs para gerenciamento de veículos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository repository;

    @PostMapping
    @Operation(summary = "Cadastrar Veículo", description = "Cadastra um novo veículo no sistema.")
    @ApiResponse(responseCode = "200", description = "Veículo cadastrado com sucesso")
    public ResponseEntity cadastrar(@RequestBody @Valid RequestVeiculo data) {
        Veiculo novoVeiculo = new Veiculo(data);
        repository.save(novoVeiculo);
        return  ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Listar Todos os Veículos", description = "Busca todos os veículos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de veículos recuperada com sucesso")
    public ResponseEntity buscarTodosVeiculos(){
        var todosVeiculos = repository.findAll();
        return ResponseEntity.ok(todosVeiculos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Veículo por ID", description = "Busca um veículo específico pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Veículo encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    public ResponseEntity<Veiculo> buscarVeiculoPorId(@PathVariable String id){
        Optional<Veiculo> veiculo = repository.findById(id);
        if (veiculo.isPresent()){
            return ResponseEntity.ok(veiculo.get());
        } else {
            throw new EntityNotFoundException("Veiculo não encontrado");
        }
    }

    @GetMapping("/contagem-veiculos")
    @Operation(summary = "Contar Veículos Vendidos e Não Vendidos", description = "Conta a quantidade de veículos vendidos e não vendidos.")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    public ResponseEntity<Map<String, Long>> contarVeiculosVendidosENaoVendidos() {
        long vendidos = repository.countVeiculosVendidos();
        long naoVendidos = repository.countVeiculosNaoVendidos();

        Map<String, Long> contagem = new HashMap<>();
        contagem.put("vendidos", vendidos);
        contagem.put("naoVendidos", naoVendidos);

        return ResponseEntity.ok(contagem);
    }

    @GetMapping("/contagem-por-decada")
    @Operation(summary = "Contar veículos por decada", description = "Conta a quantidade de veiculo a partir da decada.")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    public ResponseEntity<List<Map<String, Object>>> contarVeiculosPorDecada() {
        List<Map<String, Object>> veiculosPorDecada = repository.countVeiculosPorDecada();
        return ResponseEntity.ok(veiculosPorDecada);
    }

    @GetMapping("/contagem-por-marca")
    @Operation(summary = "Contar veículos por marca", description = "Conta a quantidade de veículos a partir da marca .")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    public ResponseEntity<List<Map<String, Object>>> contarVeiculosPorMarca() {
        List<Map<String, Object>> veiculosPorMarca = repository.countVeiculosPorMarca();
        return ResponseEntity.ok(veiculosPorMarca);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar informações do veiculo", description = "Atualiza informações do veiculo ")
    @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso")
    public ResponseEntity atualizarTodoVeiculo(@PathVariable String id, @RequestBody @Valid RequestVeiculo data){
        Optional<Veiculo> optionalVeiculo = repository.findById(id);
        if (optionalVeiculo.isPresent()) {
            Veiculo veiculo = optionalVeiculo.get();
            veiculo.setMarca(data.marca());
            veiculo.setAno(data.ano());
            veiculo.setDescricao(data.descricao());
            veiculo.setVeiculo(data.veiculo());
            veiculo.setVendido(data.vendido());
            repository.save(veiculo);
            return ResponseEntity.ok(veiculo);
        } else {
            throw new EntityNotFoundException("Error ao atualizar o veiculo");
        }
    }

    @GetMapping("/filter")
    @Operation(summary = "Buscar veiculos pela marca, cor e ano", description = "Busca todos os veiculos a partir da marca, cor e ano ")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public ResponseEntity<List<Veiculo>> buscarPorMarcaAnoCor(
            @RequestParam(required = false) Marca marca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String cor) {

        List<Veiculo> veiculos = repository.findByMarcaAnoCor(marca, ano, cor);
        return ResponseEntity.ok(veiculos);
    }

    @PatchMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar informação do veiculo", description = "Atualiza informação do veiculo ")
    @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso")
    public ResponseEntity atualizarParcialVeiculo(@PathVariable String id, @RequestBody @Valid RequestVeiculo data){
        Optional<Veiculo> optionalVeiculo = repository.findById(id);
        if (optionalVeiculo.isPresent()) {
            Veiculo veiculo = optionalVeiculo.get();
            veiculo.setMarca(data.marca());
            veiculo.setAno(data.ano());
            veiculo.setDescricao(data.descricao());
            veiculo.setVeiculo(data.veiculo());
            veiculo.setVendido(data.vendido());
            repository.save(veiculo);
            return ResponseEntity.ok(veiculo);
        } else {
            throw new EntityNotFoundException("Error ao atualizar o veiculo");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Excluir o veiculo", description = "Exclui o veiculo por id")
    @ApiResponse(responseCode = "200", description = "Exclusão realizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Error ao excluir o veiculo")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable String id){
        Optional<Veiculo> optionalVeiculo = repository.findById(id);
        if (optionalVeiculo.isPresent()){
            Veiculo veiculo = optionalVeiculo.get();
            repository.delete(veiculo);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Error ao excluir o veiculo");
        }
    }

}
