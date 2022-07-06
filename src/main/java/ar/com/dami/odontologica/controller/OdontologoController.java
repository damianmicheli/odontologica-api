package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.dto.OdontologoDTO;
import ar.com.dami.odontologica.service.IOdontologoService;
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
    public ResponseEntity<OdontologoDTO> buscar(@PathVariable Long id){

        ResponseEntity<OdontologoDTO> response;

        OdontologoDTO encontrado = odontologoService.buscar(id);

        if ( encontrado == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(encontrado, HttpStatus.OK);
        }

        return response;
    }

    @GetMapping
    public ResponseEntity<List<OdontologoDTO>> listarTodos(){

        ResponseEntity<List<OdontologoDTO>> response;

        List<OdontologoDTO> odontologos = odontologoService.listarTodos();

        response = new ResponseEntity<>(odontologos, HttpStatus.OK);

        return response;
    }

    @PostMapping
    public ResponseEntity<OdontologoDTO> guardar(@RequestBody OdontologoDTO odontologoDTO){

        ResponseEntity<OdontologoDTO> response;

        OdontologoDTO guardado = odontologoService.guardar(odontologoDTO);

        if ( guardado == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            response = new ResponseEntity<>(guardado, HttpStatus.OK);
        }

        return response;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        ResponseEntity<String> response;

        if (odontologoService.buscar(id) == null){
            response = new ResponseEntity<>("Odontólogo no encontrado.", HttpStatus.NOT_FOUND);
        } else {
            odontologoService.eliminar(id);
            response = new ResponseEntity<>("El odontólogo con ID " + id + " se eliminó correctamente.", HttpStatus.OK);

        }

        return response;
    }

    @PutMapping
    public ResponseEntity<OdontologoDTO> actualizar(@RequestBody OdontologoDTO odontologoDTO) {

        ResponseEntity<OdontologoDTO> response;
        Long id = odontologoDTO.getId();

        if (odontologoService.buscar(id) == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(odontologoService.actualizar(odontologoDTO), HttpStatus.OK);
        }

        return response;
    }

}
