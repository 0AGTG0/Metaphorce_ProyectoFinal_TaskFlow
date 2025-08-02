package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.enums.Rol;
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

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        usuario.setContraseniaHash(passwordEncoder.encode(usuario.getContraseniaHash()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setIdUsuario(id);
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    @Override
    public void deleteUsuario(Integer id) {
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
}
