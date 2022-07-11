package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.OdontologoDTO;
import ar.com.dami.odontologica.entity.Odontologo;
import ar.com.dami.odontologica.repository.IOdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService{

    private final Logger logger = Logger.getLogger(OdontologoService.class);

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public OdontologoDTO guardar(OdontologoDTO odontologoDTO) throws ConflictoException {

        String matricula = odontologoDTO.getMatricula();
        Optional<Odontologo> encontrado = odontologoRepository.findByMatricula(matricula);

        if (encontrado.isPresent()){
            throw new ConflictoException("Ya existe un odontólogo con matrícula " + matricula + ".");
        }

        Odontologo odontologo = mapper.convertValue(odontologoDTO, Odontologo.class);

        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);

        OdontologoDTO odontologoDTOGuardado = mapper.convertValue(odontologoGuardado,OdontologoDTO.class);

        logger.info("Se guardó el odontólogo: " + odontologoDTOGuardado);

        return odontologoDTOGuardado;
    }

    @Override
    public OdontologoDTO buscar(Long id) throws NoEncontradoException {

        Optional<Odontologo> odontologo = odontologoRepository.findById(id);

        if (odontologo.isEmpty()){
            throw new NoEncontradoException("Odontólogo con Id " + id + " no encontrado.");
        }

        OdontologoDTO odontologoDTOBuscado = mapper.convertValue(odontologo,OdontologoDTO.class);

        logger.info("Se buscó por Id el odontólogo: " + odontologoDTOBuscado);

        return odontologoDTOBuscado;
    }

    @Override
    public OdontologoDTO buscarPorMatricula(String matricula) throws NoEncontradoException {

        Optional<Odontologo> odontologo = odontologoRepository.findByMatricula(matricula);

        if (odontologo.isEmpty()){
            throw new NoEncontradoException("Odontólogo con Matricula " + matricula + " no encontrado.");
        }

        OdontologoDTO odontologoDTOBuscado = mapper.convertValue(odontologo,OdontologoDTO.class);

        logger.info("Se buscó por Matrícula el odontólogo: " + odontologoDTOBuscado);

        return odontologoDTOBuscado;
    }


    @Override
    public List<OdontologoDTO> listarTodos() {

        List<Odontologo> odontologos = odontologoRepository.findAll();
        List<OdontologoDTO> odontologosDTO = new ArrayList<>();

        for (Odontologo odontologo : odontologos){
            odontologosDTO.add(mapper.convertValue(odontologo, OdontologoDTO.class));
        }

        logger.info("Se listaron todos los odontólogos.");

        return odontologosDTO;
    }

    @Override
    public void eliminar(Long id) throws NoEncontradoException {

        Optional<Odontologo> odontologo = odontologoRepository.findById(id);

        if (odontologo.isEmpty()){
            throw new NoEncontradoException("No se puede eliminar porque no existe el Odontólogo con Id " + id + ",");
        }

        OdontologoDTO odontologoDTOAEliminar = mapper.convertValue(odontologo,OdontologoDTO.class);

        odontologoRepository.deleteById(id);

        logger.info("Se eliminó el odontólogo: " + odontologoDTOAEliminar);

    }

    @Override
    public OdontologoDTO actualizar(OdontologoDTO odontologoDTO) throws NoEncontradoException {

        Long id = odontologoDTO.getId();

        Optional<Odontologo> encontrado = odontologoRepository.findById(id);

        if (encontrado.isEmpty()){
            throw new NoEncontradoException("No se puede actualizar porque no existe un odontólogo con Id: " + id + ".");
        }

        OdontologoDTO odontologoDTOParaActualizar = mapper.convertValue(encontrado, OdontologoDTO.class);

        logger.info("Se actualizará un odontólogo. Datos actuales: " + odontologoDTOParaActualizar);

        Odontologo odontologo = mapper.convertValue(odontologoDTO, Odontologo.class);

        Odontologo odontologoRes = odontologoRepository.save(odontologo);

        OdontologoDTO odontologoDTOActualizado = mapper.convertValue(odontologoRes,OdontologoDTO.class);

        logger.info("Datos actuales: " + odontologoDTOActualizado);

        return odontologoDTOActualizado;
    }
}
