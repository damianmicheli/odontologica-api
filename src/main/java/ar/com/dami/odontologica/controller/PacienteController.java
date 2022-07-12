package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.service.ConflictoException;
import ar.com.dami.odontologica.service.DatosIncorrectosException;
import ar.com.dami.odontologica.service.IPacienteService;
import ar.com.dami.odontologica.service.NoEncontradoException;
import ar.com.dami.odontologica.util.Jsons;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Buscar un paciente por su Id")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscar(@PathVariable Long id) throws NoEncontradoException {

        return new ResponseEntity<>(pacienteService.buscar(id), HttpStatus.OK);

    }

    @Operation(summary = "Listar todos los pacientes")
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarTodos(){

        return new ResponseEntity<>(pacienteService.listarTodos(), HttpStatus.OK);

    }

    @Operation(summary = "Dar de alta un paciente")
    @PostMapping
    public ResponseEntity<PacienteDTO> guardar(@RequestBody PacienteDTO pacienteDTO) throws ConflictoException, DatosIncorrectosException {

        return new ResponseEntity<>(pacienteService.guardar(pacienteDTO), HttpStatus.OK);

    }

    @Operation(summary = "Eliminar el paciente con el Id indicado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws NoEncontradoException {

        pacienteService.eliminar(id);
        String mensaje = "El paciente con ID " + id + " se eliminó correctamente.";
        String mensajeJSON = Jsons.asJsonString(mensaje);
        return new ResponseEntity<>(mensajeJSON, HttpStatus.OK);

    }

    @Operation(summary = "Actualizar los datos de un paciente")
    @PutMapping
    public ResponseEntity<PacienteDTO> actualizar(@RequestBody PacienteDTO pacienteDTO) throws NoEncontradoException, ConflictoException {

        return new ResponseEntity<>(pacienteService.actualizar(pacienteDTO), HttpStatus.OK);

    }

}
