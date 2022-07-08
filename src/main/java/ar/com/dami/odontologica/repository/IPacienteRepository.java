package ar.com.dami.odontologica.repository;

import ar.com.dami.odontologica.entity.Odontologo;
import ar.com.dami.odontologica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("FROM Paciente o WHERE o.dni = ?1")
    public Optional<Paciente> findByDni(String paciente);
}
