package com.dh.apirest_clinica.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne //varios turnos pertenecen a un mismo paciente y odontologo
    @JsonBackReference(value = "paciente-turno")//oculta la parte de paciente-turnos cuando se visualiza el turno
    private Paciente paciente;
    @ManyToOne
    @JsonBackReference(value = "odontologo-turno") //1era forma de manejar que el json no se haga ciclico, reduciendo la visibilidad en Turnos
    private Odontologo odontologo;
    private LocalDate fecha;

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", odontologo=" + odontologo +
                ", fecha=" + fecha +
                '}';
    }
}
