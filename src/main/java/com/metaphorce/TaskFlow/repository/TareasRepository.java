package com.metaphorce.TaskFlow.repository;

import com.metaphorce.TaskFlow.enums.Estatus;
import com.metaphorce.TaskFlow.enums.Prioridad;
import com.metaphorce.TaskFlow.modelo.Tareas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TareasRepository extends JpaRepository<Tareas, Integer> {

    List<Tareas> findByCreadorTarea_IdUsuario(Integer idUsuario);

    List<Tareas> findByUsuarioAsignada_IdUsuario(Integer idUsuario);

    List<Tareas> findByProyecto_IdProyecto(Integer idProyecto);

    List<Tareas> findByFechaCreacion(LocalDateTime fechaCreacion);

    List<Tareas> findByPrioridad(Prioridad prioridad);

    List<Tareas> findByEstatus(Estatus estatus);
}
