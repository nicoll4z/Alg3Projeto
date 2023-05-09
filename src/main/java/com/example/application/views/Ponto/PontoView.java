package com.example.application.views.Ponto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.application.entidades.Ponto;
import com.example.application.repositories.PontoRepository;
import com.example.application.repositories.postgres.PontoRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Ponto")
@Route(value = "ponto", layout = MainLayout.class)
public class PontoView 
        extends VerticalLayout{
    private TextField cpfField;
    private Button salvarButton;
    private Button limparButton;
    private LocalDateTime data;
    private DateTimeFormatter fmt;
    PontoRepository repository =
                        new PontoRepositoryImpl();
    Grid<Ponto> grid
                = new Grid<>(Ponto.class, false);

    public PontoView(){
        cpfField = new TextField("CPF: ");
        salvarButton = new Button("Adicionar chegada");
        limparButton = new Button("Cancelar");
        fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        limparButton.addClickListener(ev ->{
            cpfField.setValue("");
        });

        salvarButton.addClickListener(ev ->{
            String cpfteste = cpfField.getValue();

            if (cpfteste == "" || cpfteste.length() < 11 || cpfteste.length()>11 ){
                Notification.show("Insira o cpf!");
                cpfField.focus();
                return;
            }

            data = LocalDateTime.now();
            Ponto novo = new Ponto();
            novo.setCpf(cpfField.getValue());
            novo.setChegada(data.format(fmt));
            repository.inserir(novo);
            Notification.show("Chegada Salva!");
            grid.setItems(repository.listar());
            limparButton.click();
        });
        salvarButton.addClickShortcut(Key.ENTER);
        
        grid.addColumn(Ponto::getCpf)
            .setHeader("CPF");
        grid.addColumn(Ponto::getChegada)
            .setHeader("Chegada");
        grid.addColumn(Ponto::getSaida)
            .setHeader("Saida");

      
        grid.addComponentColumn(c ->{
            Button saida = new Button("Adicionar Saída");
            saida.setDisableOnClick(true);
            saida.addClickListener(ev ->{
                data = LocalDateTime.now();
                c.setSaida(data.format(fmt));
                repository.editar(c);
                grid.setItems(repository.listar());
                Notification.show("Saída Salva!");
            });
            return saida;
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
        add(cpfField,hl, grid);
    }
}
