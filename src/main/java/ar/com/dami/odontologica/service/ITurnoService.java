package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.TurnoDTO;

import java.util.List;

public interface ITurnoService {

    TurnoDTO guardar (TurnoDTO turnoDTO) throws ConflictoException, DatosIncorrectosException, NoEncontradoException;
    TurnoDTO buscar(Long id) throws NoEncontradoException;
    List<TurnoDTO> listarTodos();
    void eliminar(Long id) throws NoEncontradoException;
    TurnoDTO actualizar (TurnoDTO turnoDTO) throws NoEncontradoException, DatosIncorrectosException, ConflictoException;

}
