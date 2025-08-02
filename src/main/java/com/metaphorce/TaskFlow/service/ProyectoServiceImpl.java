package com.metaphorce.TaskFlow.service;

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
        if (proyectoRepository.existsById(id)) {
            proyecto.setIdProyecto(id);
            return proyectoRepository.save(proyecto);
        }
        return null;
    }

    @Override
    public void deleteProyecto(Integer id) {
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
