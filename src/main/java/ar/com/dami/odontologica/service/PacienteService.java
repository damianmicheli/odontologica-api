package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.entity.Paciente;
import ar.com.dami.odontologica.repository.IPacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PacienteService implements IPacienteService{

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public PacienteDTO guardar(PacienteDTO pacienteDTO) {

        Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);

        Paciente pacienteRes = pacienteRepository.save(paciente);

        return mapper.convertValue(pacienteRes,PacienteDTO.class);
    }

    @Override
    public PacienteDTO buscar(Long id) {

        PacienteDTO pacienteDTO = null;
        Optional<Paciente> paciente = pacienteRepository.findById(id);

        if (paciente.isPresent()){
            pacienteDTO = mapper.convertValue(paciente, PacienteDTO.class);
        }

        return pacienteDTO;
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
