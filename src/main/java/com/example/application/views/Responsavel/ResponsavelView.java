package com.example.application.views.Responsavel;

import com.example.application.entidades.Responsavel;
import com.example.application.repositories.ResponsavelRepository;
import com.example.application.repositories.postgres.ResponsavelRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Responsável")
@Route(value = "responsavel", layout = MainLayout.class)
public class ResponsavelView
                extends VerticalLayout {
    private TextField nomeField;
    private TextField cpfAField;
    private TextField cpfRField;
    private TextField enderecoField;
    private TextField telefoneField;
    private TextField emailField;
    private int id;
    private Boolean teste = true;
    private Button salvarButton;
    private Button limparButton;
    private Grid<Responsavel> grid
                    = new Grid<>(Responsavel.class, false);
    private ResponsavelRepository repository
                    = new ResponsavelRepositoryImpl();

    
    public ResponsavelView(){
        HorizontalLayout cpf = new HorizontalLayout();
        HorizontalLayout telEmail = new HorizontalLayout();
        HorizontalLayout hl = new HorizontalLayout();
        HorizontalLayout nomeEnd = new HorizontalLayout();

        nomeField = new TextField("Nome:");
        nomeField.setClearButtonVisible(true);

        cpfAField = new TextField("CPF Aluno:");
        cpfAField.setClearButtonVisible(true);
        cpfAField.setHelperText("Apenas números");

        cpfRField = new TextField("CPF Responsável:");
        cpfRField.setClearButtonVisible(true);
        cpfRField.setHelperText("Apenas números");

        enderecoField = new TextField("Endereço");
        enderecoField.setClearButtonVisible(true);
        enderecoField.setPrefixComponent(VaadinIcon.MAP_MARKER.create());
        enderecoField.setHelperText("Rua, número, Bairro");

        telefoneField = new TextField("Telefone");
        telefoneField.setPattern( "^?[(]?[0-9]{2}[)]?[-s.]?[0-9]{5}[-s.]?[0-9]{4,6}$");
        telefoneField.setHelperText("(99) 999999999");
        telefoneField.setClearButtonVisible(true);

        emailField = new TextField("Email");
        emailField.setClearButtonVisible(true);

        limparButton = new Button("Cancelar");
        salvarButton = new Button("Salvar");

        nomeEnd.add(nomeField, enderecoField);
        hl.add(salvarButton, limparButton);
        cpf.add(cpfAField, cpfRField);
        telEmail.add(telefoneField, emailField);

        limparButton.addClickListener(ev->{
            nomeField.setValue("");
            cpfAField.setValue("");
            cpfRField.setValue("");
            enderecoField.setValue("");
            telefoneField.setValue("");
            emailField.setValue("");
        });

        salvarButton.addClickListener(ev->{
            String nm = nomeField.getValue();
            String cpfa = cpfAField.getValue();
            String cpfr = cpfRField.getValue();
            String end = enderecoField.getValue();
            String tel = telefoneField.getValue();
            String em = emailField.getValue();

            if(nm == "" ){
                Notification.show("Insira o nome!");
                nomeField.focus();
                return;
            }

            if(cpfa == "" || cpfa.length() < 11 || cpfa.length() > 11  ){
                Notification.show("Insira o CPF do aluno!");
                cpfAField.focus();
                return;
            }

            if(cpfr == "" || cpfr.length() < 11 || cpfr.length() > 11 ){
                Notification.show("Insira o CPF do responsável!");
                cpfRField.focus();
                return;
            }

            if(end == "" ){
                Notification.show("Insira o endereço!");
                enderecoField.focus();
                return;
            }

            if(tel == "" ){
                Notification.show("Insira o telefone!");
                telefoneField.focus();
                return;
            }

            if(em == "" ){
                Notification.show("Insira o email!");
                emailField.focus();
                return;
            }

            Responsavel novo = new Responsavel();
            novo.setCpfAluno(cpfAField.getValue());
            novo.setCpfResp(cpfRField.getValue());
            novo.setEmail(emailField.getValue());
            novo.setEndereco(enderecoField.getValue());
            novo.setNome(nomeField.getValue());
            novo.setTelefone(telefoneField.getValue());

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
        
        grid.addColumn(Responsavel::getNome)
            .setHeader("Nome");
        grid.addColumn(Responsavel::getCpfAluno)
            .setHeader("CPF Aluno");
        grid.addColumn(Responsavel::getCpfResp)
            .setHeader("CPF");
        grid.addColumn(Responsavel::getTelefone)
            .setHeader("Telefone");
        grid.addColumn(Responsavel::getEmail)
            .setHeader("E-mail");
        grid.addColumn(Responsavel::getEndereco)
            .setHeader("Endereço");
        
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
                enderecoField.setValue(c.getEndereco());
                telefoneField.setValue(c.getTelefone());
                emailField.setValue(c.getEmail());
                id = c.getId();
                teste = false;
            });
            return edi;
        });
        grid.setItems(repository.listar());
        add(nomeEnd,cpf,telEmail, hl, grid);
    }
}
