package com.dh.apirest_clinica.entity;
//cambiamos el nombre de la carpeta model a entity para mayor concordancia con el modelo ORM

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter  //Lombok nos da todos los getters,setter y constructores
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity  //esta clase sera una tabla
@Table(name = "domicilios") //opcional* damos un nombre personalizado a la tabla
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //describe al id como autoincremental
    private Integer id;
    private String calle;
    private int numero;
    private String localidad;
    private String provincia;

    //*Con un ORM el constructor sin id en la mayoria de casos ya no es tan necesario.
    //* no es recomendable usar el toString de Lombok ya que causa algunos bugs.
    @Override
    public String toString() {
        return "Domicilio{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }

}
