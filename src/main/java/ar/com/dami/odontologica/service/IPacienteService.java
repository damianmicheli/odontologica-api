package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.PacienteDTO;

import java.util.List;
import java.util.Set;

public interface IPacienteService {

    PacienteDTO guardar (PacienteDTO pacienteDTO) throws ConflictoException;
    PacienteDTO buscar(Long id) throws NoEncontradoException;
    PacienteDTO buscarPorDni(String dni) throws NoEncontradoException;
    List<PacienteDTO> listarTodos();
    void eliminar(Long id);
    PacienteDTO actualizar (PacienteDTO pacienteDTO);

}
