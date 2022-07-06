package ar.com.dami.odontologica.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuenciaDeDomicilio")
    @SequenceGenerator(name = "secuenciaDeDomicilio", sequenceName = "DOMICILIO_SEQUENCE", allocationSize = 1)
    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

}
