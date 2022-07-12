package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.dto.OdontologoDTO;
import ar.com.dami.odontologica.service.ConflictoException;
import ar.com.dami.odontologica.service.IOdontologoService;
import ar.com.dami.odontologica.service.NoEncontradoException;
import ar.com.dami.odontologica.util.Jsons;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("odontologos")
public class OdontologoController {

    @Autowired
    private IOdontologoService odontologoService;

    @Operation(summary = "Buscar un odontólogo por su Id")
    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDTO> buscar(@PathVariable Long id) throws NoEncontradoException {

        return new ResponseEntity<>(odontologoService.buscar(id), HttpStatus.OK);

    }

    @Operation(summary = "Listar todos los odontólogos")
    @GetMapping
    public ResponseEntity<List<OdontologoDTO>> listarTodos(){

        return new ResponseEntity<>(odontologoService.listarTodos(), HttpStatus.OK);

    }

    @Operation(summary = "Dar de alta un odontólogo")
    @PostMapping
    public ResponseEntity<OdontologoDTO> guardar(@RequestBody OdontologoDTO odontologoDTO) throws ConflictoException {

        return new ResponseEntity<>(odontologoService.guardar(odontologoDTO), HttpStatus.OK);

    }

    @Operation(summary = "Eliminar el odontólogo con el Id indicado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws NoEncontradoException {

        odontologoService.eliminar(id);
        String mensaje = "El odontólogo con ID " + id + " se eliminó correctamente.";
        String mensajeJSON = Jsons.asJsonString(mensaje);
        return new ResponseEntity<>(mensajeJSON, HttpStatus.OK);

    }

    @Operation(summary = "Actualizar los datos de un odontólogo")
    @PutMapping
    public ResponseEntity<OdontologoDTO> actualizar(@RequestBody OdontologoDTO odontologoDTO) throws NoEncontradoException, ConflictoException {

        return new ResponseEntity<>(odontologoService.actualizar(odontologoDTO), HttpStatus.OK);

    }

}
