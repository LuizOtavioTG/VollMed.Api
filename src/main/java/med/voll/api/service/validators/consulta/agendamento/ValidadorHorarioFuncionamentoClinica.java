package med.voll.api.service.validators.consulta.agendamento;

import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements  ValidadorAgendamentoDeConsulta{

    public void  validar (ConsultaAgendamentoDTO dados){
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisEncerramentoDaClinica = dataConsulta.getHour() >= 18;
        if (domingo || antesAberturaDaClinica || depoisEncerramentoDaClinica){
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }

    }
}
