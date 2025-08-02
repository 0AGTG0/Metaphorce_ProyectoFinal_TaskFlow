package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.modelo.GestionTiempo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GestionTiempoService {
    List<GestionTiempo> getAllGestionTiempo();
    Optional<GestionTiempo> getGestionTiempoById(Integer id);
    GestionTiempo createGestionTiempo(GestionTiempo gestionTiempo);
    GestionTiempo updateGestionTiempo(Integer id, GestionTiempo gestionTiempo);
    void deleteGestionTiempo(Integer id);

    List<GestionTiempo> getGestionTiempoByPeriodo(LocalDateTime startDate, LocalDateTime endDate);
    List<GestionTiempo> getGestionTiempoByUsuario(Integer idUsuario);
    List<GestionTiempo> getGestionTiempoByUsuarioAndTarea(Integer idUsuario, Integer idTarea);
    Integer getDuracionTotalSesion(Integer idUsuario, Integer idTarea);
}
