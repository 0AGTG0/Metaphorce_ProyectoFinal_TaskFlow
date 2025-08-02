package com.metaphorce.TaskFlow.modelo;
import com.metaphorce.TaskFlow.enums.Estatus;
import com.metaphorce.TaskFlow.enums.Prioridad;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tareas")
public class Tareas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Integer idTarea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creador_tarea", nullable = false)
    private Usuario creadorTarea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_asignada", nullable = false)
    private Usuario usuarioAsignada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_terminada")
    private LocalDateTime fechaTerminada;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private Estatus estatus;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GestionTiempo> registrosTiempo;

    public Integer getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public Usuario getCreadorTarea() {
        return creadorTarea;
    }

    public void setCreadorTarea(Usuario creadorTarea) {
        this.creadorTarea = creadorTarea;
    }

    public Usuario getUsuarioAsignada() {
        return usuarioAsignada;
    }

    public void setUsuarioAsignada(Usuario usuarioAsignada) {
        this.usuarioAsignada = usuarioAsignada;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaTerminada() {
        return fechaTerminada;
    }

    public void setFechaTerminada(LocalDateTime fechaTerminada) {
        this.fechaTerminada = fechaTerminada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public List<GestionTiempo> getRegistrosTiempo() {
        return registrosTiempo;
    }

    public void setRegistrosTiempo(List<GestionTiempo> registrosTiempo) {
        this.registrosTiempo = registrosTiempo;
    }
}
