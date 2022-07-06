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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {

    @Autowired
    ITurnoService turnoService;

    @Autowired
    IPacienteService pacienteService;

    @Autowired
    IOdontologoService odontologoService;

    public OdontologoDTO crearNuevoOdontologo(){
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Sujeto");
        odontologo.setApellido("de Prueba");
        odontologo.setMatricula("123355");

        return odontologo;
    }

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

    public TurnoDTO crearNuevoTurno(){

        TurnoDTO turno = new TurnoDTO();

        PacienteDTO pacienteDTO = pacienteService.guardar(crearPacienteNuevo());
        Paciente paciente = new Paciente();
        paciente.setId(pacienteDTO.getId());

        OdontologoDTO odontologoDTO = odontologoService.guardar(crearNuevoOdontologo());
        Odontologo odontologo = new Odontologo();
        odontologo.setId(odontologoDTO.getId());

        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFechaHora(LocalDateTime.of(2022,5,4,18,30));

        return turno;
    }

    @Test
    void turnoGuardarTest() {

        TurnoDTO turno = crearNuevoTurno();

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        Long id = turnoGuardado.getId();

        TurnoDTO turnoEncontrado = turnoService.buscar(id);

        assertEquals(LocalDateTime.of(2022,5,4,18,30), turnoEncontrado.getFechaHora());
    }

    @Test
    void turnoBuscarTest() {

        TurnoDTO turno = crearNuevoTurno();


        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        TurnoDTO turnoEncontrado = turnoService.buscar(turnoGuardado.getId());

        assertEquals(LocalDateTime.of(2022,5,4,18,30), turnoEncontrado.getFechaHora());
    }

    @Test
    void turnoListarTodosTest() {
        TurnoDTO turno = crearNuevoTurno();


        turnoService.guardar(turno);
        turnoService.guardar(turno);
        turnoService.guardar(turno);

        List<TurnoDTO> turnos = turnoService.listarTodos();

        assertTrue(turnos.size() >= 3);

    }

    @Test
    void turnoEliminarTest() {
        TurnoDTO turno = crearNuevoTurno();

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        Long id = turnoGuardado.getId();

        TurnoDTO turnoEncontrado = turnoService.buscar(id);

        assertEquals(LocalDateTime.of(2022,5,4,18,30), turnoEncontrado.getFechaHora());

        turnoService.eliminar(id);

        TurnoDTO turnoNoEncontrado = turnoService.buscar(id);

        assertNull(turnoNoEncontrado);

    }

    @Test
    void turnoActualizarTest() {

        TurnoDTO turno = crearNuevoTurno();

        TurnoDTO turnoGuardado = turnoService.guardar(turno);

        Long id = turnoGuardado.getId();

        assertEquals(LocalDateTime.of(2022,5,4,18,30), turnoGuardado.getFechaHora());

        TurnoDTO turnoActualizar = turnoGuardado;

        OdontologoDTO odontologoDTO = odontologoService.guardar(crearNuevoOdontologo());

        Long idOdontologoNuevo = odontologoDTO.getId();
        turnoActualizar.getOdontologo().setId(idOdontologoNuevo);
        turnoActualizar.setFechaHora(LocalDateTime.of(2022,6,4,18,30));


        turnoService.actualizar(turnoActualizar);

        TurnoDTO turnoEncontrado = turnoService.buscar(id);

        assertEquals(LocalDateTime.of(2022,6,4,18,30), turnoEncontrado.getFechaHora());
        assertEquals(idOdontologoNuevo,turnoEncontrado.getOdontologo().getId());

    }
}