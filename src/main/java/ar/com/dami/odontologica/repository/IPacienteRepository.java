package ar.com.dami.odontologica.repository;

import ar.com.dami.odontologica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
}
