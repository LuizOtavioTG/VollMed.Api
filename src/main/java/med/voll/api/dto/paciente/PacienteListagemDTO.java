package med.voll.api.dto.paciente;

import med.voll.api.model.Medico;
import med.voll.api.model.Paciente;

public record PacienteListagemDTO (Long id,
                                   String nome,
                                   String email,
                                   String cpf) {
    public PacienteListagemDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
