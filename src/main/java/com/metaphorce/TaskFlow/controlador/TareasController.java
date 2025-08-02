package com.metaphorce.TaskFlow.controlador;

import com.metaphorce.TaskFlow.enums.Estatus;
import com.metaphorce.TaskFlow.enums.Prioridad;
import com.metaphorce.TaskFlow.modelo.Tareas;
import com.metaphorce.TaskFlow.service.TareasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/TaskFlow/tareas")
public class TareasController {

    @Autowired
    private TareasService tareasService;

    // Obtener todas las tareas
    @GetMapping
    public ResponseEntity<List<Tareas>> getAllTareas() {
        List<Tareas> tareas = tareasService.getAllTareas();
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener una tarea por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Tareas> getTareaById(@PathVariable Integer id) {
        Optional<Tareas> tarea = tareasService.getTareaById(id);
        return tarea.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear una nueva tarea
    @PostMapping
    public ResponseEntity<Tareas> createTarea(@RequestBody Tareas tarea) {
        Tareas newTarea = tareasService.createTarea(tarea);
        return new ResponseEntity<>(newTarea, HttpStatus.CREATED);
    }

    // Actualizar una tarea existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Tareas> updateTarea(@PathVariable Integer id, @RequestBody Tareas tarea) {
        Tareas updatedTarea = tareasService.updateTarea(id, tarea);
        if (updatedTarea != null) {
            return new ResponseEntity<>(updatedTarea, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar una tarea por su ID
    @DeleteMapping("/elimniar/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Integer id) {
        tareasService.deleteTarea(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Métodos personalizados
    // Obtener tareas por el creador
    @GetMapping("/creador/{idUsuario}")
    public ResponseEntity<List<Tareas>> getTareasByCreador(@PathVariable Integer idUsuario) {
        List<Tareas> tareas = tareasService.getTareasByCreador(idUsuario);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener tareas por el usuario asignado
    @GetMapping("/asignado/{idUsuario}")
    public ResponseEntity<List<Tareas>> getTareasByUsuarioAsignado(@PathVariable Integer idUsuario) {
        List<Tareas> tareas = tareasService.getTareasByUsuarioAsignado(idUsuario);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener tareas por el proyecto al que están asignadas
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<Tareas>> getTareasByProyecto(@PathVariable Integer idProyecto) {
        List<Tareas> tareas = tareasService.getTareasByProyecto(idProyecto);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener tareas por su fecha de creación
    @GetMapping("/fecha-creacion/{fechaCreacion}")
    public ResponseEntity<List<Tareas>> getTareasByFechaCreacion(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaCreacion) {
        List<Tareas> tareas = tareasService.getTareasByFechaCreacion(fechaCreacion);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener tareas por su prioridad
    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<Tareas>> getTareasByPrioridad(@PathVariable Prioridad prioridad) {
        List<Tareas> tareas = tareasService.getTareasByPrioridad(prioridad);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    // Obtener tareas por su estatus
    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<Tareas>> getTareasByEstatus(@PathVariable Estatus estatus) {
        List<Tareas> tareas = tareasService.getTareasByEstatus(estatus);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }
}
