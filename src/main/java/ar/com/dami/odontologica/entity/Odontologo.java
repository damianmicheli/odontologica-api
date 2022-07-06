package ar.com.dami.odontologica.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Table
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuenciaDeOdontologo")
    @SequenceGenerator(name = "secuenciaDeOdontologo", sequenceName = "ODONTOLOGO_SEQUENCE", allocationSize = 1)
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;

    @OneToMany(mappedBy = "odontologo")
    @JsonIgnore
    private List<Turno> turnos;

}
