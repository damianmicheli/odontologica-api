package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.dto.TurnoDTO;
import ar.com.dami.odontologica.service.*;
import ar.com.dami.odontologica.util.Jsons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("turnos")
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;


    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscar(@PathVariable Long id) throws NoEncontradoException {

        return new ResponseEntity<>(turnoService.buscar(id), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTodos(){

        return new ResponseEntity<>(turnoService.listarTodos(), HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<TurnoDTO> guardar(@RequestBody TurnoDTO turnoDTO) throws NoEncontradoException, DatosIncorrectosException, ConflictoException {

        return new ResponseEntity<>(turnoService.guardar(turnoDTO), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws NoEncontradoException {

        turnoService.eliminar(id);
        String mensaje = "El turno con ID " + id + " se eliminó correctamente.";
        String mensajeJSON = Jsons.asJsonString(mensaje);
        return new ResponseEntity<>(mensajeJSON, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<TurnoDTO> actualizar (@RequestBody TurnoDTO turnoDTO) throws NoEncontradoException, DatosIncorrectosException, ConflictoException {

        return new ResponseEntity<>(turnoService.actualizar(turnoDTO),HttpStatus.OK);

    }

}


