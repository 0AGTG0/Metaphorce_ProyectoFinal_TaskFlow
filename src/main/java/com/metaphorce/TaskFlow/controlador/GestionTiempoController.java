package com.metaphorce.TaskFlow.controlador;

import com.metaphorce.TaskFlow.modelo.GestionTiempo;
import com.metaphorce.TaskFlow.service.GestionTiempoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/TaskFlow/gestion-tiempo")
public class GestionTiempoController {

    @Autowired
    private GestionTiempoService gestionTiempoService;

    // Obtener todos los registros de tiempo
    @GetMapping
    public ResponseEntity<List<GestionTiempo>> getAllGestionTiempo() {
        List<GestionTiempo> registros = gestionTiempoService.getAllGestionTiempo();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    // Obtener un registro de tiempo por su ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<GestionTiempo> getGestionTiempoById(@PathVariable Integer id) {
        Optional<GestionTiempo> registro = gestionTiempoService.getGestionTiempoById(id);
        return registro.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo registro de tiempo
    @PostMapping
    public ResponseEntity<GestionTiempo> createGestionTiempo(@RequestBody GestionTiempo gestionTiempo) {
        GestionTiempo newRegistro = gestionTiempoService.createGestionTiempo(gestionTiempo);
        return new ResponseEntity<>(newRegistro, HttpStatus.CREATED);
    }

    // Actualizar un registro de tiempo existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<GestionTiempo> updateGestionTiempo(@PathVariable Integer id, @RequestBody GestionTiempo gestionTiempo) {
        GestionTiempo updatedRegistro = gestionTiempoService.updateGestionTiempo(id, gestionTiempo);
        if (updatedRegistro != null) {
            return new ResponseEntity<>(updatedRegistro, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar un registro de tiempo por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteGestionTiempo(@PathVariable Integer id) {
        gestionTiempoService.deleteGestionTiempo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Métodos personalizados
    // Obtener registros de tiempo en un determinado intervalo
    @GetMapping("/periodo")
    public ResponseEntity<List<GestionTiempo>> getGestionTiempoByPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<GestionTiempo> registros = gestionTiempoService.getGestionTiempoByPeriodo(startDate, endDate);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    // Obtener todos los registros de tiempo de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<GestionTiempo>> getGestionTiempoByUsuario(@PathVariable Integer idUsuario) {
        List<GestionTiempo> registros = gestionTiempoService.getGestionTiempoByUsuario(idUsuario);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    // Obtener todos los registros de tiempo de una tarea específica por un usuario
    @GetMapping("/usuario/{idUsuario}/tarea/{idTarea}")
    public ResponseEntity<List<GestionTiempo>> getGestionTiempoByUsuarioAndTarea(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idTarea) {
        List<GestionTiempo> registros = gestionTiempoService.getGestionTiempoByUsuarioAndTarea(idUsuario, idTarea);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    // Obtener la duración total de una tarea por un usuario
    @GetMapping("/duracion-total/usuario/{idUsuario}/tarea/{idTarea}")
    public ResponseEntity<Integer> getDuracionTotalSesion(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idTarea) {
        Integer duracion = gestionTiempoService.getDuracionTotalSesion(idUsuario, idTarea);
        if (duracion != null) {
            return new ResponseEntity<>(duracion, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
