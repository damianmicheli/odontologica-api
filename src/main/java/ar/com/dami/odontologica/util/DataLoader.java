package ar.com.dami.odontologica.util;


import ar.com.dami.odontologica.entity.Domicilio;
import ar.com.dami.odontologica.entity.Odontologo;
import ar.com.dami.odontologica.entity.Paciente;
import ar.com.dami.odontologica.entity.Turno;
import ar.com.dami.odontologica.repository.IOdontologoRepository;
import ar.com.dami.odontologica.repository.IPacienteRepository;
import ar.com.dami.odontologica.repository.ITurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    private final Logger logger = Logger.getLogger(DataLoader.class);

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

        try {

            // Genero domicilios
            Domicilio d1 = new Domicilio("Dunder", "5533", "Scranton", "Pensilvania");
            Domicilio d2 = new Domicilio("Mifflin", "9988", "Scranton", "Pensilvania");
            Domicilio d3 = new Domicilio("Bandit", "2233", "Scranton", "Pensilvania");


            // Agrego Los pacientes
            Paciente p1 = pacienteRepository.save(new Paciente("Scott", "Michael", "20555444", LocalDate.now(), d1));
            Paciente p2 = pacienteRepository.save(new Paciente("Malone", "Kevin", "22444666", LocalDate.now(), d2));
            Paciente p3 = pacienteRepository.save(new Paciente("Martin", "Angela", "24777888", LocalDate.now(), d3));


            // Agrego odontologos
            Odontologo o1 = odontologoRepository.save(new Odontologo("Dwight", "Schrute", "444666"));
            Odontologo o2 = odontologoRepository.save(new Odontologo("Martinez", "Oscar", "323213"));
            Odontologo o3 = odontologoRepository.save(new Odontologo("Toby", "Flenderson", "444477"));

            // Agrego turnos
            Turno t1 = new Turno(p1, o1, LocalDateTime.of(2022, 8, 15, 15, 30));
            turnoRepository.save(t1);

            Turno t2 = new Turno(p2, o2, LocalDateTime.of(2022, 7, 20, 14, 00));
            turnoRepository.save(t2);

            Turno t3 = new Turno(p3, o3, LocalDateTime.of(2022, 7, 19, 11, 30));
            turnoRepository.save(t3);

        }
        catch (Exception e) {
            String mensajeError = e.getMessage();

            String mensajeErrorCorrecto = mensajeError.replace('\'','`');

            logger.error(mensajeErrorCorrecto);
        }
    }
}
