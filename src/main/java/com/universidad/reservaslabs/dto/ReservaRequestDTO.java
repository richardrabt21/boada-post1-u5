package com.universidad.reservaslabs.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ReservaRequestDTO {

    @NotNull(message = "El id del laboratorio es obligatorio")
    private Long laboratorioId;

    @NotBlank(message = "El nombre del solicitante no puede estar vacío")
    private String nombreSolicitante;

    @NotBlank(message = "El correo del solicitante no puede estar vacío")
    @Email(message = "El correo del solicitante debe ser válido")
    private String correoSolicitante;

    @NotNull(message = "La fecha y hora de inicio es obligatoria")
    private LocalDateTime inicio;

    @NotNull(message = "La fecha y hora de fin es obligatoria")
    private LocalDateTime fin;

    private String motivo;

    public Long getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(Long laboratorioId) { this.laboratorioId = laboratorioId; }

    public String getNombreSolicitante() { return nombreSolicitante; }
    public void setNombreSolicitante(String nombreSolicitante) { this.nombreSolicitante = nombreSolicitante; }

    public String getCorreoSolicitante() { return correoSolicitante; }
    public void setCorreoSolicitante(String correoSolicitante) { this.correoSolicitante = correoSolicitante; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}