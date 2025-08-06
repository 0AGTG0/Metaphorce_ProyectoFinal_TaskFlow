package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.enums.Rol;
import com.metaphorce.TaskFlow.exception.RecursoExistenteException;
import com.metaphorce.TaskFlow.exception.RecursoNoEncontradoException;
import com.metaphorce.TaskFlow.modelo.Usuario;
import com.metaphorce.TaskFlow.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        usuario1.setNombre("Juan Perez");
        usuario1.setCorreo("juanperez@email.com");
        usuario1.setContraseniaHash("12345");
        usuario1.setRol(Rol.Lider);

        usuario2 = new Usuario();
        usuario2.setIdUsuario(2);
        usuario2.setNombre("Maria Lopez");
        usuario2.setCorreo("marialopez@email.com");
        usuario2.setContraseniaHash("678910");
        usuario2.setRol(Rol.Miembro);
    }

    @Test
    void testGetAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testCreateUsuario_Exitoso() {
        when(usuarioRepository.findByCorreo(usuario1.getCorreo())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        Usuario usuarioCreado = usuarioService.createUsuario(usuario1);
        assertNotNull(usuarioCreado);
        assertEquals("hashedPassword", usuarioCreado.getContraseniaHash());
        verify(usuarioRepository, times(1)).findByCorreo(usuario1.getCorreo());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usuarioRepository, times(1)).save(usuario1);
    }

    @Test
    void testCreateUsuario_CorreoExistente_LanzaExcepcion() {
        when(usuarioRepository.findByCorreo(usuario1.getCorreo())).thenReturn(Optional.of(usuario1));

        Exception excepcion = assertThrows(RecursoExistenteException.class, () -> {
            usuarioService.createUsuario(usuario1);
        });

        String mensajeEsperado = "El correo electrónico ya se encuentra registrado.";
        String mensajeActual = excepcion.getMessage();

        assertTrue(mensajeActual.contains(mensajeEsperado));
        verify(usuarioRepository, times(1)).findByCorreo(usuario1.getCorreo());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testUpdateUsuario_Exitoso() {
        when(usuarioRepository.findById(usuario1.getIdUsuario())).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        Usuario usuarioActualizado = usuarioService.updateUsuario(usuario1.getIdUsuario(), usuario1);

        assertNotNull(usuarioActualizado);
        verify(usuarioRepository, times(1)).findById(usuario1.getIdUsuario());
        verify(usuarioRepository, times(1)).save(usuario1);
    }

    @Test
    void testUpdateUsuario_NoEncontrado_LanzaExcepcion() {
        when(usuarioRepository.findById(usuario1.getIdUsuario())).thenReturn(Optional.empty());

        Exception excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            usuarioService.updateUsuario(usuario1.getIdUsuario(), usuario1);
        });

        String mensajeEsperado = "Usuario no encontrado con el ID: " + usuario1.getIdUsuario();
        String mensajeActual = excepcion.getMessage();

        assertTrue(mensajeActual.contains(mensajeEsperado));
        verify(usuarioRepository, times(1)).findById(usuario1.getIdUsuario());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testDeleteById_Exitoso() {
        when(usuarioRepository.existsById(usuario1.getIdUsuario())).thenReturn(true);

        usuarioService.deleteById(usuario1.getIdUsuario());

        verify(usuarioRepository, times(1)).existsById(usuario1.getIdUsuario());
        verify(usuarioRepository, times(1)).deleteById(usuario1.getIdUsuario());
    }

    @Test
    void testDeleteById_NoEncontrado_LanzaExcepcion() {
        when(usuarioRepository.existsById(usuario1.getIdUsuario())).thenReturn(false);

        Exception excepcion = assertThrows(RecursoNoEncontradoException.class, () -> {
            usuarioService.deleteById(usuario1.getIdUsuario());
        });

        String mensajeEsperado = "Usuario no encontrado con el ID: " + usuario1.getIdUsuario();
        String mensajeActual = excepcion.getMessage();

        assertTrue(mensajeActual.contains(mensajeEsperado));
        verify(usuarioRepository, times(1)).existsById(usuario1.getIdUsuario());
        verify(usuarioRepository, never()).deleteById(anyInt());
    }
}