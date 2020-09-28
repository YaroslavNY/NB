package NB;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;


@Theme("mytheme")

public class MyUI extends UI {

    HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
//    Grid<MyUI.Note> grid = new Grid<>();
//    ArrayList<MyUI.Note> notes = new ArrayList<>();
//    ListDataProvider<MyUI.Note> dataProvider = DataProvider.ofCollection(notes);
//    Set<MyUI.Note> selected;

    Grid<NotesEntity> grid = new Grid<>(NotesEntity.class);
    NotesDAO notesDAO = new NotesDAO();
    List<NotesEntity> notesEntities = new ArrayList<>();
    ListDataProvider<NotesEntity> notesEntityListDataProvider = DataProvider.ofCollection(notesEntities);
    Set<NotesEntity>notesEntitySelected;


    public void saveNote(NotesEntity notes) {
        notesDAO.save(notes);
    }

    public void deleteNotes(NotesEntity notes) {
        notesDAO.delete(notes);
    }

    public void updateNote(NotesEntity notes) {
        notesDAO.update(notes);
    }

//    public List <NotesEntity> getNotesEntities (){
//    return notesEntities = notesDAO.getNotesList();
//    }



    @Override
    public void init(VaadinRequest request) {


//        grid.addColumn(MyUI.Note::getName).setCaption("Name").setMaximumWidth(250.0);
//        grid.addColumn(MyUI.Note::getText).setCaption("Text");


//        grid.addColumn(notesEntity.getName()).setCaption("Name");
//        grid.addColumn(notesEntity.getText()).setCaption("Text");


//        notesEntities = getNotesEntities();
//        grid.setDataProvider(notesEntityListDataProvider);
        

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        splitPanel.setSizeFull();
        grid.setSizeFull();
        grid.setBodyRowHeight(50);

        splitPanel.setFirstComponent(grid);

        setContent(splitPanel);

        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Label label = new Label("Put Your Notes Here");
        TextField textField = new TextField("Name");
        TextField search = new TextField();
        search.setPlaceholder("Search...");
        TextArea textArea = new TextArea("Text");
        textField.setSizeFull();
        textArea.setSizeFull();
        Button save = new Button("Save");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button cancel = new Button("Cancel");
        Button delete = new Button("Delete");
        Button clearFilter = new Button(VaadinIcons.CLOSE);
        clearFilter.setDescription("Clear the filter");

        clearFilter.addClickListener(e -> {
            search.clear();
        });

        grid.addSelectionListener(event -> {
            notesEntitySelected = event.getAllSelectedItems();
//            selected = event.getAllSelectedItems();
//            Notification.show(selected.size() + " items selected");
            Notification.show(notesEntitySelected.size() + " items selected");
        });

        CssLayout filtering = new CssLayout();
        filtering.addComponents(search, clearFilter);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        horizontalLayout.addComponents(save, cancel, new Panel(), delete);

        verticalLayout.addComponents(label, textField, textArea, horizontalLayout, new Label(), filtering);

        splitPanel.setSecondComponent(verticalLayout);

//        search.addValueChangeListener(event -> {
//            dataProvider.setFilter(MyUI.Note::getName, name -> {
//                String nameLower = name == null ? ""
//                        : name.toLowerCase(Locale.ENGLISH);
//                String filterLower = event.getValue()
//                        .toLowerCase(Locale.ENGLISH);
//                return nameLower.contains(filterLower);
//            });
//        });
        search.addValueChangeListener(event -> {
            notesEntityListDataProvider.setFilter(NotesEntity::getName, name -> {
                String nameLower = name == null ? ""
                        : name.toLowerCase(Locale.ENGLISH);
                String filterLower = event.getValue()
                        .toLowerCase(Locale.ENGLISH);
                return nameLower.contains(filterLower);
            });
        });

        delete.addClickListener(click -> {
            try {

                for(NotesEntity x : notesEntitySelected){
                    deleteNotes(x);
                }
                notesEntitySelected.clear();
                grid.setDataProvider(notesEntityListDataProvider);
//                notes.removeAll(selected);
//                grid.setDataProvider(dataProvider);
//                selected.clear();

            } catch (Exception e) {
            }
        });


        cancel.addClickListener(click -> {
            textField.clear();
            textArea.clear();
        });

        save.addClickListener(click -> {
            if (textArea.getValue().equals("")) {
                return;

            } else if (!textField.getValue().equals("")) {

                NotesEntity currentNotesEntity = new NotesEntity(textField.getValue(), textArea.getValue());
                saveNote(currentNotesEntity);

//                MyUI.Note currentNote = new MyUI.Note(textField.getValue(), textArea.getValue());
//                notes.add(currentNote);

            } else {

                NotesEntity currentNotesEntity = new NotesEntity(textField.getValue(), textArea.getValue());
                saveNote(currentNotesEntity);

//                MyUI.Note currentNote = new MyUI.Note(textArea.getValue(), textArea.getValue());
//                notes.add(currentNote);
            }
            grid.setDataProvider(notesEntityListDataProvider);
//            grid.setDataProvider(dataProvider);
            textArea.clear();
            textField.clear();

        });

        grid.addContextClickListener(e -> {
        });
        grid.addItemClickListener(e -> {
        });

    }
    @WebServlet(urlPatterns = "/*",name = "MyUIServlet", asyncSupported =  true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet{




    }



//    class Note {
//
//        String name;
//        String text;
//
//        Note(String name, String text) {
//            this.name = name;
//            this.text = text;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getText() {
//            return text;
//        }
//    }

    }



