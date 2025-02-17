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
         select m from Medico m
                    where
                    m.ativo = true
                    and
                    m.especialidade = :especialidade
                    and
                    m.id not in(
                        select c.medico.id from Consulta c
                        where
                        c.data = :data
                and
                        c.motivoCancelamento is null
                    )
                    order by rand()
                    limit 1
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
