package com.example.application.views.Funcionario;

import com.example.application.entidades.Funcionario;
import com.example.application.repositories.FuncionarioRepository;
import com.example.application.repositories.postgres.FuncionarioRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Funcionário")
@Route(value = "funcionario", layout = MainLayout.class)
public class FuncionarioView 
                extends VerticalLayout{
    private TextField nomeField;
    private TextField cpfField;
    private TextField telefoneField;
    private EmailField emailField;
    private NumberField salarioField;
    private CheckboxGroup<String> categoria
                                    = new CheckboxGroup<>();
    private int id;
    private Boolean teste = true;
    private Button salvarButton;
    private Button limparButton; 
    private Grid<Funcionario> grid 
                            = new Grid<>(Funcionario.class, false);
    private FuncionarioRepository repository
                            = new FuncionarioRepositoryImpl();
                            
    public FuncionarioView(){
        salvarButton = new Button("Salvar");
        limparButton = new Button("Cancelar");

        telefoneField = new TextField("Telefone");
        telefoneField.setPattern( "^?[(]?[0-9]{2}[)]?[-s.]?[0-9]{5}?[-]?[-s.]?[0-9]{4,6}$");
        telefoneField.setHelperText("Formato: (99) 9 9999-9999");
        telefoneField.setClearButtonVisible(true);

        nomeField = new TextField("Nome:");
        nomeField.setClearButtonVisible(true);

        cpfField = new TextField("CPF:");
        cpfField.setClearButtonVisible(true);
        cpfField.setHelperText("Apenas números");

        emailField = new EmailField("E-mail");
        emailField.getElement().setAttribute("name", "email");
        emailField.setPlaceholder("username@example.com");
        emailField.setErrorMessage(
                "Entre com um e-mail válido");
        emailField.setClearButtonVisible(true);
        emailField.setPattern("^.+@gmail\\.com$");

        categoria.setLabel("Categoria: ");
        categoria.setItems("Professora(o)", "Coordenadora(o)", "Diretora(o)", "Faxineira(o)", 
        "Cozinheira(o)", "Zeladora(o)", "Técnico de desenvolvimento infantil", "Cuidadora(o)");
        categoria.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        salarioField = new NumberField("Salário");
        salarioField.setClearButtonVisible(true);
        
        limparButton.addClickListener(ev -> {
            nomeField.setValue("");
            cpfField.setValue("");
            telefoneField.setValue("");
            emailField.setValue("");
            salarioField.setValue(null);
        });

        salvarButton.addClickListener(ev->{
            String nm = nomeField.getValue();
            String cpf = cpfField.getValue();
            String tel = telefoneField.getValue();
            String em = emailField.getValue();
            Double sal = salarioField.getValue();

            if(nm == "" ){
                Notification.show("Insira o nome!");
                nomeField.focus();
                return;
            }

            if(cpf == "" || cpf.length() < 11 || cpf.length() > 11  ){
                Notification.show("Insira o CPF do aluno!");
                cpfField.focus();
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

            if(sal == null || sal<=0){
                Notification.show("Insira o salário!");
                return;
            }

            Funcionario novo = new Funcionario();
            novo.setCpf(cpfField.getValue());
            novo.setEmail(emailField.getValue());
            novo.setNome(nomeField.getValue());
            novo.setTelefone(telefoneField.getValue());
            novo.setSalario(salarioField.getValue());
            novo.setCategoria(categoria.getValue().toString());

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
            categoria.deselectAll();
            limparButton.click();
        });
        salvarButton.addClickShortcut(Key.ENTER);

        grid.addColumn(Funcionario::getNome)
            .setHeader("Nome");
        grid.addColumn(Funcionario::getCpf)
            .setHeader("CPF");
        grid.addColumn(Funcionario::getCategoria)
            .setHeader("Categoria");
        grid.addColumn(Funcionario::getTelefone)
            .setHeader("Telefone");
        grid.addColumn(Funcionario::getEmail)
            .setHeader("E-mail");
        grid.addColumn(Funcionario::getSalario)
            .setHeader("Salário");

        grid.addComponentColumn(c ->{
            Button del = new Button("X");
            del.addClickListener(ev ->{
                repository.excluir(c.getId());
                grid.setItems(repository.listar());
            });
            return del;
        });

        grid.addComponentColumn(c ->{
            Button edi = new Button("Editar");
            edi.addClickListener(ev ->{
                nomeField.focus();
                nomeField.setValue(c.getNome());
                cpfField.setValue(c.getCpf());
                telefoneField.setValue(c.getTelefone());
                emailField.setValue(c.getEmail());
                salarioField.setValue(c.getSalario());
                categoria.deselectAll();
                id = c.getId();
                teste = false;
            });
            return edi;
        });

        HorizontalLayout telEm = new HorizontalLayout();
        HorizontalLayout NomCp = new HorizontalLayout();

        NomCp.add(nomeField, cpfField);
        telEm.add(telefoneField, emailField);
        grid.setItems(repository.listar());
        add( NomCp, telEm, categoria, salarioField, salvarButton, grid);
    }
    
}
