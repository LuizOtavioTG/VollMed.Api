package med.voll.api.service.validators.consulta.cancelamento;

import med.voll.api.dto.consulta.ConsultaCancelamentoDTO;

public interface ValidadorCancelamentoDeConsulta {
    void validar (ConsultaCancelamentoDTO dados);
}
