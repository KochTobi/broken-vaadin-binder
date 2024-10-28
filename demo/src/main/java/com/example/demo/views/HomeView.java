package com.example.demo.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import java.util.List;

@Route("")
public class HomeView extends VerticalLayout {

    private final Grid<ContactInfo> contactInfoGrid;

    public record ContactInfo(String name, String email) {

    }

    public HomeView() {

        add(new H1("Welcome to your new application"));
        add(new Paragraph("This is the home view"));

        contactInfoGrid = new Grid<>();
        Binder<ContactInfo> binder = new Binder<>();
        contactInfoGrid.getEditor().setBinder(binder);
        contactInfoGrid.addColumn(ContactInfo::name)
            .setHeader("Name");
        contactInfoGrid.addColumn(ContactInfo::email)
                .setHeader("Email")
            .setEditorComponent(this::renderContactInfo);
        contactInfoGrid.addComponentColumn(it -> {
            Button button = new Button("Edit");
            button.addClickListener(buttonClickEvent -> contactInfoGrid.getEditor().editItem(it));
            return button;
        });
        contactInfoGrid.setItems(List.of(
            new ContactInfo("John Doe", "john.doe@example.com"),
            new ContactInfo("John Doe", "john.doe@example.com"),
            new ContactInfo("John Doe", "john.doe@example.com"),
            new ContactInfo("John Doe", "john.doe@example.com")
        ));
        add(contactInfoGrid);

    }

    private Component renderContactInfo(ContactInfo contactInfo) {
        var textField = new TextField();
        textField.setValue(contactInfo.name());
        textField.addValueChangeListener(
            contact -> contactInfoGrid.setItems(
                List.of(new ContactInfo(contact.getValue(), contactInfo.name()))
                //more complex stuff happening
            ));
        var textFieldEmail = new TextField();
        textFieldEmail.setValue(contactInfo.email());
        textFieldEmail.addValueChangeListener(
            contact -> contactInfoGrid.setItems(
                List.of(new ContactInfo(contactInfo.email(), contact.getValue()))));
        return new Span(textField,textFieldEmail);

    }
}
