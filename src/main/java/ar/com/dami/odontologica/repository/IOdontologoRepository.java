package ar.com.dami.odontologica.repository;

import ar.com.dami.odontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {
}


