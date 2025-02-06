package med.voll.api.service;

import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    private ConsultaRepository consultaRepository;
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;
    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public void agendar (ConsultaAgendamentoDTO dadosDTO){
        if (!pacienteRepository.existsById(dadosDTO.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }
        if (dadosDTO.idMedico() != null && !medicoRepository.existsById(dadosDTO.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }


        var paciente = pacienteRepository.getReferenceById(dadosDTO.idPaciente());
        var medico = escolherMedico(dadosDTO);
        var consulta = new Consulta(null, medico, paciente, dadosDTO.data());
        consultaRepository.save(consulta);

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
