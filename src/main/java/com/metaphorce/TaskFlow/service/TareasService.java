package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.enums.Estatus;
import com.metaphorce.TaskFlow.enums.Prioridad;
import com.metaphorce.TaskFlow.modelo.Tareas;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TareasService {
    List<Tareas> getAllTareas();
    Optional<Tareas> getTareaById(Integer id);
    Tareas createTarea(Tareas tarea);
    Tareas updateTarea(Integer id, Tareas tarea);
    void deleteTarea(Integer id);

    List<Tareas> getTareasByCreador(Integer idUsuario);
    List<Tareas> getTareasByUsuarioAsignado(Integer idUsuario);
    List<Tareas> getTareasByProyecto(Integer idProyecto);
    List<Tareas> getTareasByFechaCreacion(LocalDateTime fechaCreacion);
    List<Tareas> getTareasByPrioridad(Prioridad prioridad);
    List<Tareas> getTareasByEstatus(Estatus estatus);
}
