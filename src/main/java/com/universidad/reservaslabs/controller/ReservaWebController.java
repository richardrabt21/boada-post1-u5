package com.universidad.reservaslabs.controller;

import com.universidad.reservaslabs.dto.ReservaRequestDTO;
import com.universidad.reservaslabs.repository.LaboratorioRepository;
import com.universidad.reservaslabs.service.ReservaService;
import com.universidad.reservaslabs.service.SolapamientoReservaException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/reservas")
public class ReservaWebController {

    private final ReservaService reservaService;
    private final LaboratorioRepository laboratorioRepository;

    public ReservaWebController(ReservaService reservaService, LaboratorioRepository laboratorioRepository) {
        this.reservaService = reservaService;
        this.laboratorioRepository = laboratorioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        return "reservas/lista";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("reserva", new ReservaRequestDTO());
        model.addAttribute("laboratorios", laboratorioRepository.findAll());
        return "reservas/nueva";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("reserva") ReservaRequestDTO dto,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("laboratorios", laboratorioRepository.findAll());
            return "reservas/nueva";
        }

        try {
            reservaService.crearReserva(dto);
            return "redirect:/reservas";
        } catch (SolapamientoReservaException | NoSuchElementException | IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("laboratorios", laboratorioRepository.findAll());
            return "reservas/nueva";
        }
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return "redirect:/reservas";
    }
}