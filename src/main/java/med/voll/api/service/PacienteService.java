package med.voll.api.service;

import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.dto.paciente.PacienteDetalhadoDTO;
import med.voll.api.dto.paciente.PacienteListagemDTO;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    private Paciente convercaoParaPaciete (PacienteCadastroDTO pacienteCadastroDTO){
        return new Paciente(pacienteCadastroDTO);
    }

    public Paciente cadastrarPaciente(PacienteCadastroDTO pacienteCadastroDTO){
        return pacienteRepository.save(convercaoParaPaciete(pacienteCadastroDTO));
    }

    public Page<PacienteListagemDTO> listarTodosOsPacientes(Pageable paginacao) {
        Page<Paciente> pacientesPage = pacienteRepository.findAllByAtivoTrue(paginacao);
        return pacientesPage.map(PacienteListagemDTO::new);
    }

    public PacienteDetalhadoDTO autualizarPaciente(PacienteAtualizacaoDTO dadosAtualizados) {
        Paciente paciente = pacienteRepository.getReferenceById(dadosAtualizados.id());
        paciente.atualizarInformacoes(dadosAtualizados);
        return new PacienteDetalhadoDTO(paciente);
    }

    public void excluirPaciente(Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.excluirPaciente(id);
    }

    public PacienteDetalhadoDTO detalharPaciente(Long id) {
        Paciente  paciente = pacienteRepository.getReferenceById(id);
        return new PacienteDetalhadoDTO(paciente);

    }
}
