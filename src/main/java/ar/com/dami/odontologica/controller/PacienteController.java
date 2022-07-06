package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.service.IPacienteService;
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
    public ResponseEntity<PacienteDTO> buscar(@PathVariable Long id){

        ResponseEntity<PacienteDTO> response;

        PacienteDTO encontrado = pacienteService.buscar(id);

        if ( encontrado == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(encontrado, HttpStatus.OK);
        }

        return response;
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarTodos(){

        ResponseEntity<List<PacienteDTO>> response;

        List<PacienteDTO> pacientes = pacienteService.listarTodos();

        response = new ResponseEntity<>(pacientes, HttpStatus.OK);

        return response;
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> guardar(@RequestBody PacienteDTO pacienteDTO){

        ResponseEntity<PacienteDTO> response;

        PacienteDTO guardado = pacienteService.guardar(pacienteDTO);

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

        if (pacienteService.buscar(id) == null){
            response = new ResponseEntity<>("Paciente no encontrado.", HttpStatus.NOT_FOUND);
        } else {
            pacienteService.eliminar(id);
            response = new ResponseEntity<>("El paciente con ID " + id + " se eliminó correctamente.", HttpStatus.OK);

        }

        return response;
    }

    @PutMapping
    public ResponseEntity<PacienteDTO> actualizar(@RequestBody PacienteDTO pacienteDTO) {

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
