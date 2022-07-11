package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.OdontologoDTO;
import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.dto.TurnoDTO;
import ar.com.dami.odontologica.entity.Odontologo;
import ar.com.dami.odontologica.entity.Paciente;
import ar.com.dami.odontologica.entity.Turno;
import ar.com.dami.odontologica.repository.ITurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService{

    private final Logger logger = Logger.getLogger(OdontologoService.class);
    private final ITurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;
    private final ObjectMapper mapper;

    @Autowired
    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService, ObjectMapper mapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.mapper = mapper;
    }

    @Override
    public TurnoDTO guardar(TurnoDTO turnoDTO) throws ConflictoException, DatosIncorrectosException, NoEncontradoException {

        // Obtengo odontologo, paciente y fecha
        LocalDateTime fechaHora = turnoDTO.getFechaHora();
        Odontologo odontologo = turnoDTO.getOdontologo();
        Paciente paciente = turnoDTO.getPaciente();

        // verifico que la fecha exista
        if (fechaHora == null) {
            throw new DatosIncorrectosException("La fecha del turno no se ingresó.");
        }

        // verifico que la fecha no sea anterior a ahora
        if (fechaHora.isBefore(LocalDateTime.now())){
            throw new DatosIncorrectosException("La fecha del turno no puede ser anterior a este momento.");

        }

        //verifico que el paciente exista
        PacienteDTO pacienteEncontrado = pacienteService.buscar(paciente.getId());


        //verifico que el odontologo exista
        OdontologoDTO odontologoEncontrado = odontologoService.buscar(odontologo.getId());


        //Verifico que el odontologo no tenga un turno previo
        Optional<Turno> odontologoFechaEncontrado = turnoRepository.findByOdontologoAndFechaHora(odontologo, fechaHora);

        if (odontologoFechaEncontrado.isPresent()) {
            throw new ConflictoException("El odontólogo con Id " + odontologo.getId() + " ya tiene un turno asignado en ese horario.");
        }

        //Verifico que el paciente no tenga un turno previo
        Optional<Turno> pacienteFechaEncontrado = turnoRepository.findByPacienteAndFechaHora(paciente, fechaHora);

        if (pacienteFechaEncontrado.isPresent()) {
            throw new ConflictoException("El paciente con Id " + paciente.getId() + " ya tiene un turno asignado en ese horario.");
        }

        //Si llego hasta acá, la solicitud es correcta. Convierto el turno a DTO y lo guardo
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);

        Turno turnoRes = turnoRepository.save(turno);

        TurnoDTO turnoDTOGuardado = buscar(turnoRes.getId());

        logger.info("Se registró el siguiente turno: " + turnoDTOGuardado);

        return turnoDTOGuardado;
    }

    @Override
    public TurnoDTO buscar(Long id) throws NoEncontradoException {

        Optional<Turno> turno = turnoRepository.findById(id);

        if (turno.isEmpty()){
            throw new NoEncontradoException("Turno con Id " + id + " no encontrado.");
        }

        TurnoDTO turnoDTO = mapper.convertValue(turno, TurnoDTO.class);

        logger.info("Se buscó por Id el turno: " + turnoDTO);

        return turnoDTO;
    }

    @Override
    public List<TurnoDTO> listarTodos() {

        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> turnosDTO = new ArrayList<>();

        for (Turno turno : turnos){
            turnosDTO.add(mapper.convertValue(turno, TurnoDTO.class));
        }

        logger.info("Se listaron todos los turnos.");

        return turnosDTO;
    }

    @Override
    public void eliminar(Long id) throws NoEncontradoException {

        Optional<Turno> turno = turnoRepository.findById(id);

        if (turno.isEmpty()){
            throw new NoEncontradoException("No se puede eliminar porque no existe el Turno con Id " + id + ".");
        }

        TurnoDTO turnoDTOAEliminar = mapper.convertValue(turno,TurnoDTO.class);

        turnoRepository.deleteById(id);

        logger.info("Se  eliminó el turno: " + turnoDTOAEliminar);

    }

    @Override
    public TurnoDTO actualizar(TurnoDTO turnoDTO) throws NoEncontradoException, DatosIncorrectosException, ConflictoException {

        Optional<Turno> encontrado = turnoRepository.findById(turnoDTO.getId());

        if (encontrado.isEmpty()){
            throw new NoEncontradoException("No se puede actualizar porque no existe un turno con Id: " + turnoDTO.getId() + ".");
        }

        // Obtengo datos de paciente, odontologo y fecha.
        Odontologo odontologo = turnoDTO.getOdontologo();
        Paciente paciente = turnoDTO.getPaciente();
        LocalDateTime fechaHora = turnoDTO.getFechaHora();

        // verifico que la fecha no sea anterior a ahora
        if (fechaHora.isBefore(LocalDateTime.now())){
            throw new DatosIncorrectosException("La fecha del turno no puede ser anterior a este momento.");

        }

        //verifico que el paciente exista
        PacienteDTO pacienteEncontrado = pacienteService.buscar(paciente.getId());


        //verifico que el odontologo exista
        OdontologoDTO odontologoEncontrado = odontologoService.buscar(odontologo.getId());


        //Verifico que el odontologo no tenga un turno previo (distinto al que estoy modificando)
        Optional<Turno> odontologoFechaEncontrado = turnoRepository.findByOdontologoAndFechaHora(odontologo, fechaHora);

        if (odontologoFechaEncontrado.isPresent()){
            if(!odontologoFechaEncontrado.get().getId().equals(turnoDTO.getId())) {
                throw new ConflictoException("El odontólogo con Id " + odontologo.getId() + " ya tiene un turno asignado en ese horario.");
            }
        }

        //Verifico que el paciente no tenga un turno previo (distinto al que estoy modificando)
        Optional<Turno> pacienteFechaEncontrado = turnoRepository.findByPacienteAndFechaHora(paciente, fechaHora);

        if (pacienteFechaEncontrado.isPresent()){
            if(!pacienteFechaEncontrado.get().getId().equals(turnoDTO.getId())) {
                throw new ConflictoException("El paciente con Id " + paciente.getId() + " ya tiene un turno asignado en ese horario.");
            }
        }


        TurnoDTO turnoDTOParaActualizar = mapper.convertValue(encontrado, TurnoDTO.class);

        logger.info("Se actualizará un turno. Datos originales: " + turnoDTOParaActualizar);

        Turno turno = mapper.convertValue(turnoDTO, Turno.class);

        Turno turnoRes = turnoRepository.save(turno);

        TurnoDTO turnoDTOActualizado = mapper.convertValue(turnoRes, TurnoDTO.class);

        logger.info("Datos actuales: " + turnoDTOActualizado);

        return turnoDTOActualizado;
    }
}
