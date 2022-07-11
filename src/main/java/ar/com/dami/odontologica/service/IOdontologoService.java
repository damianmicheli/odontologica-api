package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.OdontologoDTO;

import java.util.List;

public interface IOdontologoService {

    OdontologoDTO guardar (OdontologoDTO odontologoDTO) throws ConflictoException;
    OdontologoDTO buscar(Long id) throws NoEncontradoException;
    OdontologoDTO buscarPorMatricula(String matricula) throws NoEncontradoException;
    List<OdontologoDTO> listarTodos();
    void eliminar(Long id) throws NoEncontradoException;
    OdontologoDTO actualizar (OdontologoDTO odontologoDTO) throws NoEncontradoException;

}
