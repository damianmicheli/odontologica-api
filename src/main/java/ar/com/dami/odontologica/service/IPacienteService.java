package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.PacienteDTO;

import java.util.List;
import java.util.Set;

public interface IPacienteService {

    PacienteDTO guardar (PacienteDTO pacienteDTO);
    PacienteDTO buscar(Long id);
    List<PacienteDTO> listarTodos();
    void eliminar(Long id);
    PacienteDTO actualizar (PacienteDTO pacienteDTO);

}
