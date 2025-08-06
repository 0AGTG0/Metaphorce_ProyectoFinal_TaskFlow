package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.enums.Rol;
import com.metaphorce.TaskFlow.exception.RecursoExistenteException;
import com.metaphorce.TaskFlow.exception.RecursoNoEncontradoException;
import com.metaphorce.TaskFlow.modelo.Usuario;
import com.metaphorce.TaskFlow.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario createUsuario(Usuario usuario) {
        // Verificar si el correo ya existe
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (usuarioExistente.isPresent()) {
            throw new RecursoExistenteException("El correo electrónico ya se encuentra registrado.");
        }

        // Hashear la contraseña antes de guardarla
        String contraseniaHash = passwordEncoder.encode(usuario.getContraseniaHash());
        usuario.setContraseniaHash(contraseniaHash);

        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario) {
        // Buscamos el usuario y si no existe, lanzamos la excepción
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuario.setIdUsuario(id);
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con el ID: " + id));
    }

    @Override
    public void deleteById(Integer id) {
        // Verificamos si el usuario existe antes de intentar eliminarlo
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con el ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    public List<Usuario> getUsuariosByRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    @Override
    public Optional<Usuario> getUsuarioByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }
}
