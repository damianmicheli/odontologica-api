package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.TurnoDTO;

import java.util.List;

public interface ITurnoService {

    TurnoDTO guardar (TurnoDTO turnoDTO);
    TurnoDTO buscar(Long id);
    List<TurnoDTO> listarTodos();
    void eliminar(Long id);
    TurnoDTO actualizar (TurnoDTO turnoDTO);

}
