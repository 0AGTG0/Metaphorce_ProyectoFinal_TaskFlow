package com.metaphorce.TaskFlow.repository;

import com.metaphorce.TaskFlow.modelo.GestionTiempo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GestionTiempoRepository extends JpaRepository<GestionTiempo, Integer> {

    List<GestionTiempo> findByFechaInicioBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<GestionTiempo> findByUsuario_IdUsuario(Integer idUsuario);

    List<GestionTiempo> findByUsuario_IdUsuarioAndTarea_IdTarea(Integer idUsuario, Integer idTarea);

    @Query("SELECT SUM(g.duracionSesion) FROM GestionTiempo g WHERE g.usuario.idUsuario = :idUsuario AND g.tarea.idTarea = :idTarea")
    Integer sumDuracionSesionByUsuarioAndTarea(@Param("idUsuario") Integer idUsuario, @Param("idTarea") Integer idTarea);
}
