package med.voll.api.service;

import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.dto.consulta.ConsultaDetalhamentoDTO;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.service.validators.ValidadorAgendamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    private final List<ValidadorAgendamentoDeConsulta> validadores;
    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository,
                           MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository,
                           List<ValidadorAgendamentoDeConsulta> validadores) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;
    }

    public ConsultaDetalhamentoDTO agendar (ConsultaAgendamentoDTO dadosDTO){
        if (!pacienteRepository.existsById(dadosDTO.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }
        if (dadosDTO.idMedico() != null && !medicoRepository.existsById(dadosDTO.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validadores.forEach(v -> v.validar(dadosDTO));

        var paciente = pacienteRepository.getReferenceById(dadosDTO.idPaciente());
        var medico = escolherMedico(dadosDTO);
        if(medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }
        var consulta = new Consulta(null, medico, paciente, dadosDTO.data());
        consultaRepository.save(consulta);

        return  new ConsultaDetalhamentoDTO(consulta);

    }

    private Medico escolherMedico(ConsultaAgendamentoDTO dadosDTO) {
        if(dadosDTO.idMedico() != null){
            return medicoRepository.getReferenceById(dadosDTO.idMedico());
        }
        if(dadosDTO.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando médico não é selecionado!");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dadosDTO.especialidade(), dadosDTO.data());
    }
}
