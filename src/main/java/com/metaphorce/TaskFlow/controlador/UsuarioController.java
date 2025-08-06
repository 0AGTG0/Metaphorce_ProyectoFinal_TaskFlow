package com.metaphorce.TaskFlow.controlador;

import com.metaphorce.TaskFlow.enums.Rol;
import com.metaphorce.TaskFlow.exception.RecursoNoEncontradoException;
import com.metaphorce.TaskFlow.modelo.Usuario;
import com.metaphorce.TaskFlow.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/TaskFlow/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Registrar nuevos usuarios
    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        // Validación de campos nulos o vacíos
        if (usuario.getNombre() == null || usuario.getNombre().isBlank() || usuario.getNombre().isEmpty() ||
                usuario.getContraseniaHash() == null || usuario.getContraseniaHash().isBlank() || usuario.getContraseniaHash().isEmpty() ||
                usuario.getCorreo() == null || usuario.getCorreo().isBlank() || usuario.getCorreo().isEmpty() ||
                usuario.getRol() == null) {
            return new ResponseEntity<>("No puede dejar ningún campo en blanco", HttpStatus.BAD_REQUEST);
        }

        // El GlobalExceptionHandler manejara excepciones genericas
            Usuario nuevoUsuario = usuarioService.createUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        // Si no se encuentra, la excepción se lanzará en el servicio, no aquí
        return ResponseEntity.ok(usuario.orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con el ID: " + id)));
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        // El servicio se encargará de lanzar la excepción si el usuario no existe
        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario);
        return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
    }

    // Eliminar un usuario por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener un usuario por su correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> getUsuarioByCorreo(@PathVariable String correo) {
        Optional<Usuario> usuario = usuarioService.getUsuarioByCorreo(correo);
        return ResponseEntity.ok(usuario.orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con el correo: " + correo)));
    }

    // Obtener usuarios por su rol
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> getUsuariosByRol(@PathVariable Rol rol) {
        List<Usuario> usuarios = usuarioService.getUsuariosByRol(rol);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Obtener un usuario por su nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Usuario> getUsuarioByNombre(@PathVariable String nombre) {
        Optional<Usuario> usuario = usuarioService.getUsuarioByNombre(nombre);
        return ResponseEntity.ok(usuario.orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con el nombre: " + nombre)));
    }
}
