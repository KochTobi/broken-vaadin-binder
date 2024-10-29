package com.example.demo.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route("")
public class HomeView extends VerticalLayout {

    private final Grid<ContactInfo> contactInfoGrid;

    public record ContactInfo(String name, String email) {

    }

    public HomeView() {

        add(new H1("Welcome to your new application"));
        add(new Paragraph("This is the home view"));

        contactInfoGrid = new Grid<>();
        Binder<ContactInfo> binder = new Binder<>(ContactInfo.class);
        contactInfoGrid.getEditor().setBinder(binder);
        contactInfoGrid.addColumn(ContactInfo::name)
            .setHeader("Name");
        contactInfoGrid.addColumn(ContactInfo::email)
                .setHeader("Email");
        contactInfoGrid.addComponentColumn(it -> {
            Button button = new Button("Edit");
            button.addClickListener(buttonClickEvent -> contactInfoGrid.getEditor().editItem(it));
            return button;
        }).setEditorComponent(this::renderContactInfo);
        contactInfoGrid.setItems(Stream.of(
            new ContactInfo("John Doe1", "john.doe@example.com"),
            new ContactInfo("John Doe2", "john.doe@example.com"),
            new ContactInfo("John Doe3", "john.doe@example.com"),
            new ContactInfo("John Doe4", "john.doe@example.com")
        ).collect(Collectors.toCollection(() -> new ArrayList<>())));
        add(contactInfoGrid);

    }

    private Component renderContactInfo(ContactInfo contactInfo) {
        var textField = new TextField();
        textField.setValue(contactInfo.name());
        var textFieldEmail = new TextField();
        textFieldEmail.setValue(contactInfo.email());
        var okButton = new Button("Save");
        okButton.addClickListener(buttonClickEvent -> {
            var listDataView = contactInfoGrid.getListDataView();
            listDataView.addItemBefore(
                new ContactInfo(textField.getValue(), textFieldEmail.getValue()), contactInfo);
            listDataView.removeItem(contactInfo);
            listDataView.refreshAll();
            contactInfoGrid.getEditor().closeEditor();
        });
        return new Span(textField,textFieldEmail,okButton);


    }
}
