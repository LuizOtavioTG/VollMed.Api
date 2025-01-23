package med.voll.api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.PacienteCadastroDTO;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;

    public Paciente(PacienteCadastroDTO pacienteCadastroDTO) {
        this.nome = pacienteCadastroDTO.nome();
        this.email = pacienteCadastroDTO.email();
        this.telefone = pacienteCadastroDTO.email();
        this.cpf = pacienteCadastroDTO.cpf();
        this.endereco = new Endereco(pacienteCadastroDTO.endereco());
    }
}
