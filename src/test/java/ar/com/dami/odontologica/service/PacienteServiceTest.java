package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.entity.Domicilio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteServiceTest {

    @Autowired
    IPacienteService pacienteService;

    public PacienteDTO crearPacienteNuevo(){
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Don Bosco");
        domicilio.setNumero("5544");
        domicilio.setLocalidad("CABA");
        domicilio.setProvincia("CABA");

        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Sujeto");
        paciente.setApellido("de Prueba");
        paciente.setDni("22334455");
        paciente.setFechaIngreso(LocalDate.of(2002,5,5));
        paciente.setDomicilio(domicilio);

        return paciente;
    }

    @Test
    void pacienteGuardarTest() {

        PacienteDTO paciente = crearPacienteNuevo();

        PacienteDTO pacienteGuardado = pacienteService.guardar(paciente);

        PacienteDTO pacienteEncontrado = pacienteService.buscar(pacienteGuardado.getId());
        System.out.println(pacienteEncontrado);
        System.out.println(pacienteEncontrado.getDomicilio().toString());
        assertEquals("Sujeto", pacienteEncontrado.getNombre());
    }

    @Test
    void pacienteBuscarTest() {

        PacienteDTO paciente = crearPacienteNuevo();

        PacienteDTO pacienteGuardado = pacienteService.guardar(paciente);

        PacienteDTO pacienteEncontrado = pacienteService.buscar(pacienteGuardado.getId());

        assertEquals("Sujeto", pacienteEncontrado.getNombre());
    }

    @Test
    void pacienteListarTodosTest() {

        PacienteDTO paciente = crearPacienteNuevo();

        pacienteService.guardar(paciente);
        pacienteService.guardar(paciente);
        pacienteService.guardar(paciente);

        List<PacienteDTO> pacientes = pacienteService.listarTodos();

        assertTrue(pacientes.size() >= 3);

    }

    @Test
    void pacienteEliminarTest() {

        PacienteDTO paciente = crearPacienteNuevo();

        PacienteDTO pacienteGuardado = pacienteService.guardar(paciente);

        Long id = pacienteGuardado.getId();

        PacienteDTO pacienteEncontrado = pacienteService.buscar(id);

        assertEquals("Sujeto", pacienteEncontrado.getNombre());

        pacienteService.eliminar(id);

        PacienteDTO pacienteNoEncontrado = pacienteService.buscar(id);

        assertNull(pacienteNoEncontrado);

    }

    @Test
    void pacienteActualizarTest() {

        PacienteDTO paciente = crearPacienteNuevo();

        PacienteDTO pacienteGuardado = pacienteService.guardar(paciente);

        Long id = pacienteGuardado.getId();

        assertEquals("Sujeto", pacienteGuardado.getNombre());

        PacienteDTO pacienteActualizar = pacienteGuardado;
        pacienteActualizar.setNombre("Marty");
        pacienteActualizar.setApellido("McFly");
        pacienteActualizar.getDomicilio().setCalle("Calle falsa");

        pacienteService.actualizar(pacienteActualizar);

        PacienteDTO pacienteEncontrado = pacienteService.buscar(id);

        assertEquals("Marty", pacienteEncontrado.getNombre());
        assertEquals("Calle falsa", pacienteEncontrado.getDomicilio().getCalle());


    }
}