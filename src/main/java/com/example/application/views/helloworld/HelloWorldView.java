package com.example.application.views.helloworld;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.Lumo;

@PageTitle("Seja bem-vindo!")
@Route(value = "bemvindo", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldView() {
        name = new TextField("Digite seu nome: ");
        sayHello = new Button("Enviar");
        sayHello.addClickListener(e -> {
            Notification.show("Olá, " + name.getValue() + ". Aproveite nossas funcionalidades!");
        });
        sayHello.addClickShortcut(Key.ENTER);
        Button toggleButton = new Button("Tema", click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
 
            if (themeList.contains(Lumo.DARK)) {
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });
        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello, toggleButton);
        add(name, sayHello, toggleButton);

    }

}
