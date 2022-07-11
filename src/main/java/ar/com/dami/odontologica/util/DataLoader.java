package ar.com.dami.odontologica.util;

import ar.com.dami.odontologica.entity.Domicilio;
import ar.com.dami.odontologica.entity.Odontologo;
import ar.com.dami.odontologica.entity.Paciente;
import ar.com.dami.odontologica.entity.Turno;
import ar.com.dami.odontologica.repository.IOdontologoRepository;
import ar.com.dami.odontologica.repository.IPacienteRepository;
import ar.com.dami.odontologica.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    private final IOdontologoRepository odontologoRepository;
    private final IPacienteRepository pacienteRepository;
    private final ITurnoRepository turnoRepository;

    @Autowired
    public DataLoader(IOdontologoRepository odontologoRepository, IPacienteRepository pacienteRepository, ITurnoRepository turnoRepository) {
        this.odontologoRepository = odontologoRepository;
        this.pacienteRepository = pacienteRepository;
        this.turnoRepository = turnoRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Domicilio d1 = new Domicilio("Don vbosco", "2233", "CABA", "CABA");
        Paciente p1 = pacienteRepository.save(new Paciente ("Damián Nuevo", "Micheli", "27756514", LocalDate.now(), d1));

        Odontologo o1 = odontologoRepository.save(new Odontologo("CarlosNuevo", "Perez", "323213"));

        Turno t1 = new Turno(p1, o1, LocalDateTime.of(2022, 8, 15, 15, 30));
        turnoRepository.save(t1);

    }
}
