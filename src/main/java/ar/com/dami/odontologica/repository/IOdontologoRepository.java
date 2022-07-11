package ar.com.dami.odontologica.repository;

import ar.com.dami.odontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {

    Optional<Odontologo> findByMatricula(String matricula);

}


