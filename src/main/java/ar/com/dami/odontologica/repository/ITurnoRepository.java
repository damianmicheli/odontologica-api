package ar.com.dami.odontologica.repository;

import ar.com.dami.odontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITurnoRepository extends JpaRepository<Turno, Long> {
}
