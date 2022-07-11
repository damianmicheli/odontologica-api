package ar.com.dami.odontologica.service;

import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.entity.Domicilio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteServiceTest {

    @Autowired
    IPacienteService pacienteService;

    public PacienteDTO crearPacienteNuevo(){
        Random rand = new Random();

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Don Bosco");
        domicilio.setNumero("5544");
        domicilio.setLocalidad("CABA");
        domicilio.setProvincia("CABA");

        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Paciente");
        paciente.setApellido("de Prueba");
        paciente.setDni(String.valueOf(rand.nextInt(31000000)+2000000));
        paciente.setFechaIngreso(LocalDate.of(2002,5,5));
        paciente.setDomicilio(domicilio);

        return paciente;
    }

    @Test
    void pacienteGuardarTest() throws ConflictoException, NoEncontradoException, DatosIncorrectosException {

        PacienteDTO paciente = crearPacienteNuevo();

        PacienteDTO pacienteGuardado = pacienteService.guardar(paciente);

        PacienteDTO pacienteEncontrado = pacienteService.buscar(pacienteGuardado.getId());
        System.out.println(pacienteEncontrado);
        System.out.println(pacienteEncontrado.getDomicilio().toString());
        assertEquals("Paciente", pacienteEncontrado.getNombre());
    }

    @Test
    void pacienteBuscarTest() throws ConflictoException, NoEncontradoException, DatosIncorrectosException {

        PacienteDTO paciente = crearPacienteNuevo();

        PacienteDTO pacienteGuardado = pacienteService.guardar(paciente);

        PacienteDTO pacienteEncontrado = pacienteService.buscar(pacienteGuardado.getId());

        assertEquals("Paciente", pacienteEncontrado.getNombre());
    }

    @Test
    void pacienteListarTodosTest() throws ConflictoException, DatosIncorrectosException {


        pacienteService.guardar(crearPacienteNuevo());
        pacienteService.guardar(crearPacienteNuevo());
        pacienteService.guardar(crearPacienteNuevo());

        List<PacienteDTO> pacientes = pacienteService.listarTodos();

        assertTrue(pacientes.size() >= 3);

    }

    @Test
    void pacienteEliminarTest() throws ConflictoException, NoEncontradoException, DatosIncorrectosException {

        PacienteDTO paciente = crearPacienteNuevo();

        PacienteDTO pacienteGuardado = pacienteService.guardar(paciente);

        Long id = pacienteGuardado.getId();

        PacienteDTO pacienteEncontrado = pacienteService.buscar(id);

        assertEquals("Paciente", pacienteEncontrado.getNombre());

        pacienteService.eliminar(id);


        NoEncontradoException thrown = assertThrows(NoEncontradoException.class, () -> {
            pacienteService.buscar(id);
        });

            assertEquals("Paciente con Id " + id + " no encontrado.", thrown.getMessage());


    }

    @Test
    void pacienteActualizarTest() throws NoEncontradoException, ConflictoException, DatosIncorrectosException {

        PacienteDTO paciente = crearPacienteNuevo();

        PacienteDTO pacienteGuardado = pacienteService.guardar(paciente);

        Long id = pacienteGuardado.getId();

        assertEquals("Paciente", pacienteGuardado.getNombre());

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