package com.metaphorce.TaskFlow.modelo;
import com.metaphorce.TaskFlow.enums.Rol;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "contrasenia_hash", nullable = false)
    private String contraseniaHash;

    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Proyecto> proyectosGestionados;

    @OneToMany(mappedBy = "creadorTarea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tareas> tareasCreadas;

    @OneToMany(mappedBy = "usuarioAsignada", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tareas> tareasAsignadas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GestionTiempo> registrosTiempo;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseniaHash() {
        return contraseniaHash;
    }

    public void setContraseniaHash(String contraseniaHash) {
        this.contraseniaHash = contraseniaHash;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Proyecto> getProyectosGestionados() {
        return proyectosGestionados;
    }

    public void setProyectosGestionados(List<Proyecto> proyectosGestionados) {
        this.proyectosGestionados = proyectosGestionados;
    }

    public List<Tareas> getTareasCreadas() {
        return tareasCreadas;
    }

    public void setTareasCreadas(List<Tareas> tareasCreadas) {
        this.tareasCreadas = tareasCreadas;
    }

    public List<Tareas> getTareasAsignadas() {
        return tareasAsignadas;
    }

    public void setTareasAsignadas(List<Tareas> tareasAsignadas) {
        this.tareasAsignadas = tareasAsignadas;
    }

    public List<GestionTiempo> getRegistrosTiempo() {
        return registrosTiempo;
    }

    public void setRegistrosTiempo(List<GestionTiempo> registrosTiempo) {
        this.registrosTiempo = registrosTiempo;
    }
}