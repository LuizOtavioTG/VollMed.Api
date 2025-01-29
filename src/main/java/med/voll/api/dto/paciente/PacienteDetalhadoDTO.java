package med.voll.api.dto.paciente;

import jakarta.persistence.Embedded;
import med.voll.api.model.Endereco;
import med.voll.api.model.Paciente;

public record PacienteDetalhadoDTO(Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {
    public PacienteDetalhadoDTO (Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getEmail(), paciente.getTelefone(), paciente.getEndereco());
    }
}
