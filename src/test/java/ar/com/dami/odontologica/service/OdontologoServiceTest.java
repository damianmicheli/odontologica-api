package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.OdontologoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    IOdontologoService odontologoService;

    public OdontologoDTO crearNuevoOdontologo(){
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Sujeto");
        odontologo.setApellido("de Prueba");
        odontologo.setMatricula("123355");

        return odontologo;
    }

    @Test
    void odontologoGuardarTest() throws NoEncontradoException, ConflictoException {

        OdontologoDTO odontologo = crearNuevoOdontologo();

        OdontologoDTO odontologoGuardado = odontologoService.guardar(odontologo);

        OdontologoDTO odontologoEncontrado = odontologoService.buscar(odontologoGuardado.getId());

        assertEquals("Sujeto", odontologoEncontrado.getNombre());
    }

    @Test
    void odontologoBuscarTest() throws NoEncontradoException, ConflictoException {

        OdontologoDTO odontologo = crearNuevoOdontologo();

        OdontologoDTO odontologoGuardado = odontologoService.guardar(odontologo);

        OdontologoDTO odontologoEncontrado = odontologoService.buscar(odontologoGuardado.getId());

        assertEquals("Sujeto", odontologoEncontrado.getNombre());
    }

    @Test
    void odontologoListarTodosTest() throws ConflictoException {

        OdontologoDTO odontologo = crearNuevoOdontologo();

        odontologoService.guardar(odontologo);
        odontologoService.guardar(odontologo);
        odontologoService.guardar(odontologo);

        List<OdontologoDTO> odontologos = odontologoService.listarTodos();

        assertTrue(odontologos.size() >= 3);

    }

    @Test
    void odontologoEliminarTest() throws NoEncontradoException, ConflictoException {

        OdontologoDTO odontologo = crearNuevoOdontologo();

        OdontologoDTO odontologoGuardado = odontologoService.guardar(odontologo);

        Long id = odontologoGuardado.getId();

        OdontologoDTO odontologoEncontrado = odontologoService.buscar(id);

        assertEquals("Sujeto", odontologoEncontrado.getNombre());

        odontologoService.eliminar(id);

        OdontologoDTO odontologoNoEncontrado = odontologoService.buscar(id);

        assertNull(odontologoNoEncontrado);



    }

    @Test
    void odontologoActualizarTest() throws NoEncontradoException, ConflictoException {

        OdontologoDTO odontologo = crearNuevoOdontologo();

        OdontologoDTO odontologoGuardado = odontologoService.guardar(odontologo);

        Long id = odontologoGuardado.getId();

        assertEquals("Sujeto", odontologoGuardado.getNombre());

        OdontologoDTO odontologoActualizar = odontologoGuardado;
        odontologoActualizar.setNombre("Doc. Emmet");
        odontologoActualizar.setApellido("Brown");
        odontologoActualizar.setMatricula("808080");

        odontologoService.actualizar(odontologoActualizar);

        OdontologoDTO odontologoEncontrado = odontologoService.buscar(id);

        assertEquals("Doc. Emmet", odontologoEncontrado.getNombre());

    }
}