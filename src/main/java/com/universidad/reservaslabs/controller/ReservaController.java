package com.universidad.reservaslabs.controller;

import com.universidad.reservaslabs.dto.ReservaRequestDTO;
import com.universidad.reservaslabs.model.Reserva;
import com.universidad.reservaslabs.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Reserva> crear(@Valid @RequestBody ReservaRequestDTO dto) {
        Reserva creada = reservaService.crearReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }
}