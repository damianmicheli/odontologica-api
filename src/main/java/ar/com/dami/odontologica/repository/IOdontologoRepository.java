package ar.com.dami.odontologica.repository;

import ar.com.dami.odontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {

    @Query("FROM Odontologo o WHERE o.matricula = ?1")
    public Optional<Odontologo> findByMatricula(String matricula);

}


