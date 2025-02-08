package med.voll.api.service.validators;

import jakarta.validation.ValidationException;
import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.PacienteRepository;
import org.springframework.stereotype.Component;


@Component
public class ValidadorPacienteAtivo  implements ValidadorAgendamentoDeConsulta{

    private final PacienteRepository repository;

    public ValidadorPacienteAtivo(PacienteRepository repository) {
        this.repository = repository;
    }

    public void validar(ConsultaAgendamentoDTO dados){
        Boolean pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com paciente inativo");
        }
    }


}
