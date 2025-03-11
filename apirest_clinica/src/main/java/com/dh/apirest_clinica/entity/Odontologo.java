package com.dh.apirest_clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String matricula;
    private String apellido;
    private String nombre;

    @OneToMany(mappedBy = "odontologo") //definimos que un odon. maneja muchos turnos
    @JsonManagedReference(value = "odontologo-turno") //1era forma de manejar que el json no se haga ciclico, reduciendo la visibilidad en Turnos
    //@JsonIgnore //2da forma solo se usa desde la parte de 1
    private Set<Turno> turnoSet;


    //to string
    @Override
    public String toString() {
        return "Odontologo{" +
                "id=" + id +
                ", matricula=" + matricula +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
