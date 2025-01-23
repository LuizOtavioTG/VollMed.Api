package med.voll.api.service;

import med.voll.api.dto.MedicoAtualizacaoDTO;
import med.voll.api.dto.MedicoCadastroDTO;
import med.voll.api.dto.MedicoListagemDTO;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    private Medico convercaoParaMedico(MedicoCadastroDTO medicoCadastroDTO){
        return new Medico(medicoCadastroDTO);
    }

    private List<MedicoListagemDTO> convercaoParaMedicoDTO(List<Medico> medicos){
        return  medicos.stream()
                .map(MedicoListagemDTO::new)
                .toList();
    }


    public void cadastrarMedico(MedicoCadastroDTO dado) {
        medicoRepository.save(convercaoParaMedico(dado));
    }

    public Page<MedicoListagemDTO> listarTodosOsMedicos(Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(MedicoListagemDTO::new);
    }

    public void autualizarMedico(MedicoAtualizacaoDTO dados) {
        Medico medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    public void excluirMedico(Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }
}
