package ar.com.dami.odontologica.service;


import ar.com.dami.odontologica.dto.OdontologoDTO;
import ar.com.dami.odontologica.dto.PacienteDTO;
import ar.com.dami.odontologica.dto.TurnoDTO;
import ar.com.dami.odontologica.entity.Domicilio;
import ar.com.dami.odontologica.entity.Odontologo;
import ar.com.dami.odontologica.entity.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {

    @Autowired
    ITurnoService turnoService;

    @Autowired
    IPacienteService pacienteService;

    @Autowired
    IOdontologoService odontologoService;

    private final LocalDateTime fechaTurno =
            LocalDateTime
                    .now()
                    .withHour(15)
                    .withMinute(30)
                    .withSecond(0)
                    .withNano(0)
                    .plusMonths(1);


    public OdontologoDTO crearNuevoOdontologo(){
        Random rand = new Random();
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Odontologo");
        odontologo.setApellido("de Prueba");
        odontologo.setMatricula(String.valueOf(rand.nextInt(10000)));

        return odontologo;
    }

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

    public TurnoDTO crearNuevoTurno() throws ConflictoException {

        TurnoDTO turno = new TurnoDTO();

        PacienteDTO pacienteDTO = pacienteService.guardar(crearPacienteNuevo());
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDTO.getId());

        OdontologoDTO odontologoDTO = odontologoService.guardar(crearNuevoOdontologo());
        Odontologo odontologo = new Odontologo();
        odontologo.setId(odontologoDTO.getId());

        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        turno.setFechaHora(fechaTurno);

        return turno;
    }

    @Test
    void turnoGuardarTest() throws ConflictoException {

        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(5L));

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        Long id = turnoGuardado.getId();

        TurnoDTO turnoEncontrado = turnoService.buscar(id);

        assertEquals(fechaTurno.plusDays(5L), turnoEncontrado.getFechaHora());
    }

    @Test
    void turnoBuscarTest() throws ConflictoException {

        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(10L));

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        TurnoDTO turnoEncontrado = turnoService.buscar(turnoGuardado.getId());

        assertEquals(fechaTurno.plusDays(10L), turnoEncontrado.getFechaHora());
    }

    @Test
    void turnoListarTodosTest() throws ConflictoException {
        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(15L));
        turnoService.guardar(turno);

        TurnoDTO turno2 = crearNuevoTurno();
        turno2.setFechaHora(fechaTurno.plusDays(20L));
        turnoService.guardar(turno2);

        TurnoDTO turno3 = crearNuevoTurno();
        turno3.setFechaHora(fechaTurno.plusDays(25L));
        turnoService.guardar(turno3);


        List<TurnoDTO> turnos = turnoService.listarTodos();

        assertTrue(turnos.size() >= 3);

    }

    @Test
    void turnoEliminarTest() throws ConflictoException {
        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(30L));

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        Long id = turnoGuardado.getId();

        TurnoDTO turnoEncontrado = turnoService.buscar(id);

        assertEquals(fechaTurno.plusDays(30L), turnoEncontrado.getFechaHora());

        turnoService.eliminar(id);

        TurnoDTO turnoNoEncontrado = turnoService.buscar(id);

        assertNull(turnoNoEncontrado);

    }

    @Test
    void turnoActualizarTest() throws ConflictoException {

        TurnoDTO turno = crearNuevoTurno();
        turno.setFechaHora(fechaTurno.plusDays(35L));

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        Long id = turnoGuardado.getId();

        assertEquals(fechaTurno.plusDays(35L), turnoGuardado.getFechaHora());

        TurnoDTO turnoActualizar = turnoGuardado;

        OdontologoDTO odontologoDTO = odontologoService.guardar(crearNuevoOdontologo());

        Long idOdontologoNuevo = odontologoDTO.getId();
        turnoActualizar.getOdontologo().setId(idOdontologoNuevo);
        turnoActualizar.setFechaHora(fechaTurno.plusDays(40L));


        turnoService.actualizar(turnoActualizar);

        TurnoDTO turnoEncontrado = turnoService.buscar(id);

        assertEquals(fechaTurno.plusDays(40L), turnoEncontrado.getFechaHora());
        assertEquals(idOdontologoNuevo,turnoEncontrado.getOdontologo().getId());

    }
}