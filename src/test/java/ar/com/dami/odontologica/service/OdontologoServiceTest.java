package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.OdontologoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    IOdontologoService odontologoService;

    private final Random rand = new Random();

    public OdontologoDTO crearNuevoOdontologo(){
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Odontologo");
        odontologo.setApellido("de Prueba");
        odontologo.setMatricula(String.valueOf(rand.nextInt(9000)+1000));

        return odontologo;
    }

    @Test
    void odontologoGuardarTest() throws NoEncontradoException, ConflictoException {

        OdontologoDTO odontologo = crearNuevoOdontologo();

        OdontologoDTO odontologoGuardado = odontologoService.guardar(odontologo);

        OdontologoDTO odontologoEncontrado = odontologoService.buscar(odontologoGuardado.getId());

        assertEquals("Odontologo", odontologoEncontrado.getNombre());
    }

    @Test
    void odontologoBuscarTest() throws NoEncontradoException, ConflictoException {

        OdontologoDTO odontologo = crearNuevoOdontologo();

        OdontologoDTO odontologoGuardado = odontologoService.guardar(odontologo);

        OdontologoDTO odontologoEncontrado = odontologoService.buscar(odontologoGuardado.getId());

        assertEquals("Odontologo", odontologoEncontrado.getNombre());
    }

    @Test
    void odontologoListarTodosTest() throws ConflictoException {


        odontologoService.guardar(crearNuevoOdontologo());
        odontologoService.guardar(crearNuevoOdontologo());
        odontologoService.guardar(crearNuevoOdontologo());

        List<OdontologoDTO> odontologos = odontologoService.listarTodos();

        assertTrue(odontologos.size() >= 3);

    }

    @Test
    void odontologoEliminarTest() throws NoEncontradoException, ConflictoException {

        OdontologoDTO odontologo = crearNuevoOdontologo();

        OdontologoDTO odontologoGuardado = odontologoService.guardar(odontologo);

        Long id = odontologoGuardado.getId();

        OdontologoDTO odontologoEncontrado = odontologoService.buscar(id);

        assertEquals("Odontologo", odontologoEncontrado.getNombre());

        odontologoService.eliminar(id);

        NoEncontradoException thrown = assertThrows(NoEncontradoException.class, () -> {
            odontologoService.buscar(id);
        });

        assertEquals("Odontólogo con Id " + id + " no encontrado.", thrown.getMessage());



    }

    @Test
    void odontologoActualizarTest() throws NoEncontradoException, ConflictoException {

        OdontologoDTO odontologo = crearNuevoOdontologo();

        OdontologoDTO odontologoGuardado = odontologoService.guardar(odontologo);

        Long id = odontologoGuardado.getId();

        assertEquals("Odontologo", odontologoGuardado.getNombre());

        OdontologoDTO odontologoActualizar = odontologoGuardado;
        odontologoActualizar.setNombre("Doc. Emmet");
        odontologoActualizar.setApellido("Brown");
        odontologoActualizar.setMatricula(String.valueOf(rand.nextInt(9000)+1000));

        odontologoService.actualizar(odontologoActualizar);

        OdontologoDTO odontologoEncontrado = odontologoService.buscar(id);

        assertEquals("Doc. Emmet", odontologoEncontrado.getNombre());

    }
}