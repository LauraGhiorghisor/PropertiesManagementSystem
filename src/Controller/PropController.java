package Controller;

import Model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.StringTokenizer;

public class PropController {


    //
    public PropController() {
    }

    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    //delete
    @FXML
    private Button logout;
    @FXML
    private HBox bar, content;

    @FXML
    private TextField pTypeField, pAddressField, pNoRoomsField, pSellingPriceField, pSoldPriceField, pFloorField, pMCField, pNoFloorsField, pGardenField, pGarageField, pRefField, searchField,
            bNameField, bAddressField, bPhoneField, bEmailField, bWebField, bUserField, bPasswordField;
    @FXML
    private Text currentUser;

    @FXML
    private TableView<Property> tableView;
    @FXML
    private TableView<Branch> branchTableView;
    @FXML
    private TableColumn addressCol, noRoomsCol, sellingPriceCol, soldPriceCol, floorsCol, mcCol, floorCol, gardenCol, garageCol, typeCol, refCol,
            bNameCol, bAddressCol, bPhoneCol, bEmailCol, bWebCol, bUserCol, bPasswordCol;

    @FXML
    Hyperlink link;
    private String logged;

    //Log out deletes session and directs the user to log in page
    public void logout() {
        Parent homePage = null;
        try {
            Files.deleteIfExists(Paths.get("Files/session.dat"));
            homePage = FXMLLoader.load(getClass().getResource("/FXMLview/Login.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        logout.getScene().setRoot(homePage);
    }

//    Only deletes session file.
    public void deleteSession() {
        try {
            Files.deleteIfExists(Paths.get("Files/session.dat"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Exit logs the user out and terminates the programme
    public void exit(ActionEvent event) {
        //https://www.programcreek.com/java-api-examples/?class=javafx.scene.control.Alert.AlertType&method=CONFIRMATION
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit?");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                Files.deleteIfExists(Paths.get("Files/session.dat"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        } else {
            event.consume();
        }
    }

    //Help displays user guide
    public void help() {
        addWindow("/FXMLview/Help.fxml", "Help");
    }

    //    Used to display on-screen feedback, loads FXML files passed as arguments
    public void addWindow(String path, String title) {
        System.out.println("2");
        try {
            Parent addProperty = FXMLLoader.load(getClass().getResource(path));
            Stage stage = new Stage();
            Scene add = new Scene(addProperty);
            stage.setTitle(title);
            stage.setScene(add);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Adds a new property to the view (and file), using the field values.
    Checks for input types and shows on screen feedback for types that do not match.
    Checks for empty fields and notifies the user that they must be filled in.
     */
    public void addProperty(ActionEvent e) {

        try {
            boolean exception = false;
            MyFile file = new MyFile("Files/branches.dat");
            ArrayList<Branch> branchlist = new ArrayList<Branch>();
            branchlist = file.readFrom();
            Branch branch = new Branch();

            for (int i = 0; i < branchlist.size(); i++) {
                if (branchlist.get(i).getName().equals(currentUser.getText())) branch = branchlist.get(i);
            }

            ObservableList<Property> fakelist;
            fakelist = tableView.getItems();
            if (pTypeField.getText().equals("")) {
                addWindow("/FXMLview/MissingType.fxml", "Missing type");
                exception = true;
            } else if (pTypeField.getText().equalsIgnoreCase("flat")) {
                if (pNoRoomsField.getText().isEmpty() || pRefField.getText().isEmpty() || pAddressField.getText().isEmpty() ||
                        pSellingPriceField.getText().isEmpty() || pSoldPriceField.getText().isEmpty() ||
                        pFloorField.getText().isEmpty() || pMCField.getText().isEmpty()
                ) {
//                        System.out.println("empty fields");
                    throw new NumberFormatException();
                } else {
                    Flat f = new Flat(Integer.parseInt(pRefField.getText()), pAddressField.getText(), Integer.parseInt(pNoRoomsField.getText()),
                            Double.parseDouble(pSellingPriceField.getText()), Double.parseDouble(pSoldPriceField.getText()), Integer.parseInt(pFloorField.getText()),
                            Double.parseDouble(pMCField.getText()));
                    branch.addProperty(f);
                    fakelist.add(f);
                    displayTable(fakelist);
                    writeChangesToFile(branch);
                }
            } else if (pTypeField.getText().equalsIgnoreCase("house")) {
                if (pSoldPriceField.getText().equals("") || pRefField.getText().equals("") || pAddressField.getText().equals("") ||
                        pSellingPriceField.getText().equals("") || pNoRoomsField.getText().equals("") || pNoFloorsField.getText().equals("") ||
                        pGarageField.getText().equals("") || pGardenField.getText().equals("")) {
//                    System.out.println("it is emplty");
                    throw new NumberFormatException();
                } else if (!pGarageField.getText().equalsIgnoreCase("yes") && !pGarageField.getText().equalsIgnoreCase("no")) {
                    addWindow("/FXMLview/YesNo.fxml", "Specify YES/NO");
                    exception = true;
                } else if (!pGardenField.getText().equalsIgnoreCase("yes") && !pGardenField.getText().equalsIgnoreCase("no")) {
                    addWindow("/FXMLview/YesNo.fxml", "Specify YES/NO");
                    exception = true;
                } else {
                    if (pGardenField.getText().equalsIgnoreCase("yes")) pGardenField.setText("YES");
                    if (pGardenField.getText().equalsIgnoreCase("no")) pGardenField.setText("NO");
                    if (pGarageField.getText().equalsIgnoreCase("yes")) pGarageField.setText("YES");
                    if (pGarageField.getText().equalsIgnoreCase("no")) pGarageField.setText("NO");
                    House h = new House(Integer.parseInt(pRefField.getText()), pAddressField.getText(), Integer.parseInt(pNoRoomsField.getText()), Double.parseDouble(pSellingPriceField.getText()),
                            Double.parseDouble(pSoldPriceField.getText()), Integer.parseInt(pNoFloorsField.getText()), pGardenField.getText(), pGarageField.getText());
                    branch.addProperty(h);
                    fakelist.add(h);
                    displayTable(fakelist);
                    writeChangesToFile(branch);

                }
            }
// Clears fields only if the Add has been successful.
            if (exception == false) {
                pTypeField.clear();
                pAddressField.clear();
                pRefField.clear();
                pNoFloorsField.clear();
                pNoRoomsField.clear();
                pFloorField.clear();
                pMCField.clear();
                pGardenField.clear();
                pGarageField.clear();
                pSellingPriceField.clear();
                pSoldPriceField.clear();
            }

        } catch (NumberFormatException er) {
            NumberFormatError error = new NumberFormatError(er);
            error.getMessage();
        } catch (NullPointerException error) {
            error.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // Returns current branch data.
    public Branch getCurrentBranch() {
        MyFile file = new MyFile("Files/branches.dat");
        ArrayList<Branch> branchlist = new ArrayList<Branch>();
        try {
            branchlist = file.readFrom();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Branch branch = new Branch();
        for (int i = 0; i < branchlist.size(); i++) {
            if (branchlist.get(i).getName().equals(currentUser.getText())) branch.updateBranch(branchlist.get(i));
        }
        return branch;
    }

    //Deletes property from view and file.
    public void deleteProperty() {
        try {
            ObservableList<Property> list, selected;
            list = tableView.getItems();
            selected = tableView.getSelectionModel().getSelectedItems();
            for (Property p : selected) {
                list.remove(p);
            }
            ArrayList<Property> newList = new ArrayList<Property>();
            for (int i = 0; i < list.size(); i++)
                newList.add(list.get(i));
            Branch branch = getCurrentBranch();
            branch.setProperties(newList);
            writeChangesToFile(branch);
        } catch (NoSuchElementException e) {
            System.out.println("end of props");
            Branch branch = getCurrentBranch();
            branch.deleteProperty(branch.getProperties().get(0));
            writeChangesToFile(branch);
        }
    }

    //Creates the list of properties to be displayed, then calls the display method.
    public void generatePropertyTable(ActionEvent event) {

        //read branch
        Branch branch = getCurrentBranch();

//            Branch branch = new Branch(branchlist.get(0));
        ArrayList<Property> list = new ArrayList<Property>();
        list = branch.getProperties();
        ObservableList<Property> props = FXCollections.observableArrayList();

        for (int i = 0; i < list.size(); i++) {
            props.add(list.get(i));
        }

        displayTable(props);
    }

    //Displays all properties on screen
    public void displayTable(ObservableList props) {
        typeCol.setCellValueFactory(new PropertyValueFactory<Property, String>("type"));
        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());

        refCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("reference"));
        refCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        addressCol.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());

        noRoomsCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("noRooms"));
        noRoomsCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        sellingPriceCol.setCellValueFactory(new PropertyValueFactory<Property, Double>("sellingPrice"));
        sellingPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        soldPriceCol.setCellValueFactory(new PropertyValueFactory<Property, Double>("soldPrice"));
        soldPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        floorCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("floor"));
        floorCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        mcCol.setCellValueFactory(new PropertyValueFactory<Property, Double>("monthlyCharge"));
        mcCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        floorsCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("floors"));
        floorsCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        gardenCol.setCellValueFactory(new PropertyValueFactory<Property, String>("garden"));
        gardenCol.setCellFactory(TextFieldTableCell.forTableColumn());

        garageCol.setCellValueFactory(new PropertyValueFactory<Property, String>("garage"));
        garageCol.setCellFactory(TextFieldTableCell.forTableColumn());


        tableView.setEditable(true);
        tableView.setItems(props);
    }

    //Gets the event for address editing, saves the changes to file.
    public void editAddress(Event e) {
        TableColumn.CellEditEvent<Property, String> event = (TableColumn.CellEditEvent<Property, String>) e;
        Property p = event.getRowValue();
        Property p1 = new Property(p);
        Branch b = getCurrentBranch();
        for (int i = 0; i < b.getProperties().size(); i++)
            if (b.getProperties().get(i).getReference() == p.getReference())
                b.getProperties().get(i).setAddress(event.getNewValue());
        p.setAddress(event.getNewValue());
        writeChangesToFile(b);
    }

    //Gets the event for reference editing and saves the changes to file.
    public void editReference(Event e) {
        try {
            TableColumn.CellEditEvent<Property, Integer> event = (TableColumn.CellEditEvent<Property, Integer>) e;
            Property p = event.getRowValue();
            Branch b = getCurrentBranch();
            for (int i = 0; i < b.getProperties().size(); i++)
                if (b.getProperties().get(i).getReference() == p.getReference())
                    b.getProperties().get(i).setReference(event.getNewValue());
            p.setReference(event.getNewValue());
            writeChangesToFile(b);

        } catch (NumberFormatException error) {
            addWindow("/FXMLview/InputException.fxml", "Input Error");
        }
    }


    // Gets the event for editing the number of rooms and saves the changes to file.
    public void editNoRooms(Event e) {
        TableColumn.CellEditEvent<Property, Integer> event = (TableColumn.CellEditEvent<Property, Integer>) e;
        Property p = event.getRowValue();
        Branch b = getCurrentBranch();
        for (int i = 0; i < b.getProperties().size(); i++)
            if (b.getProperties().get(i).getReference() == p.getReference())
                b.getProperties().get(i).setNoRooms(event.getNewValue());
        p.setNoRooms(event.getNewValue());
        writeChangesToFile(b);

    }

    // Gets the event for editing the selling price and saved the changes to file.
    public void editSellingPrice(Event e) {
        TableColumn.CellEditEvent<Property, Double> event = (TableColumn.CellEditEvent<Property, Double>) e;
        Property p = event.getRowValue();
        Branch b = getCurrentBranch();
        for (int i = 0; i < b.getProperties().size(); i++)
            if (b.getProperties().get(i).getReference() == p.getReference())
                b.getProperties().get(i).setSellingPrice(event.getNewValue());
        p.setSellingPrice(event.getNewValue());
        writeChangesToFile(b);

    }

    // Gets the event for editing the sold price and saves the changes to file.
    public void editSoldPrice(Event e) {
        TableColumn.CellEditEvent<Property, Double> event = (TableColumn.CellEditEvent<Property, Double>) e;
        Property p = event.getRowValue();
        Branch b = getCurrentBranch();
        for (int i = 0; i < b.getProperties().size(); i++)
            if (b.getProperties().get(i).getReference() == p.getReference())
                b.getProperties().get(i).setSoldPrice(event.getNewValue());
        p.setSoldPrice(event.getNewValue());
        writeChangesToFile(b);

    }

    // Gets the event for editing the number of rooms and saves the changes to file.
    public void editFloors(Event e) {
        TableColumn.CellEditEvent<Property, Integer> event = (TableColumn.CellEditEvent<Property, Integer>) e;
        House h = (House) event.getRowValue();
        Branch b = getCurrentBranch();
        House h1 = null;
        for (int i = 0; i < b.getProperties().size(); i++)
            if (b.getProperties().get(i).getReference() == h.getReference()) h1 = (House) b.getProperties().get(i);
        h1.setFloors(event.getNewValue());
        h.setFloors(event.getNewValue());
        writeChangesToFile(b);

    }

    // Gets the event for editing the floor and saves the changes to file.
    public void editFloor(Event e) {
        TableColumn.CellEditEvent<Property, Integer> event = (TableColumn.CellEditEvent<Property, Integer>) e;
        Flat f = (Flat) event.getRowValue();
        Branch b = getCurrentBranch();
        Flat f1 = null;
        for (int i = 0; i < b.getProperties().size(); i++)
            if (b.getProperties().get(i).getReference() == f.getReference()) f1 = (Flat) b.getProperties().get(i);
        f1.setFloor(event.getNewValue());
        f.setFloor(event.getNewValue());
        writeChangesToFile(b);

    }


    // Gets the event for editing the monthly charge and saves the changes to file.
    public void editMonthlyCharge(Event e) {
        TableColumn.CellEditEvent<Property, Double> event = (TableColumn.CellEditEvent<Property, Double>) e;
        Flat f = (Flat) event.getRowValue();
        Branch b = getCurrentBranch();
        Flat f1 = null;
        for (int i = 0; i < b.getProperties().size(); i++)
            if (b.getProperties().get(i).getReference() == f.getReference()) f1 = (Flat) b.getProperties().get(i);
        f1.setMonthlyCharge(event.getNewValue());
        f.setMonthlyCharge(event.getNewValue());
        writeChangesToFile(b);

    }

    // Gets the event for editing the garden and saves the changes to file.
    public void editGarden(Event e) {
        TableColumn.CellEditEvent<Property, String> event = (TableColumn.CellEditEvent<Property, String>) e;
        House h = (House) event.getRowValue();
        Branch b = getCurrentBranch();
        House h1 = null;
        for (int i = 0; i < b.getProperties().size(); i++)
            if (b.getProperties().get(i).getReference() == h.getReference()) h1 = (House) b.getProperties().get(i);
        h1.setGarden(event.getNewValue());
        h.setGarden(event.getNewValue());
        writeChangesToFile(b);
    }

    // Gets the event for editing the garage and saves the changes to file.
    public void editGarage(Event e) {
        TableColumn.CellEditEvent<Property, String> event = (TableColumn.CellEditEvent<Property, String>) e;
        House h = (House) event.getRowValue();
        Branch b = getCurrentBranch();
        House h1 = null;
        for (int i = 0; i < b.getProperties().size(); i++)
            if (b.getProperties().get(i).getReference() == h.getReference()) h1 = (House) b.getProperties().get(i);
        h1.setGarage(event.getNewValue());
        h.setGarage(event.getNewValue());
        writeChangesToFile(b);

    }

    /*
    Reads the list of branches from file, finds the branch that needs to be updated and updates it to the value
    in the parameter, then writes the list of branches back to file.
    */
    public void writeChangesToFile(Branch branch) throws NumberFormatException {
        MyFile w = new MyFile("Files/branches.dat");
        try {
            ArrayList<Branch> list = w.readFrom();

            Branch cb = getCurrentBranch();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getName().equals(cb.getName())) list.get(i).updateBranch(branch);
            }

            for (int i = 0; i < list.size(); i++) {
                w.appendTo(list.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    Creates the list of flats, then calls the display method
    public void generateFlatsTable(ActionEvent event) {
        Branch branch = getCurrentBranch();
        ArrayList<Property> list = new ArrayList<Property>();
        list = branch.getProperties();
        ObservableList<Property> props = FXCollections.observableArrayList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType().equalsIgnoreCase("flat")) props.add(list.get(i));
        }

        displayTable(props);
    }

    //    Creates the list of houses, then calls the display method
    public void generateHousesTable(ActionEvent event) {
        Branch branch = getCurrentBranch();
        ArrayList<Property> list = new ArrayList<Property>();
        list = branch.getProperties();
        ObservableList<Property> props = FXCollections.observableArrayList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType().equalsIgnoreCase("house")) props.add(list.get(i));
        }
        displayTable(props);
    }

    //    Creates the list of sold properties, then calls the display method
    public void generateSoldTable(ActionEvent event) {
        Branch branch = getCurrentBranch();
        ArrayList<Property> list = new ArrayList<Property>();
        list = branch.getProperties();
        ObservableList<Property> props = FXCollections.observableArrayList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSoldPrice() > 0.0) props.add(list.get(i));
        }

        displayTable(props);
    }

    //    Creates the list of properties on sale, then calls the display method
    public void generateSalesTable(ActionEvent event) {
        Branch branch = getCurrentBranch();
        ArrayList<Property> list = new ArrayList<Property>();
        list = branch.getProperties();
        ObservableList<Property> props = FXCollections.observableArrayList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSoldPrice() == 0.0) props.add(list.get(i));
        }
        displayTable(props);
    }


    //Searches for address in the property list of the current branch. Displays results.
    public void search() {
        Branch branch = getCurrentBranch();
        ArrayList<Property> props = branch.getProperties();
        ArrayList<Property> result = new ArrayList<Property>();
        ObservableList<Property> olResult = FXCollections.observableArrayList();
        String s;
        StringTokenizer find = new StringTokenizer(searchField.getText().toLowerCase());
        while (find.hasMoreTokens()) {
            s = find.nextToken();
            for (int i = 0; i < props.size(); i++)
                if (props.get(i).getAddress().toLowerCase().indexOf(s) != -1) {
                    boolean found = false;

                    for (int j = 0; j < result.size(); j++) {
                        if (props.get(i).getReference() == result.get(j).getReference()) {
                            found = true;
                        }
                    }

                    if (found == false) result.add(props.get(i));
                }
        }
        for (int i = 0; i < result.size(); i++) {
            olResult.add(result.get(i));
        }
        displayTable(olResult);
    }


    public void openLink(ActionEvent event) {
        Application a = new Application() {
            @Override
            public void start(Stage stage) throws Exception {

            }
        };
//        HostServices services = a.getHostServices();
//        services.showDocument("www.google.com");
        try {
            Desktop.getDesktop().browse(new URI("https://www.google.com/search?sxsrf=ALeKk01Vh3UQedW-rDF1lD3uat60p5bW0w%3A1582650932986&source=hp&ei=NFZVXtytObSChbIPw-SeuAM&q=properties+to+buy&oq=properties+to+buy&gs_l=psy-ab.3..0l10.3622.7021..7213...4.0..0.114.1494.16j3......0....1..gws-wiz.....10..35i362i39j35i39j0i131j0i67j0i131i67j0i10.89qS26qqcuY&ved=0ahUKEwjcl6WVmu3nAhU0QUEAHUOyBzcQ4dUDCAg&uact=5"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }


// This section is the beginning of the admin view events

    //    Reads all branches from file - used in other methods
    public ArrayList<Branch> getBranches() {
        MyFile file = new MyFile("Files/branches.dat");
        ArrayList<Branch> branchlist = new ArrayList<Branch>();
        try {
            branchlist = file.readFrom();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return branchlist;
    }

    //    Generates a list of branches, then calls the display method
    public void generateBranchesTable(ActionEvent event) {

        ArrayList<Branch> branchlist = getBranches();
        ObservableList<Branch> branches = FXCollections.observableArrayList();

        for (int i = 0; i < branchlist.size(); i++) {
            branches.add(branchlist.get(i));
        }

        displayBranchesTable(branches);
    }

    // Displays all branches
    public void displayBranchesTable(ObservableList b) {

        bNameCol.setCellValueFactory(new PropertyValueFactory<Branch, String>("name"));
        bNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        bAddressCol.setCellValueFactory(new PropertyValueFactory<Branch, String>("address"));
        bAddressCol.setCellFactory(TextFieldTableCell.forTableColumn());

        bPhoneCol.setCellValueFactory(new PropertyValueFactory<Branch, String>("phoneNumber"));
        bPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());

        bEmailCol.setCellValueFactory(new PropertyValueFactory<Branch, String>("emailAddress"));
        bEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        bWebCol.setCellValueFactory(new PropertyValueFactory<Branch, String>("webAddress"));
        bWebCol.setCellFactory(TextFieldTableCell.forTableColumn());

        bUserCol.setCellValueFactory(new PropertyValueFactory<Branch, String>("username"));
        bUserCol.setCellFactory(TextFieldTableCell.forTableColumn());

        bPasswordCol.setCellValueFactory(new PropertyValueFactory<Branch, String>("password"));
        bPasswordCol.setCellFactory(TextFieldTableCell.forTableColumn());

        branchTableView.setEditable(true);
        branchTableView.setItems(b);
    }

    // Gets the edit name event and writes changes to file.
    public void editBName(Event e) {
        TableColumn.CellEditEvent<Branch, String> event = (TableColumn.CellEditEvent<Branch, String>) e;
        Branch b = event.getRowValue();
        ArrayList<Branch> list = getBranches();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(b.getName())) {
                list.get(i).setName(event.getNewValue());
                System.out.println("the new branch name db" + list.get(i).getName());
            }
        }
        b.setName(event.getNewValue());
        System.out.println("on screen name" + b.getName());
        writeBranchChangesToFile(list);
    }

    // Gets the edit address event and writes changes to file.
    public void editBAddress(Event e) {
        TableColumn.CellEditEvent<Branch, String> event = (TableColumn.CellEditEvent<Branch, String>) e;
        Branch b = event.getRowValue();
        ArrayList<Branch> list = getBranches();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(b.getName())) {
                list.get(i).setAddress(event.getNewValue());
                System.out.println("the new branch address db" + list.get(i).getAddress());
            }
        }
        b.setAddress(event.getNewValue());
        System.out.println("the new branch name on screen" + b.getAddress());
        writeBranchChangesToFile(list);
    }

    // Gets the edit name event and writes changes to file.
    public void editBPhone(Event e) {
        TableColumn.CellEditEvent<Branch, String> event = (TableColumn.CellEditEvent<Branch, String>) e;
        Branch b = event.getRowValue();
        ArrayList<Branch> list = getBranches();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getName().equals(b.getName())) list.get(i).setPhoneNumber(event.getNewValue());
        b.setPhoneNumber(event.getNewValue());
        writeBranchChangesToFile(list);
    }

    // Gets the edit email event and writes changes to file.
    public void editBEmail(Event e) {
        TableColumn.CellEditEvent<Branch, String> event = (TableColumn.CellEditEvent<Branch, String>) e;
        Branch b = event.getRowValue();
        ArrayList<Branch> list = getBranches();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getName().equals(b.getName())) list.get(i).setEmailAddress(event.getNewValue());
        b.setEmailAddress(event.getNewValue());
        writeBranchChangesToFile(list);
    }

    // Gets the edit web address event and writes changes to file.
    public void editBWeb(Event e) {
        TableColumn.CellEditEvent<Branch, String> event = (TableColumn.CellEditEvent<Branch, String>) e;
        Branch b = event.getRowValue();
        ArrayList<Branch> list = getBranches();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getName().equals(b.getName())) list.get(i).setWebAddress(event.getNewValue());
        b.setWebAddress(event.getNewValue());
        writeBranchChangesToFile(list);
    }

    // Gets the edit username event and writes changes to file.
    public void editBUser(Event e) {
        TableColumn.CellEditEvent<Branch, String> event = (TableColumn.CellEditEvent<Branch, String>) e;
        Branch b = event.getRowValue();
        ArrayList<Branch> list = getBranches();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getName().equals(b.getName())) list.get(i).setUsername(event.getNewValue());
        b.setUsername(event.getNewValue());
        writeBranchChangesToFile(list);
    }

    // Gets the edit password event and writes changes to file.
    public void editBPass(Event e) {
        TableColumn.CellEditEvent<Branch, String> event = (TableColumn.CellEditEvent<Branch, String>) e;
        Branch b = event.getRowValue();
        ArrayList<Branch> list = getBranches();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getName().equals(b.getName())) list.get(i).setPassword(event.getNewValue());
        b.setPassword(event.getNewValue());
        writeBranchChangesToFile(list);
    }

    //writes all branches to file
    public void writeBranchChangesToFile(ArrayList<Branch> list) {
        MyFile w = new MyFile("Files/branches.dat");
        try {

            for (int i = 0; i < list.size(); i++) {
                w.appendTo(list.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Adds a new branch to the branch list and writes changes to file
    public void addBranch() {
        try {
            ArrayList<Branch> branchlist = getBranches();
            ObservableList<Branch> fakelist;
            fakelist = branchTableView.getItems();
            if (bNameField.getText().isEmpty() || bAddressField.getText().isEmpty() || bPhoneField.getText().isEmpty() ||
                    bEmailField.getText().isEmpty() || bWebField.getText().isEmpty() ||
                    bUserField.getText().isEmpty() || bPasswordField.getText().isEmpty())
                throw new NumberFormatException();
            Branch b = new Branch(bNameField.getText(), bAddressField.getText(), bPhoneField.getText(), bEmailField.getText(), bWebField.getText(), bUserField.getText(), bPasswordField.getText());
            branchlist.add(b);
            fakelist.add(b);
            displayBranchesTable(fakelist);
            writeBranchChangesToFile(branchlist);

            //Clears fields
            bNameField.clear();
            bAddressField.clear();
            bPhoneField.clear();
            bEmailField.clear();
            bWebField.clear();
            bUserField.clear();
            bPasswordField.clear();

        } catch (NumberFormatException er) {
            NumberFormatError error = new NumberFormatError(er);
            error.getMessage();
        } catch (NullPointerException error) {
            error.printStackTrace();
        }
    }

    //Deletes selected branch and writes changes to file.
    public void deleteBranch() {
        ObservableList<Branch> list, selected;
        list = branchTableView.getItems();
        selected = branchTableView.getSelectionModel().getSelectedItems();
        ArrayList<Branch> blist1 = new ArrayList<>();
        try {
            for (Branch b : selected) {
                list.remove(b);
            }
            for (int i = 0; i < list.size(); i++)
                blist1.add(list.get(i));
            writeBranchChangesToFile(blist1);
        } catch (NoSuchElementException e) {
            try {
                Files.deleteIfExists(Paths.get("Files/branches.dat"));
                MyFile f = new MyFile("Files/branches.dat");
                f.writeTo(null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    // Searches branches by address.
    public void searchBranches() {
        ArrayList<Branch> branches = getBranches();
        ObservableList<Branch> olResult = FXCollections.observableArrayList();
        String s;
        StringTokenizer find = new StringTokenizer(searchField.getText().toLowerCase());
        while (find.hasMoreTokens()) {
            s = find.nextToken();

            for (Branch value : branches)
                if (value.getAddress().toLowerCase().contains(s)) {

                    boolean found = false;
                    for (Branch branch : olResult) {
                        if (value.getName().equals(branch.getName())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) olResult.add(value);
                }
            displayBranchesTable(olResult);
        }
    }

    //Sets up current branch and initializes table view to the respective branches or properties, using a try catch.
    public void initialize() {
//  Tries to read admins.
        try {
            MyFile sesh1 = new MyFile("Files/session.dat");
            ArrayList<Admin> a = sesh1.readFrom();
            currentUser.setText(a.get(0).getUsername());
            ArrayList<Branch> list = getBranches();
            ObservableList<Branch> branches = FXCollections.observableArrayList();

            for (int i = 0; i < list.size(); i++) {
                branches.add(list.get(i));
            }
            displayBranchesTable(branches);
        }
//        If no admins are found, throws ClassException and reads Secretary/Branch instead.
        catch (ClassCastException e) {
            MyFile sesh = new MyFile("Files/session.dat");
//            System.out.println("it's a secretary");
            ArrayList<Branch> b = new ArrayList<>();
            try {
                b = sesh.readFrom();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            currentUser.setText(b.get(0).getName());
            link.setText(b.get(0).getWebAddress());
            Branch branch = getCurrentBranch();
            ArrayList<Property> list = branch.getProperties();
            ObservableList<Property> props = FXCollections.observableArrayList();

            for (int i = 0; i < list.size(); i++) {
                props.add(list.get(i));
            }
            displayTable(props);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

