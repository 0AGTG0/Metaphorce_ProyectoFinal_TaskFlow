package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.exception.RecursoNoEncontradoException;
import com.metaphorce.TaskFlow.modelo.GestionTiempo;
import com.metaphorce.TaskFlow.modelo.Tareas;
import com.metaphorce.TaskFlow.modelo.Usuario;
import com.metaphorce.TaskFlow.repository.GestionTiempoRepository;
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
public class GestionTiempoServiceImplTest {

    @Mock
    private GestionTiempoRepository gestionTiempoRepository;

    @InjectMocks
    private GestionTiempoServiceImpl gestionTiempoService;

    private GestionTiempo gestionTiempo1;
    private GestionTiempo gestionTiempo2;
    private Usuario usuario;
    private Tareas tarea;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1);

        tarea = new Tareas();
        tarea.setIdTarea(10);

        gestionTiempo1 = new GestionTiempo();
        gestionTiempo1.setIdGestion(1);
        gestionTiempo1.setFechaInicio(LocalDateTime.now().minusHours(2));
        gestionTiempo1.setFechaFin(LocalDateTime.now().minusHours(1));
        gestionTiempo1.setUsuario(usuario);
        gestionTiempo1.setTarea(tarea);

        gestionTiempo2 = new GestionTiempo();
        gestionTiempo2.setIdGestion(2);
        gestionTiempo2.setFechaInicio(LocalDateTime.now().minusHours(4));
        gestionTiempo2.setFechaFin(LocalDateTime.now().minusHours(3));
        gestionTiempo2.setUsuario(usuario);
        gestionTiempo2.setTarea(tarea);
    }

    @Test
    void testGetAllGestionTiempo() {
        when(gestionTiempoRepository.findAll()).thenReturn(Arrays.asList(gestionTiempo1, gestionTiempo2));
        List<GestionTiempo> gestionTiempoList = gestionTiempoService.getAllGestionTiempo();
        assertNotNull(gestionTiempoList);
        assertEquals(2, gestionTiempoList.size());
        verify(gestionTiempoRepository, times(1)).findAll();
    }

    @Test
    void testGetGestionTiempoById_Exitoso() {
        when(gestionTiempoRepository.findById(1)).thenReturn(Optional.of(gestionTiempo1));
        Optional<GestionTiempo> foundGestionTiempo = gestionTiempoService.getGestionTiempoById(1);
        assertTrue(foundGestionTiempo.isPresent());
        assertEquals(gestionTiempo1, foundGestionTiempo.get());
        verify(gestionTiempoRepository, times(1)).findById(1);
    }

    @Test
    void testGetGestionTiempoById_NoEncontrado() {
        when(gestionTiempoRepository.findById(99)).thenReturn(Optional.empty());
        Optional<GestionTiempo> foundGestionTiempo = gestionTiempoService.getGestionTiempoById(99);
        assertFalse(foundGestionTiempo.isPresent());
        verify(gestionTiempoRepository, times(1)).findById(99);
    }

    @Test
    void testCreateGestionTiempo() {
        when(gestionTiempoRepository.save(any(GestionTiempo.class))).thenReturn(gestionTiempo1);
        GestionTiempo createdGestionTiempo = gestionTiempoService.createGestionTiempo(gestionTiempo1);
        assertNotNull(createdGestionTiempo);
        assertEquals(gestionTiempo1.getIdGestion(), createdGestionTiempo.getIdGestion());
        verify(gestionTiempoRepository, times(1)).save(gestionTiempo1);
    }

    @Test
    void testUpdateGestionTiempo_Exitoso() {
        GestionTiempo gestionTiempoActualizada = new GestionTiempo();
        gestionTiempoActualizada.setFechaFin(LocalDateTime.now());

        when(gestionTiempoRepository.findById(1)).thenReturn(Optional.of(gestionTiempo1));
        when(gestionTiempoRepository.save(any(GestionTiempo.class))).thenReturn(gestionTiempoActualizada);

        GestionTiempo resultado = gestionTiempoService.updateGestionTiempo(1, gestionTiempoActualizada);
        assertNotNull(resultado);
        assertEquals(gestionTiempoActualizada.getFechaFin(), resultado.getFechaFin());
        verify(gestionTiempoRepository, times(1)).findById(1);
        verify(gestionTiempoRepository, times(1)).save(gestionTiempoActualizada);
    }

    @Test
    void testUpdateGestionTiempo_NoEncontrado_LanzaExcepcion() {
        when(gestionTiempoRepository.findById(99)).thenReturn(Optional.empty());

        Exception excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            gestionTiempoService.updateGestionTiempo(99, gestionTiempo1);
        });

        String mensajeEsperado = "Registro de gestión de tiempo no encontrado con el ID: 99";
        assertTrue(excepcion.getMessage().contains(mensajeEsperado));
        verify(gestionTiempoRepository, times(1)).findById(99);
        verify(gestionTiempoRepository, never()).save(any(GestionTiempo.class));
    }

    @Test
    void testDeleteGestionTiempo_Exitoso() {
        when(gestionTiempoRepository.existsById(1)).thenReturn(true);

        gestionTiempoService.deleteGestionTiempo(1);

        verify(gestionTiempoRepository, times(1)).existsById(1);
        verify(gestionTiempoRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteGestionTiempo_NoEncontrado_LanzaExcepcion() {
        when(gestionTiempoRepository.existsById(99)).thenReturn(false);

        Exception excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            gestionTiempoService.deleteGestionTiempo(99);
        });

        String mensajeEsperado = "Registro de gestión de tiempo no encontrado con el ID: 99";
        assertTrue(excepcion.getMessage().contains(mensajeEsperado));
        verify(gestionTiempoRepository, times(1)).existsById(99);
        verify(gestionTiempoRepository, never()).deleteById(anyInt());
    }

    @Test
    void testGetDuracionTotalSesion() {
        when(gestionTiempoRepository.sumDuracionSesionByUsuarioAndTarea(1, 10)).thenReturn(3600); // 3600 segundos = 1 hora
        Integer duracion = gestionTiempoService.getDuracionTotalSesion(1, 10);
        assertNotNull(duracion);
        assertEquals(3600, duracion);
        verify(gestionTiempoRepository, times(1)).sumDuracionSesionByUsuarioAndTarea(1, 10);
    }
}