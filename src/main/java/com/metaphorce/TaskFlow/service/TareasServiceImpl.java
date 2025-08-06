package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.exception.RecursoNoEncontradoException;
import com.metaphorce.TaskFlow.enums.Estatus;
import com.metaphorce.TaskFlow.enums.Prioridad;
import com.metaphorce.TaskFlow.modelo.Tareas;
import com.metaphorce.TaskFlow.repository.TareasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TareasServiceImpl implements TareasService {

    @Autowired
    private TareasRepository tareasRepository;

    @Override
    public List<Tareas> getAllTareas() {
        return tareasRepository.findAll();
    }

    @Override
    public Optional<Tareas> getTareaById(Integer id) {
        return tareasRepository.findById(id);
    }

    @Override
    public Tareas createTarea(Tareas tarea) {
        return tareasRepository.save(tarea);
    }

    @Override
    public Tareas updateTarea(Integer id, Tareas tarea) {
        // Buscamos la tarea por ID y si no existe, lanzamos la excepción
        return tareasRepository.findById(id).map(tareaExistente -> {
            tarea.setIdTarea(id);
            return tareasRepository.save(tarea);
        }).orElseThrow(() -> new RecursoNoEncontradoException("Tarea no encontrada con el ID: " + id));
    }

    @Override
    public void deleteTarea(Integer id) {
        // Verificamos la existencia antes de eliminar
        if (!tareasRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Tarea no encontrada con el ID: " + id);
        }
        tareasRepository.deleteById(id);
    }

    @Override
    public List<Tareas> getTareasByCreador(Integer idUsuario) {
        return tareasRepository.findByCreadorTarea_IdUsuario(idUsuario);
    }

    @Override
    public List<Tareas> getTareasByUsuarioAsignado(Integer idUsuario) {
        return tareasRepository.findByUsuarioAsignada_IdUsuario(idUsuario);
    }

    @Override
    public List<Tareas> getTareasByProyecto(Integer idProyecto) {
        return tareasRepository.findByProyecto_IdProyecto(idProyecto);
    }

    @Override
    public List<Tareas> getTareasByFechaCreacion(LocalDateTime fechaCreacion) {
        return tareasRepository.findByFechaCreacion(fechaCreacion);
    }

    @Override
    public List<Tareas> getTareasByPrioridad(Prioridad prioridad) {
        return tareasRepository.findByPrioridad(prioridad);
    }

    @Override
    public List<Tareas> getTareasByEstatus(Estatus estatus) {
        return tareasRepository.findByEstatus(estatus);
    }
}
