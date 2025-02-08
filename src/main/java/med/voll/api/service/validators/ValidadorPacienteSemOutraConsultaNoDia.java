package med.voll.api.service.validators;

import jakarta.validation.ValidationException;
import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta{

    private final ConsultaRepository repository;
    public ValidadorPacienteSemOutraConsultaNoDia(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar (ConsultaAgendamentoDTO dados){
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
