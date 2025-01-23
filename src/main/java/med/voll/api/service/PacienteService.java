package med.voll.api.service;

import med.voll.api.dto.PacienteCadastroDTO;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    private Paciente convercaoParaPaciete (PacienteCadastroDTO pacienteCadastroDTO){
        return new Paciente(pacienteCadastroDTO);
    }

    public void cadastrarPaciente(PacienteCadastroDTO pacienteCadastroDTO){
        pacienteRepository.save(convercaoParaPaciete(pacienteCadastroDTO));
    }

}
