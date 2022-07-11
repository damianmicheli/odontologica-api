package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.entity.Odontologo;
import ar.com.dami.odontologica.entity.Paciente;
import ar.com.dami.odontologica.repository.IPacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PacienteService implements IPacienteService{

    private final Logger logger = Logger.getLogger(PacienteService.class);

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public PacienteDTO guardar(PacienteDTO pacienteDTO) throws ConflictoException, DatosIncorrectosException {

        String dni = pacienteDTO.getDni();

        Optional<Paciente> encontrado = pacienteRepository.findByDni(dni);

        if (encontrado.isPresent()){
            throw new ConflictoException("Ya existe un paciente con el DNI " + dni + ".");
        }

        if (pacienteDTO.getFechaIngreso() == null) {
            throw new DatosIncorrectosException("La fecha no es correcta.");
        }

        Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);

        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        PacienteDTO pacienteDTOGuardado = mapper.convertValue(pacienteGuardado, PacienteDTO.class);

        logger.info("Se guardó el paciente: " + pacienteDTOGuardado);

        return pacienteDTOGuardado;

    }

    public PacienteDTO buscar(Long id) throws NoEncontradoException {

        Optional<Paciente> paciente = pacienteRepository.findById(id);

        if (paciente.isEmpty()){
            throw new NoEncontradoException("Paciente con Id " + id + " no encontrado.");
        }

        PacienteDTO pacienteDTOBuscado = mapper.convertValue(paciente,PacienteDTO.class);

        logger.info("Se buscó por Id el paciente: " + pacienteDTOBuscado);

        return pacienteDTOBuscado;
    }

    @Override
    public PacienteDTO buscarPorDni(String dni) throws NoEncontradoException {

        Optional<Paciente> paciente = pacienteRepository.findByDni(dni);

        if (paciente.isEmpty()){
            throw new NoEncontradoException("Paciente con DNI " + dni + " no encontrado.");
        }

        PacienteDTO pacienteDTOBuscado = mapper.convertValue(paciente,PacienteDTO.class);

        logger.info("Se buscó por DNI el paciente: " + pacienteDTOBuscado);

        return pacienteDTOBuscado;
    }

    @Override
    public List<PacienteDTO> listarTodos() {

        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDTO> pacientesDTO = new ArrayList<>();

        for (Paciente paciente : pacientes){
            pacientesDTO.add(mapper.convertValue(paciente, PacienteDTO.class));
        }

        logger.info("Se listaron todos los pacientes.");

        return pacientesDTO;
    }

    @Override
    public void eliminar(Long id) throws NoEncontradoException {

        Optional<Paciente> paciente = pacienteRepository.findById(id);

        if (paciente.isEmpty()){
            throw new NoEncontradoException("No se puede eliminar porque no existe el Paciente con Id " + id + ".");
        }

        PacienteDTO pacienteDTOAEliminar = mapper.convertValue(paciente,PacienteDTO.class);

        pacienteRepository.deleteById(id);

        logger.info("Se eliminó el paciente: " + pacienteDTOAEliminar);

    }

    @Override
    public PacienteDTO actualizar(PacienteDTO pacienteDTO) throws NoEncontradoException, ConflictoException {

        Long id = pacienteDTO.getId();

        Optional<Paciente> encontrado = pacienteRepository.findById(id);

        if (encontrado.isEmpty()){
            throw new NoEncontradoException("No se puede actualizar porque no existe un paciente con Id: " + id + ".");
        }

        String dni = pacienteDTO.getDni();
        Optional<Paciente> encontradoDni = pacienteRepository.findByDni(dni);

        if (encontradoDni.isPresent() && !encontradoDni.get().getId().equals(id) ){
            throw new ConflictoException("Ya existe otro paciente con el DNI " + dni + ".");
        }


        PacienteDTO pacienteDTOParaActualizar = mapper.convertValue(encontrado,PacienteDTO.class);

        logger.info("Se actualizará un paciente. Datos originales: " + pacienteDTOParaActualizar);

        Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);

        Paciente pacienteRes = pacienteRepository.save(paciente);

        PacienteDTO pacienteDTOActualizado = mapper.convertValue(pacienteRes,PacienteDTO.class);

        logger.info("Datos actuales: " + pacienteDTOActualizado);


        return pacienteDTOActualizado;
    }
}
