package med.voll.api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.MedicoAtualizacaoDTO;
import med.voll.api.dto.MedicoCadastroDTO;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(MedicoCadastroDTO medicoCadastroDTO) {
        this.ativo = true;
        this.crm = medicoCadastroDTO.crm();
        this.email = medicoCadastroDTO.email();
        this.nome = medicoCadastroDTO.nome();
        this.telefone = medicoCadastroDTO.telefone();
        this.especialidade = medicoCadastroDTO.especialidade();
        this.endereco = new Endereco(medicoCadastroDTO.endereco());
    }


    public void atualizarInformacoes(MedicoAtualizacaoDTO dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if(dados.endereco() != null){
            this.endereco.atualizarInformacoesEndereco(dados.endereco());
        }


        }

    public void excluir() {
        this.ativo = false;
    }
}
