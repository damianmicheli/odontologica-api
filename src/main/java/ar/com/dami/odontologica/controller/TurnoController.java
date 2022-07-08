package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.dto.TurnoDTO;
import ar.com.dami.odontologica.service.IOdontologoService;
import ar.com.dami.odontologica.service.IPacienteService;
import ar.com.dami.odontologica.service.ITurnoService;
import ar.com.dami.odontologica.service.NoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("turnos")
public class TurnoController {

    private final ITurnoService turnoService;
    private final IOdontologoService odontologoService;
    private final IPacienteService pacienteService;

    @Autowired
    public TurnoController(ITurnoService turnoService, IOdontologoService odontologoService, IPacienteService pacienteService) {
        this.turnoService = turnoService;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscar(@PathVariable Long id){

        ResponseEntity<TurnoDTO> response;

        TurnoDTO encontrado = turnoService.buscar(id);

        if (encontrado == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(encontrado, HttpStatus.OK);
        }

        return response;
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTodos(){

        ResponseEntity<List<TurnoDTO>> response;

        List<TurnoDTO> turnos = turnoService.listarTodos();

        response = new ResponseEntity<>(turnos, HttpStatus.OK);

        return response;
    }


    @PostMapping
    public ResponseEntity<TurnoDTO> guardar(@RequestBody TurnoDTO turnoDTO) throws NoEncontradoException {

        ResponseEntity<TurnoDTO> response;
        Long odontologoId = turnoDTO.getOdontologo().getId();
        Long pacienteId = turnoDTO.getPaciente().getId();

        if (odontologoService.buscar(odontologoId) == null
                || pacienteService.buscar(pacienteId) == null
                || turnoDTO.getFechaHora() == null){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            response = new ResponseEntity<>(turnoService.guardar(turnoDTO), HttpStatus.OK);
        }

        return response;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        ResponseEntity<String> response;

        if (turnoService.buscar(id) == null){
            response = new ResponseEntity<>("Turno no encontrado.", HttpStatus.NOT_FOUND);
        } else {
            turnoService.eliminar(id);
            response = new ResponseEntity<>("El turno con ID " + id + " se eliminó correctamente.", HttpStatus.OK);

        }
        return response;
    }

    @PutMapping
    public ResponseEntity<TurnoDTO> actualizar (@RequestBody TurnoDTO turnoDTO) throws NoEncontradoException {

        ResponseEntity<TurnoDTO> response;
        Long odontologoId = turnoDTO.getOdontologo().getId();
        Long pacienteId = turnoDTO.getPaciente().getId();

        if (turnoService.buscar(turnoDTO.getId()) == null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (odontologoService.buscar(odontologoId) == null
                || pacienteService.buscar(pacienteId) == null
                || turnoDTO.getFechaHora() == null){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            response = new ResponseEntity<>(turnoService.actualizar(turnoDTO),HttpStatus.OK);
        }

        return response;
    }

}


