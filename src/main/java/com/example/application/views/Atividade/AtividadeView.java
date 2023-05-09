package com.example.application.views.Atividade;

import com.example.application.entidades.Atividade;
import com.example.application.repositories.AtividadeRepository;
import com.example.application.repositories.postgres.AtividadeRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Atividades")
@Route(value = "atividade", layout = MainLayout.class)
public class AtividadeView extends VerticalLayout{
    private TextField nomefield;
    private IntegerField cargahorariafield;
    private Button salvarButton;
    private Button limparButton;
    private Grid<Atividade> grid
                        = new Grid<>(Atividade.class, false);
    private AtividadeRepository repository
                        = new AtividadeRepositoryImpl();
    private Boolean teste = true;
    private int id;
   
 
 
    public AtividadeView() {
        nomefield = new TextField("Atividade: ");
        nomefield.setHelperText("Ex.: Pula-Pula, Parque, Almoço");
        cargahorariafield = new IntegerField("Carga Horaria");
        cargahorariafield.setHelperText("Tempo de duração");
        salvarButton = new Button("Salvar");
        limparButton = new Button("Cancelar");
 
        limparButton.addClickListener(ev ->{
            nomefield.setValue("");
            cargahorariafield.setValue(null);
        });
 
        salvarButton.addClickListener(ev -> {
            Integer ch = cargahorariafield.getValue();
 
            String nm = nomefield.getValue();
            if(nm == "" ){
                Notification.show("Insira o nome!");
                nomefield.focus();
                return;
            }
 
            if(ch == null || ch <= 0 ){
                Notification.show("Carga horária inválida!!");
                cargahorariafield.focus();
                return;
            }
           
            Atividade novo = new Atividade();
            novo.setNome(nomefield.getValue());
            novo.setCargaHoraria(cargahorariafield.getValue());
 
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
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(salvarButton, limparButton);
 
        grid.addColumn(Atividade::getNome)
            .setHeader("Atividade");
        grid.addColumn(Atividade::getCargaHoraria)
            .setHeader("Carga Horária");
 
        grid.addComponentColumn(c -> {
            Button del = new Button("X");
            del.addClickListener(ev ->{
                        repository.excluir(c.getId());
                        grid.setItems(repository.listar());
            });
            return del;
           
        });
        grid.addComponentColumn(c ->{
            Button editaButton = new Button("Editar");
            editaButton.addClickListener(ev ->{
                nomefield.focus();
                nomefield.setValue(c.getNome());
                cargahorariafield.setValue(c.getCargaHoraria());
                id = c.getId();
                teste = false;
            });
            return editaButton;
        });
        grid.setItems(repository.listar());
 
        add(nomefield, cargahorariafield, hl, grid);
    }
}
