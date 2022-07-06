package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.TurnoDTO;
import ar.com.dami.odontologica.entity.Turno;
import ar.com.dami.odontologica.repository.ITurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService{

    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public TurnoDTO guardar(TurnoDTO turnoDTO) {

        Turno turno = mapper.convertValue(turnoDTO, Turno.class);

        Turno turnoRes = turnoRepository.save(turno);

        return mapper.convertValue(turnoRes,TurnoDTO.class);
    }

    @Override
    public TurnoDTO buscar(Long id) {

        TurnoDTO turnoDTO = null;
        Optional<Turno> turno = turnoRepository.findById(id);

        if (turno.isPresent()){
            turnoDTO = mapper.convertValue(turno, TurnoDTO.class);
        }

        return turnoDTO;
    }

    @Override
    public List<TurnoDTO> listarTodos() {

        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> turnosDTO = new ArrayList<>();

        for (Turno turno : turnos){
            turnosDTO.add(mapper.convertValue(turno, TurnoDTO.class));
        }

        return turnosDTO;
    }

    @Override
    public void eliminar(Long id) {
        turnoRepository.deleteById(id);
    }

    @Override
    public TurnoDTO actualizar(TurnoDTO turnoDTO) {
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);

        Turno turnoRes = turnoRepository.save(turno);

        return mapper.convertValue(turnoRes,TurnoDTO.class);
    }
}
