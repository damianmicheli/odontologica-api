package ar.com.dami.odontologica.repository;

import ar.com.dami.odontologica.entity.Odontologo;
import ar.com.dami.odontologica.entity.Paciente;
import ar.com.dami.odontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ITurnoRepository extends JpaRepository<Turno, Long> {

    Optional<Turno> findByOdontologoAndFechaHora(Odontologo odontologo, LocalDateTime fechaHora);

    Optional<Turno> findByPacienteAndFechaHora(Paciente paciente, LocalDateTime fechaHora);
}
