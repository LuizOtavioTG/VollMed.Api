package med.voll.api.dto.consulta;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.MotivoCancelamento;

public record ConsultaCancelamentoDTO(@NotNull
                                      Long idConsulta,

                                      @NotNull
                                      MotivoCancelamento motivoCancelamento) {
}
