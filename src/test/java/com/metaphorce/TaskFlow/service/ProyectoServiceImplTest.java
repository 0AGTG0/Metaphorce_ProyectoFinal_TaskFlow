package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.exception.RecursoNoEncontradoException;
import com.metaphorce.TaskFlow.modelo.Proyecto;
import com.metaphorce.TaskFlow.modelo.Usuario;
import com.metaphorce.TaskFlow.repository.ProyectoRepository;
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
public class ProyectoServiceImplTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private ProyectoServiceImpl proyectoService;

    private Proyecto proyecto1;
    private Proyecto proyecto2;
    private Usuario lider;

    @BeforeEach
    void setUp() {
        lider = new Usuario();
        lider.setIdUsuario(1);

        proyecto1 = new Proyecto();
        proyecto1.setIdProyecto(1);
        proyecto1.setTitulo("Proyecto Alpha");
        proyecto1.setDescripcion("Descripción de Alpha");
        proyecto1.setFechaInicio(LocalDateTime.now());
        proyecto1.setUsuario(lider);

        proyecto2 = new Proyecto();
        proyecto2.setIdProyecto(2);
        proyecto2.setTitulo("Proyecto Beta");
        proyecto2.setDescripcion("Descripción de Beta");
        proyecto2.setFechaInicio(LocalDateTime.now());
        proyecto2.setUsuario(lider);
    }

    @Test
    void testGetAllProyectos() {
        when(proyectoRepository.findAll()).thenReturn(Arrays.asList(proyecto1, proyecto2));
        List<Proyecto> proyectos = proyectoService.getAllProyectos();
        assertNotNull(proyectos);
        assertEquals(2, proyectos.size());
        verify(proyectoRepository, times(1)).findAll();
    }

    @Test
    void testGetProyectoById_Exitoso() {
        when(proyectoRepository.findById(1)).thenReturn(Optional.of(proyecto1));
        Optional<Proyecto> foundProyecto = proyectoService.getProyectoById(1);
        assertTrue(foundProyecto.isPresent());
        assertEquals(proyecto1, foundProyecto.get());
        verify(proyectoRepository, times(1)).findById(1);
    }

    @Test
    void testGetProyectoById_NoEncontrado() {
        when(proyectoRepository.findById(99)).thenReturn(Optional.empty());
        Optional<Proyecto> foundProyecto = proyectoService.getProyectoById(99);
        assertFalse(foundProyecto.isPresent());
        verify(proyectoRepository, times(1)).findById(99);
    }

    @Test
    void testCreateProyecto() {
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto1);
        Proyecto createdProyecto = proyectoService.createProyecto(proyecto1);
        assertNotNull(createdProyecto);
        assertEquals(proyecto1.getTitulo(), createdProyecto.getTitulo());
        verify(proyectoRepository, times(1)).save(proyecto1);
    }

    @Test
    void testUpdateProyecto_Exitoso() {
        Proyecto proyectoActualizado = new Proyecto();
        proyectoActualizado.setTitulo("Proyecto Alpha V2");
        proyectoActualizado.setDescripcion("Descripción actualizada");

        when(proyectoRepository.findById(1)).thenReturn(Optional.of(proyecto1));
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyectoActualizado);

        Proyecto resultado = proyectoService.updateProyecto(1, proyectoActualizado);
        assertNotNull(resultado);
        assertEquals("Proyecto Alpha V2", resultado.getTitulo());
        verify(proyectoRepository, times(1)).findById(1);
        verify(proyectoRepository, times(1)).save(proyectoActualizado);
    }

    @Test
    void testUpdateProyecto_NoEncontrado_LanzaExcepcion() {
        when(proyectoRepository.findById(99)).thenReturn(Optional.empty());

        Exception excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            proyectoService.updateProyecto(99, proyecto1);
        });

        String mensajeEsperado = "Proyecto no encontrado con el ID: 99";
        assertTrue(excepcion.getMessage().contains(mensajeEsperado));
        verify(proyectoRepository, times(1)).findById(99);
        verify(proyectoRepository, never()).save(any(Proyecto.class));
    }

    @Test
    void testDeleteProyecto_Exitoso() {
        when(proyectoRepository.existsById(1)).thenReturn(true);

        proyectoService.deleteProyecto(1);

        verify(proyectoRepository, times(1)).existsById(1);
        verify(proyectoRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteProyecto_NoEncontrado_LanzaExcepcion() {
        when(proyectoRepository.existsById(99)).thenReturn(false);

        Exception excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            proyectoService.deleteProyecto(99);
        });

        String mensajeEsperado = "Proyecto no encontrado con el ID: 99";
        assertTrue(excepcion.getMessage().contains(mensajeEsperado));
        verify(proyectoRepository, times(1)).existsById(99);
        verify(proyectoRepository, never()).deleteById(anyInt());
    }
}