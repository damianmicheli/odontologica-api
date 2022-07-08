package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.OdontologoDTO;
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

    private final Logger logger = Logger.getLogger(OdontologoService.class);

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public PacienteDTO guardar(PacienteDTO pacienteDTO) throws ConflictoException {

        String dni = pacienteDTO.getDni();

        Optional<Paciente> encontrado = pacienteRepository.findByDni(dni);

        if (encontrado.isPresent()){
            throw new ConflictoException("Ya existe un paciente con el DNI " + dni + ".");
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

        return mapper.convertValue(paciente, PacienteDTO.class);
    }

    public PacienteDTO buscarPorDni(String dni) throws NoEncontradoException {

        Optional<Paciente> paciente = pacienteRepository.findByDni(dni);

        if (paciente.isEmpty()){
            throw new NoEncontradoException("Paciente con DNI " + dni + " no encontrado.");
        }

        return mapper.convertValue(paciente, PacienteDTO.class);
    }

    @Override
    public List<PacienteDTO> listarTodos() {

        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDTO> pacientesDTO = new ArrayList<>();

        for (Paciente paciente : pacientes){
            pacientesDTO.add(mapper.convertValue(paciente, PacienteDTO.class));
        }

        return pacientesDTO;
    }

    @Override
    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public PacienteDTO actualizar(PacienteDTO pacienteDTO) {
        Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);

        Paciente pacienteRes = pacienteRepository.save(paciente);

        return mapper.convertValue(pacienteRes,PacienteDTO.class);
    }
}
