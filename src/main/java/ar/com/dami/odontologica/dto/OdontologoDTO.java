package ar.com.dami.odontologica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OdontologoDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;

}
