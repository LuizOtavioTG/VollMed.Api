package med.voll.api.dto.consulta;

import java.time.LocalDateTime;

public record ConsultaDetalhamentoDTO (Long id,
                                       Long idMedico,
                                       Long idPaciente,
                                       LocalDateTime data){
    
}