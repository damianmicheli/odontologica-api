package ar.com.dami.odontologica.dto;

import ar.com.dami.odontologica.entity.Domicilio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteDTO {

    private Long id;
    private String apellido;
    private String nombre;
    private String dni;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;

}
