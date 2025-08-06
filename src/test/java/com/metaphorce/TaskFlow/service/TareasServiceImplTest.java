package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.enums.Estatus;
import com.metaphorce.TaskFlow.enums.Prioridad;
import com.metaphorce.TaskFlow.exception.RecursoNoEncontradoException;
import com.metaphorce.TaskFlow.modelo.Proyecto;
import com.metaphorce.TaskFlow.modelo.Tareas;
import com.metaphorce.TaskFlow.modelo.Usuario;
import com.metaphorce.TaskFlow.repository.TareasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TareasServiceImplTest {

    @Mock
    private TareasRepository tareasRepository;

    @InjectMocks
    private TareasServiceImpl tareasService;

    private Tareas tarea1;
    private Tareas tarea2;
    private Usuario creador;
    private Usuario asignado;
    private Proyecto proyecto;

    @BeforeEach
    void setUp() {
        creador = new Usuario();
        creador.setIdUsuario(1);

        asignado = new Usuario();
        asignado.setIdUsuario(2);

        proyecto = new Proyecto();
        proyecto.setIdProyecto(10);

        tarea1 = new Tareas();
        tarea1.setIdTarea(1);
        tarea1.setTitulo("Implementar login");
        tarea1.setCreadorTarea(creador);
        tarea1.setUsuarioAsignada(asignado);
        tarea1.setProyecto(proyecto);
        tarea1.setPrioridad(Prioridad.Alta);
        tarea1.setEstatus(Estatus.En_progreso);

        tarea2 = new Tareas();
        tarea2.setIdTarea(2);
        tarea2.setTitulo("Diseñar base de datos");
        tarea2.setCreadorTarea(creador);
        tarea2.setUsuarioAsignada(asignado);
        tarea2.setProyecto(proyecto);
        tarea2.setPrioridad(Prioridad.Media);
        tarea2.setEstatus(Estatus.Asignada);
    }

    @Test
    void testGetAllTareas() {
        when(tareasRepository.findAll()).thenReturn(Arrays.asList(tarea1, tarea2));
        List<Tareas> tareas = tareasService.getAllTareas();
        assertNotNull(tareas);
        assertEquals(2, tareas.size());
        verify(tareasRepository, times(1)).findAll();
    }

    @Test
    void testGetTareaById_Exitoso() {
        when(tareasRepository.findById(1)).thenReturn(Optional.of(tarea1));
        Optional<Tareas> foundTarea = tareasService.getTareaById(1);
        assertTrue(foundTarea.isPresent());
        assertEquals(tarea1, foundTarea.get());
        verify(tareasRepository, times(1)).findById(1);
    }

    @Test
    void testGetTareaById_NoEncontrado() {
        when(tareasRepository.findById(99)).thenReturn(Optional.empty());
        Optional<Tareas> foundTarea = tareasService.getTareaById(99);
        assertFalse(foundTarea.isPresent());
        verify(tareasRepository, times(1)).findById(99);
    }

    @Test
    void testCreateTarea() {
        when(tareasRepository.save(any(Tareas.class))).thenReturn(tarea1);
        Tareas createdTarea = tareasService.createTarea(tarea1);
        assertNotNull(createdTarea);
        assertEquals(tarea1.getTitulo(), createdTarea.getTitulo());
        verify(tareasRepository, times(1)).save(tarea1);
    }

    @Test
    void testUpdateTarea_Exitoso() {
        Tareas tareaActualizada = new Tareas();
        tareaActualizada.setTitulo("Título actualizado");
        tareaActualizada.setEstatus(Estatus.Hecha);

        when(tareasRepository.findById(1)).thenReturn(Optional.of(tarea1));
        when(tareasRepository.save(any(Tareas.class))).thenReturn(tareaActualizada);

        Tareas resultado = tareasService.updateTarea(1, tareaActualizada);
        assertNotNull(resultado);
        assertEquals("Título actualizado", resultado.getTitulo());
        verify(tareasRepository, times(1)).findById(1);
        verify(tareasRepository, times(1)).save(tareaActualizada);
    }

    @Test
    void testUpdateTarea_NoEncontrado_LanzaExcepcion() {
        when(tareasRepository.findById(99)).thenReturn(Optional.empty());

        Exception excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            tareasService.updateTarea(99, tarea1);
        });

        String mensajeEsperado = "Tarea no encontrada con el ID: 99";
        assertTrue(excepcion.getMessage().contains(mensajeEsperado));
        verify(tareasRepository, times(1)).findById(99);
        verify(tareasRepository, never()).save(any(Tareas.class));
    }

    @Test
    void testDeleteTarea_Exitoso() {
        when(tareasRepository.existsById(1)).thenReturn(true);

        tareasService.deleteTarea(1);

        verify(tareasRepository, times(1)).existsById(1);
        verify(tareasRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteTarea_NoEncontrado_LanzaExcepcion() {
        when(tareasRepository.existsById(99)).thenReturn(false);

        Exception excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            tareasService.deleteTarea(99);
        });

        String mensajeEsperado = "Tarea no encontrada con el ID: 99";
        assertTrue(excepcion.getMessage().contains(mensajeEsperado));
        verify(tareasRepository, times(1)).existsById(99);
        verify(tareasRepository, never()).deleteById(anyInt());
    }

    @Test
    void testGetTareasByCreador() {
        when(tareasRepository.findByCreadorTarea_IdUsuario(1)).thenReturn(Arrays.asList(tarea1, tarea2));
        List<Tareas> tareas = tareasService.getTareasByCreador(1);
        assertNotNull(tareas);
        assertEquals(2, tareas.size());
        verify(tareasRepository, times(1)).findByCreadorTarea_IdUsuario(1);
    }

    @Test
    void testGetTareasByPrioridad() {
        when(tareasRepository.findByPrioridad(Prioridad.Alta)).thenReturn(Arrays.asList(tarea1));
        List<Tareas> tareas = tareasService.getTareasByPrioridad(Prioridad.Alta);
        assertNotNull(tareas);
        assertEquals(1, tareas.size());
        assertEquals(Prioridad.Alta, tareas.get(0).getPrioridad());
        verify(tareasRepository, times(1)).findByPrioridad(Prioridad.Alta);
    }
}