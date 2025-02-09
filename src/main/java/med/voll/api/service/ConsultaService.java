package med.voll.api.service;

import med.voll.api.dto.consulta.ConsultaAgendamentoDTO;
import med.voll.api.dto.consulta.ConsultaCancelamentoDTO;
import med.voll.api.dto.consulta.ConsultaDetalhamentoDTO;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.service.validators.consulta.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.service.validators.consulta.cancelamento.ValidadorCancelamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    private final List<ValidadorAgendamentoDeConsulta> validadorAgendamentoDeConsultas;


    private final List<ValidadorCancelamentoDeConsulta> validadorCancelamentoDeConsultas;
    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository,
                           MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository,
                           List<ValidadorAgendamentoDeConsulta> validadorAgendamentoDeConsultas,
                           List<ValidadorCancelamentoDeConsulta> validadorCancelamentoDeConsultas) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadorAgendamentoDeConsultas = validadorAgendamentoDeConsultas;
        this.validadorCancelamentoDeConsultas = validadorCancelamentoDeConsultas;
    }

    public ConsultaDetalhamentoDTO agendar (ConsultaAgendamentoDTO dadosDTO){
        if (!pacienteRepository.existsById(dadosDTO.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }
        if (dadosDTO.idMedico() != null && !medicoRepository.existsById(dadosDTO.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validadorAgendamentoDeConsultas.forEach(v -> v.validar(dadosDTO));

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

    public void cancelar(ConsultaCancelamentoDTO consultaCancelamentoDTO) {

        if (!consultaRepository.existsById(consultaCancelamentoDTO.idConsulta())) {
            throw new ValidacaoException("Id da consulta informada não existe!");
        }
        validadorCancelamentoDeConsultas.forEach(v -> v.validar(consultaCancelamentoDTO));

        var consulta = consultaRepository.getReferenceById(consultaCancelamentoDTO.idConsulta());
        consulta.cancelar(consultaCancelamentoDTO.motivoCancelamento());
    }
}
