package com.example.application.views.Aluno;

import com.example.application.entidades.Aluno;
import com.example.application.repositories.AlunoRepository;
import com.example.application.repositories.postgres.AlunoRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Alunos")
@Route(value = "aluno",layout = MainLayout.class)
public class AlunoView 
                extends VerticalLayout{
    private TextField nomeField;
    private TextField cpfAField;
    private TextField cpfRField;
    private DatePicker nascimento;
    private DatePicker.DatePickerI18n fmt;
    private int id;
    private Boolean teste = true;
    private Button salvarButton;
    private Button limparButton;
    private Grid<Aluno> grid 
                        = new Grid<>(Aluno.class, false);
    private AlunoRepository repository 
                        = new AlunoRepositoryImpl();

    public AlunoView(){
        limparButton = new Button("Cancelar");
        salvarButton = new Button("Salvar");
        nomeField = new TextField("Nome:");

        nomeField.setClearButtonVisible(true);
        cpfAField = new TextField("CPF Aluno:");
        cpfAField.setClearButtonVisible(true);
        cpfAField.setHelperText("Apenas números");

        cpfRField = new TextField("CPF Responsável:");
        cpfRField.setClearButtonVisible(true);
        cpfRField.setHelperText("Apenas números");
        
        nascimento = new DatePicker("Data de nascimento: ");
        fmt = new DatePicker.DatePickerI18n();
        fmt.setDateFormat("dd-MM-yyyy");
        

       limparButton.addClickListener(ev->{
        nomeField.setValue("");
        cpfAField.setValue("");
        cpfRField.setValue("");
        nascimento.setValue(null);
       });

       salvarButton.addClickListener(ev -> {
            String nm = nomeField.getValue();
            String cpfa = cpfAField.getValue();
            String cpfr = cpfRField.getValue();

            if(nm == "" ){
                Notification.show("Insira seu nome!");
                nomeField.focus();
                return;
            }

            if(cpfa == "" || cpfa.length() < 11 || cpfa.length() > 11 ){
                Notification.show("Insira o CPF do aluno!");
                cpfAField.focus();
                return;
            }

            if(cpfr == "" || cpfr.length() < 11 || cpfr.length() > 11  ){
                Notification.show("Insira o CPF do responsável!");
                cpfRField.focus();
                return;
            }
            
            Aluno novo = new Aluno();
            novo.setNome(nomeField.getValue());
            nascimento.setI18n(fmt);
            novo.setNascimento(nascimento.getValue().toString());
            novo.setCpfAluno(cpfAField.getValue());
            novo.setCpfResp(cpfRField.getValue());

            if(teste == true ){
                repository.inserir(novo);
                Notification.show("Salvo!!");
            } else {
                novo.setId(id);
                repository.editar(novo);
                Notification.show("Alteração salva!");
                teste = true;
            }

            grid.setItems(repository.listar());
            limparButton.click();
        });
        salvarButton.addClickShortcut(Key.ENTER);
        grid.addColumn(Aluno::getNome)
            .setHeader("Nome");
        grid.addColumn(Aluno::getCpfAluno)
            .setHeader("CPF");
        grid.addColumn(Aluno::getCpfResp)
            .setHeader("CPF Responsável");
        grid.addColumn(Aluno::getNascimento)
            .setHeader("Data de Nascimento");

        
        grid.addComponentColumn(c->{
            Button del = new Button("X");
            del.addClickListener(ev->{
                repository.excluir(c.getId());
                grid.setItems(repository.listar());
            });
            return del;
        });

        grid.addComponentColumn(c->{
            Button edi = new Button("Editar");
            edi.addClickListener(ev->{
                nomeField.focus();
                nomeField.setValue(c.getNome());
                cpfAField.setValue(c.getCpfAluno());
                cpfRField.setValue(c.getCpfResp());
                id = c.getId();
                teste = false;
            });
            return edi;
        });

        HorizontalLayout hl = new HorizontalLayout();
        hl.add(salvarButton, limparButton);
        grid.setItems(repository.listar());
        add(nomeField, cpfAField, cpfRField, nascimento, hl, grid);
    }
}
