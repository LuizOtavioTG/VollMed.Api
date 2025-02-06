package med.voll.api.dto.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaAgendamentoDTO(Long idMedico,

                                     @NotNull
                                     Long idPaciente,

                                     @NotNull
                                     @Future
                                     LocalDateTime data) {
}
