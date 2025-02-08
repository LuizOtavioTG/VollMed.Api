package med.voll.api.repository;

import aj.org.objectweb.asm.commons.Remapper;
import med.voll.api.model.Especialidade;
import med.voll.api.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {


    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
        SELECT m FROM Medico m 
        WHERE
        m.ativo = true 
        AND
        m.especialidade = :especialidade
        AND
        m.id NOT IN(
            SELECT c.medico.id FROM Consulta c
            WHERE
            c.data = :data)
        ORDER BY RANDOM()
        LIMIT 1
    """)
    Medico escolherMedicoAleatorioLivreNaData(@Param("especialidade")Especialidade especialidade,
                                              @Param("data") LocalDateTime data);
    @Query("""
          SELECT m.ativo
          FROM Medico m
          WHERE
          m.id = :id
    """)
    Boolean findAtivoById(@Param("id") Long idMedico);
}
