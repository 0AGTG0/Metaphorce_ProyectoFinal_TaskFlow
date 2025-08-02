package com.metaphorce.TaskFlow.controlador;

import com.metaphorce.TaskFlow.modelo.Proyecto;
import com.metaphorce.TaskFlow.modelo.Usuario;
import com.metaphorce.TaskFlow.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/TaskFlow/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    // Obtener todos los proyectos
    @GetMapping
    public ResponseEntity<List<Proyecto>> getAllProyectos() {
        List<Proyecto> proyectos = proyectoService.getAllProyectos();
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    // Obtener un proyecto por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable Integer id) {
        Optional<Proyecto> proyecto = proyectoService.getProyectoById(id);
        return proyecto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo proyecto
    @PostMapping
    public ResponseEntity<Proyecto> createProyecto(@RequestBody Proyecto proyecto) {
        Proyecto newProyecto = proyectoService.createProyecto(proyecto);
        return new ResponseEntity<>(newProyecto, HttpStatus.CREATED);
    }

    // Actualizar un proyecto existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Proyecto> updateProyecto(@PathVariable Integer id, @RequestBody Proyecto proyecto) {
        Proyecto updatedProyecto = proyectoService.updateProyecto(id, proyecto);
        if (updatedProyecto != null) {
            return new ResponseEntity<>(updatedProyecto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar un proyecto por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable Integer id) {
        proyectoService.deleteProyecto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Métodos personalizados
    // Obtener un proyecto por su título
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Proyecto> getProyectoByTitulo(@PathVariable String titulo) {
        Optional<Proyecto> proyecto = proyectoService.getProyectoByTitulo(titulo);
        return proyecto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener proyectos por la fecha de creación
    @GetMapping("/fecha-inicio/{fechaInicio}")
    public ResponseEntity<List<Proyecto>> getProyectosByFechaInicio(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio) {
        List<Proyecto> proyectos = proyectoService.getProyectosByFechaInicio(fechaInicio);
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    // Obtener proyectos asignados a un líder específico
    @GetMapping("/lider/{idLider}")
    public ResponseEntity<List<Proyecto>> getProyectosByLiderId(@PathVariable Integer idLider) {
        List<Proyecto> proyectos = proyectoService.getProyectosByLiderId(idLider);
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    // Obtener proyectos creados en un cierto periodo de tiempo
    @GetMapping("/periodo")
    public ResponseEntity<List<Proyecto>> getProyectosByPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Proyecto> proyectos = proyectoService.getProyectosByPeriodo(startDate, endDate);
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    // Obtener todos los usuarios asignados a un proyecto
    @GetMapping("/{idProyecto}/usuarios")
    public ResponseEntity<List<Usuario>> getUsuariosAsignadosByProyectoId(@PathVariable Integer idProyecto) {
        List<Usuario> usuarios = proyectoService.getUsuariosAsignadosByProyectoId(idProyecto);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
