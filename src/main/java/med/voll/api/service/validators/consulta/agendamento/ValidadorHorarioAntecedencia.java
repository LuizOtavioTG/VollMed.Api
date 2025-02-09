package med.voll.api.service.validators.consulta.agendamento;

import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta{

    public void  validar (ConsultaAgendamentoDTO dados){
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
        if(diferencaEmMinutos<30){
            throw new ValidacaoException("Consulta de ser agendada com 30 minutos de antecedencia");
        }
    }
}
