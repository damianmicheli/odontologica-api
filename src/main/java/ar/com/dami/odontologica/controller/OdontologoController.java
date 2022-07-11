package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.dto.OdontologoDTO;
import ar.com.dami.odontologica.service.ConflictoException;
import ar.com.dami.odontologica.service.IOdontologoService;
import ar.com.dami.odontologica.service.NoEncontradoException;
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

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDTO> buscar(@PathVariable Long id) throws NoEncontradoException {

        return new ResponseEntity<>(odontologoService.buscar(id), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<OdontologoDTO>> listarTodos(){

        return new ResponseEntity<>(odontologoService.listarTodos(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<OdontologoDTO> guardar(@RequestBody OdontologoDTO odontologoDTO) throws ConflictoException {

        return new ResponseEntity<>(odontologoService.guardar(odontologoDTO), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws NoEncontradoException {

        odontologoService.eliminar(id);

        return new ResponseEntity<>("El odontólogo con ID " + id + " se eliminó correctamente.", HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<OdontologoDTO> actualizar(@RequestBody OdontologoDTO odontologoDTO) throws NoEncontradoException {

        return new ResponseEntity<>(odontologoService.actualizar(odontologoDTO), HttpStatus.OK);

    }

}
