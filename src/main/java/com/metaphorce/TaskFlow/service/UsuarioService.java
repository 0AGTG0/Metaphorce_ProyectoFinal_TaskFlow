package com.metaphorce.TaskFlow.service;

import com.metaphorce.TaskFlow.enums.Rol;
import com.metaphorce.TaskFlow.modelo.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();
    Optional<Usuario> getUsuarioById(Integer id);
    Usuario createUsuario(Usuario usuario);
    Usuario updateUsuario(Integer id, Usuario usuario);
    void deleteUsuario(Integer id);

    Optional<Usuario> getUsuarioByCorreo(String correo);
    List<Usuario> getUsuariosByRol(Rol rol);
    Optional<Usuario> getUsuarioByNombre(String nombre);
}
