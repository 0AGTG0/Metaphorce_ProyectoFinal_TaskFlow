create database TaskFlow;

use TaskFlow;

create table if not exists usuario (
    id_usuario INT AUTO_INCREMENT,
    nombre VARCHAR(50),
    contrasenia_hash VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    rol enum('Lider','Gerente','Miembro') NOT NULL,
    constraint usuario_id_usuario_pk primary key(id_usuario)
);

create table if not exists proyecto(
	id_proyecto int auto_increment,
    id_usuario int not null,
    titulo varchar(50) not null,
    descripcion text not null,
    fecha_inicio datetime not null default current_timestamp,
    fecha_fin datetime null, -- para que puedan cambiarlo cuando termine el proyecto
    constraint proyecto_id_proyecto_pk primary key(id_proyecto),
    constraint proyecto_id_usuario_pk foreign key(id_usuario) references usuario(id_usuario)
);

create table if not exists tareas(
	id_tarea int auto_increment,
    id_usuario int not null,
    id_proyecto int not null,
    titulo varchar(50)not null,
    descripcion text not null,
    prioridad enum('Alta','Media','Baja') not null,
);

create table if not exists gestion_tiempo(
	
);