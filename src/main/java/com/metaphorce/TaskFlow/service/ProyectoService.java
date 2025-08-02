package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.modelo.Proyecto;
import com.metaphorce.TaskFlow.modelo.Usuario;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProyectoService {
    List<Proyecto> getAllProyectos();
    Optional<Proyecto> getProyectoById(Integer id);
    Proyecto createProyecto(Proyecto proyecto);
    Proyecto updateProyecto(Integer id, Proyecto proyecto);
    void deleteProyecto(Integer id);

    Optional<Proyecto> getProyectoByTitulo(String titulo);
    List<Proyecto> getProyectosByFechaInicio(LocalDateTime fechaInicio);
    List<Proyecto> getProyectosByLiderId(Integer idLider);
    List<Proyecto> getProyectosByPeriodo(LocalDateTime startDate, LocalDateTime endDate);
    List<Usuario> getUsuariosAsignadosByProyectoId(Integer idProyecto);
}
