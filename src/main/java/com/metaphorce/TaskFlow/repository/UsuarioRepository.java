package com.metaphorce.TaskFlow.repository;

import com.metaphorce.TaskFlow.enums.Rol;
import com.metaphorce.TaskFlow.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByRol(Rol rol);

    Optional<Usuario> findByNombre(String nombre);
}
