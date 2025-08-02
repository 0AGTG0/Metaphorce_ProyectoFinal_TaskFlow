package com.metaphorce.TaskFlow.repository;

import com.metaphorce.TaskFlow.modelo.Proyecto;
import com.metaphorce.TaskFlow.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    Optional<Proyecto> findByTitulo(String titulo);

    List<Proyecto> findByFechaInicio(LocalDateTime fechaInicio);

    List<Proyecto> findByUsuario_IdUsuario(Integer idUsuario);

    List<Proyecto> findByFechaInicioBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT DISTINCT t.usuarioAsignada FROM Tareas t WHERE t.proyecto.idProyecto = :idProyecto")
    List<Usuario> findUsuariosAsignadosByProyectoId(@Param("idProyecto") Integer idProyecto);
}
