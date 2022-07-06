package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.OdontologoDTO;

import java.util.List;

public interface IOdontologoService {

    OdontologoDTO guardar (OdontologoDTO odontologoDTO);
    OdontologoDTO buscar(Long id);
    List<OdontologoDTO> listarTodos();
    void eliminar(Long id);
    OdontologoDTO actualizar (OdontologoDTO odontologoDTO);

}
