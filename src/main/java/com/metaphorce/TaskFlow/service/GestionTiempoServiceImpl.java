package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.exception.RecursoNoEncontradoException;
import com.metaphorce.TaskFlow.modelo.GestionTiempo;
import com.metaphorce.TaskFlow.repository.GestionTiempoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GestionTiempoServiceImpl implements GestionTiempoService {

    @Autowired
    private GestionTiempoRepository gestionTiempoRepository;

    @Override
    public List<GestionTiempo> getAllGestionTiempo() {
        return gestionTiempoRepository.findAll();
    }

    @Override
    public Optional<GestionTiempo> getGestionTiempoById(Integer id) {
        return gestionTiempoRepository.findById(id);
    }

    @Override
    public GestionTiempo createGestionTiempo(GestionTiempo gestionTiempo) {
        return gestionTiempoRepository.save(gestionTiempo);
    }

    @Override
    public GestionTiempo updateGestionTiempo(Integer id, GestionTiempo gestionTiempo) {
        // En lugar de devolver null, lanzamos una excepción si el registro no existe
        return gestionTiempoRepository.findById(id).map(gestionExistente -> {
            gestionTiempo.setIdGestion(id);
            return gestionTiempoRepository.save(gestionTiempo);
        }).orElseThrow(() -> new RecursoNoEncontradoException("Registro de gestión de tiempo no encontrado con el ID: " + id));
    }

    @Override
    public void deleteGestionTiempo(Integer id) {
        // Verificamos la existencia antes de eliminar
        if (!gestionTiempoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Registro de gestión de tiempo no encontrado con el ID: " + id);
        }
        gestionTiempoRepository.deleteById(id);
    }

    @Override
    public List<GestionTiempo> getGestionTiempoByPeriodo(LocalDateTime startDate, LocalDateTime endDate) {
        return gestionTiempoRepository.findByFechaInicioBetween(startDate, endDate);
    }

    @Override
    public List<GestionTiempo> getGestionTiempoByUsuario(Integer idUsuario) {
        return gestionTiempoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public List<GestionTiempo> getGestionTiempoByUsuarioAndTarea(Integer idUsuario, Integer idTarea) {
        return gestionTiempoRepository.findByUsuario_IdUsuarioAndTarea_IdTarea(idUsuario, idTarea);
    }

    @Override
    public Integer getDuracionTotalSesion(Integer idUsuario, Integer idTarea) {
        return gestionTiempoRepository.sumDuracionSesionByUsuarioAndTarea(idUsuario, idTarea);
    }
}
