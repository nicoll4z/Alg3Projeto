package com.example.application.views.Matricula;

import com.example.application.entidades.Matricula;
import com.example.application.repositories.MatriculaRepository;
import com.example.application.repositories.postgres.MatriculaRepositoryImpl;
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
@PageTitle("Matricula")
@Route(value = "matricula", layout = MainLayout.class)

public class MatriculaView
                extends VerticalLayout{
    private TextField cpfField;
    private Button salvarButton;
    private Button limparButton;
    private TextField dataField;
    private Boolean teste = true;
    private int id;
    MatriculaRepository repository =
                        new MatriculaRepositoryImpl();
    Grid<Matricula> grid
                = new Grid<>(Matricula.class, false);

    public MatriculaView(){
        cpfField = new TextField("CPF: ");
        cpfField.setHelperText("Apenas números");
        cpfField.setClearButtonVisible(true);

        salvarButton = new Button("Gerar matricula");

        limparButton = new Button("Cancelar");

        dataField = new TextField("Data de entrada: ");
        dataField.setPrefixComponent(VaadinIcon.CALENDAR.create());
        dataField.setHelperText("dd/mm/aaaa");
        dataField.setClearButtonVisible(true);
        

        limparButton.addClickListener(ev ->{
            cpfField.setValue("");
            dataField.setValue("");
            
        });

        salvarButton.addClickListener(ev ->{
            String cpfteste = cpfField.getValue();

            if (cpfteste == "" || cpfteste.length() <11 || cpfteste.length()>11){
                Notification.show("Insira um CPF válido!");
                cpfField.focus();
                return;
            }

            
            Matricula novo = new Matricula();
            novo.setCpf(cpfField.getValue());
            novo.setData(dataField.getValue());
            novo.setNumMatricula(dataField.getValue().replaceAll("/", "") + cpfField.getValue().substring(8));

            if(teste == true){
                repository.inserir(novo);
                Notification.show("Matricula gerada com sucesso!");
            }else{
                novo.setId(id);
                repository.editar(novo);
                Notification.show("Alteração salva!");
                teste = true;
            }
           
            grid.setItems(repository.listar());
            limparButton.click();
        });
        salvarButton.addClickShortcut(Key.ENTER);
        
        grid.addColumn(Matricula::getCpf)
            .setHeader("CPF");
        grid.addColumn(Matricula::getData)
            .setHeader("Data de entrada");
        grid.addColumn(Matricula::getNumMatricula)
            .setHeader("Número da matricula");

      
        grid.addComponentColumn(c ->{
            Button edita = new Button("Editar");
            edita.addClickListener(ev ->{
                cpfField.focus();
                cpfField.setValue(c.getCpf());
                dataField.setValue(c.getData());
                id = c.getId();
                teste = false;
            });
            return edita;
        });
        
        grid.addComponentColumn(c ->{
            Button del = new Button("X");
            del.addClickListener(ev ->{
                repository.excluir(c.getCpf());
                Notification.show("Excluido com sucesso");
                grid.setItems(repository.listar());
            });
            return del;
        });

        
        HorizontalLayout hl = new HorizontalLayout();
        grid.setItems(repository.listar());
        hl.add(salvarButton, limparButton);
        add(cpfField, dataField, hl, grid);
    }
}
