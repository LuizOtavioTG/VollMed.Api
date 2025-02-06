package med.voll.api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;

import java.util.List;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;
    private Boolean ativo;

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;

    public Paciente(PacienteCadastroDTO pacienteCadastroDTO) {
        this.ativo = true;
        this.nome = pacienteCadastroDTO.nome();
        this.email = pacienteCadastroDTO.email();
        this.telefone = pacienteCadastroDTO.email();
        this.cpf = pacienteCadastroDTO.cpf();
        this.endereco = new Endereco(pacienteCadastroDTO.endereco());
    }

    public void atualizarInformacoes(PacienteAtualizacaoDTO dadosAtualizados) {
        if(dadosAtualizados.nome() != null){
            this.nome = dadosAtualizados.nome();
        }
        if(dadosAtualizados.telefone() != null){
            this.telefone = dadosAtualizados.telefone();
        }
        if(dadosAtualizados.endereco() != null){
            this.endereco.atualizarInformacoesEndereco(dadosAtualizados.endereco());
        }


    }

    public void excluirPaciente(Long id) {
        this.ativo = false;
    }
}
