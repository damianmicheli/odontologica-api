package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.service.ConflictoException;
import ar.com.dami.odontologica.service.DatosIncorrectosException;
import ar.com.dami.odontologica.service.IPacienteService;
import ar.com.dami.odontologica.service.NoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private IPacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscar(@PathVariable Long id) throws NoEncontradoException {

        return new ResponseEntity<>(pacienteService.buscar(id), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarTodos(){

        return new ResponseEntity<>(pacienteService.listarTodos(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<PacienteDTO> guardar(@RequestBody PacienteDTO pacienteDTO) throws ConflictoException, DatosIncorrectosException {

        return new ResponseEntity<>(pacienteService.guardar(pacienteDTO), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws NoEncontradoException {

        pacienteService.eliminar(id);

        return new ResponseEntity<>("El paciente con ID " + id + " se eliminó correctamente.", HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<PacienteDTO> actualizar(@RequestBody PacienteDTO pacienteDTO) throws NoEncontradoException {

        return new ResponseEntity<>(pacienteService.actualizar(pacienteDTO), HttpStatus.OK);

    }

}
