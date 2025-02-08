package med.voll.api.service.validators;

import jakarta.persistence.Column;
import jakarta.validation.ValidationException;
import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta{

    private final ConsultaRepository repository;

    public ValidadorMedicoComOutraConsultaNoMesmoHorario(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar (ConsultaAgendamentoDTO dados){
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }
}
