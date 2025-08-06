package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.exception.RecursoNoEncontradoException;
import com.metaphorce.TaskFlow.modelo.Proyecto;
import com.metaphorce.TaskFlow.modelo.Usuario;
import com.metaphorce.TaskFlow.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public List<Proyecto> getAllProyectos() {
        return proyectoRepository.findAll();
    }

    @Override
    public Optional<Proyecto> getProyectoById(Integer id) {
        return proyectoRepository.findById(id);
    }

    @Override
    public Proyecto createProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    @Override
    public Proyecto updateProyecto(Integer id, Proyecto proyecto) {
        // En lugar de devolver null, lanzamos una excepción si el proyecto no existe
        return proyectoRepository.findById(id).map(proyectoExistente -> {
            proyecto.setIdProyecto(id);
            return proyectoRepository.save(proyecto);
        }).orElseThrow(() -> new RecursoNoEncontradoException("Proyecto no encontrado con el ID: " + id));
    }

    @Override
    public void deleteProyecto(Integer id) {
        // Verificamos la existencia antes de eliminar
        if (!proyectoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Proyecto no encontrado con el ID: " + id);
        }
        proyectoRepository.deleteById(id);
    }

    @Override
    public Optional<Proyecto> getProyectoByTitulo(String titulo) {
        return proyectoRepository.findByTitulo(titulo);
    }

    @Override
    public List<Proyecto> getProyectosByFechaInicio(LocalDateTime fechaInicio) {
        return proyectoRepository.findByFechaInicio(fechaInicio);
    }

    @Override
    public List<Proyecto> getProyectosByLiderId(Integer idLider) {
        return proyectoRepository.findByUsuario_IdUsuario(idLider);
    }

    @Override
    public List<Proyecto> getProyectosByPeriodo(LocalDateTime startDate, LocalDateTime endDate) {
        return proyectoRepository.findByFechaInicioBetween(startDate, endDate);
    }

    @Override
    public List<Usuario> getUsuariosAsignadosByProyectoId(Integer idProyecto) {
        return proyectoRepository.findUsuariosAsignadosByProyectoId(idProyecto);
    }
}
