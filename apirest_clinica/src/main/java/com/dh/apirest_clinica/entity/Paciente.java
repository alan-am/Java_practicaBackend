package com.dh.apirest_clinica.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String apellido;
    private String nombre;
    private String dni;
    private LocalDate fechaIngreso;
    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)  //relacion uno a uno - unidireccional, pq domicilio no tiene especificado nada respecto a paciente
    private Domicilio domicilio;
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.REMOVE) //esta relacionado con la columna paciente de Turno, y propaga la eliminacion de un paciente y sus turnos.
    @JsonManagedReference(value = "paciente-turno")
    //@JsonIgnore
    private Set<Turno> turnoSet;


    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", domicilio=" + domicilio +
                '}';
    }
}
