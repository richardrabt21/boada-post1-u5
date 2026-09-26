package com.universidad.reservaslabs.service;

import com.universidad.reservaslabs.dto.ReservaRequestDTO;
import com.universidad.reservaslabs.model.EstadoReserva;
import com.universidad.reservaslabs.model.Laboratorio;
import com.universidad.reservaslabs.model.Reserva;
import com.universidad.reservaslabs.repository.LaboratorioRepository;
import com.universidad.reservaslabs.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final LaboratorioRepository laboratorioRepository;

    public ReservaService(ReservaRepository reservaRepository, LaboratorioRepository laboratorioRepository) {
        this.reservaRepository = reservaRepository;
        this.laboratorioRepository = laboratorioRepository;
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Reserva crearReserva(ReservaRequestDTO dto) {
        if (!dto.getFin().isAfter(dto.getInicio())) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
        }

        Laboratorio laboratorio = laboratorioRepository.findById(dto.getLaboratorioId())
                .orElseThrow(() -> new NoSuchElementException(
                        "No existe un laboratorio con id " + dto.getLaboratorioId()));

        List<Reserva> solapadas = reservaRepository.buscarSolapamientos(
                dto.getLaboratorioId(), dto.getInicio(), dto.getFin());

        if (!solapadas.isEmpty()) {
            throw new SolapamientoReservaException(
                    "El laboratorio '" + laboratorio.getNombre() +
                    "' ya tiene una reserva en ese horario");
        }

        Reserva reserva = new Reserva();
        reserva.setLaboratorio(laboratorio);
        reserva.setNombreSolicitante(dto.getNombreSolicitante());
        reserva.setCorreoSolicitante(dto.getCorreoSolicitante());
        reserva.setInicio(dto.getInicio());
        reserva.setFin(dto.getFin());
        reserva.setMotivo(dto.getMotivo());
        reserva.setEstado(EstadoReserva.CONFIRMADA);

        return reservaRepository.save(reserva);
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe una reserva con id " + id));
        reserva.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }
}