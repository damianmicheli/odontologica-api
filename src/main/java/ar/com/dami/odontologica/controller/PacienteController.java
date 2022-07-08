package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.service.ConflictoException;
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

        ResponseEntity<List<PacienteDTO>> response;

        List<PacienteDTO> pacientes = pacienteService.listarTodos();

        response = new ResponseEntity<>(pacientes, HttpStatus.OK);

        return response;
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> guardar(@RequestBody PacienteDTO pacienteDTO) throws ConflictoException {

        return new ResponseEntity<>(pacienteService.guardar(pacienteDTO), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws NoEncontradoException {

        ResponseEntity<String> response;

        if (pacienteService.buscar(id) == null){
            response = new ResponseEntity<>("Paciente no encontrado.", HttpStatus.NOT_FOUND);
        } else {
            pacienteService.eliminar(id);
            response = new ResponseEntity<>("El paciente con ID " + id + " se eliminó correctamente.", HttpStatus.OK);

        }

        return response;
    }

    @PutMapping
    public ResponseEntity<PacienteDTO> actualizar(@RequestBody PacienteDTO pacienteDTO) throws NoEncontradoException {

        ResponseEntity<PacienteDTO> response;
        Long id = pacienteDTO.getId();

        if (pacienteService.buscar(id) == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(pacienteService.actualizar(pacienteDTO), HttpStatus.OK);
        }

        return response;
    }

}
