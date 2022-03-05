package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.AccessCd;
import model.CD;
import model.User;

import java.io.IOException;
import java.util.ArrayList;


public class MainMenu {
    User currentUser;
    Stage primaryStage;
    //int noItems;
    //boolean show = true;
    public static double wallet = 0;

    public MainMenu(){}

    public void setWallet(double profit){
        wallet += profit;
    }

    public MainMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene exec(Stage ps) {
        primaryStage = ps;
        VBox vert = new VBox();
        VBox vert2 = new VBox();

        Text role = new Text();
        vert2.setPadding(new Insets(10, 50, 50, 50));

        role.setStyle("-fx-fill:white;" +
                "-fx-font: 20px Helvetica;");
        BorderPane mainPane = new BorderPane();

        vert2.setStyle("-fx-background-image: url('/dash-left.jpg');" +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: 250 720;" +
                "-fx-background-position: center center;");

        if (currentUser.getLevel() == 1) role.setText("Administrator");
        else if (currentUser.getLevel() == 2) role.setText("Manager");
        else role.setText("Cashier");

        vert2.getChildren().addAll(role);

        mainPane.setLeft(vert2);
        vert.setSpacing(10);
        vert.setAlignment(Pos.CENTER);
        mainPane.setCenter(vert);

        Scene scene = new Scene(mainPane, 900, 520);

        //ADMINISTRATOR_MENU
        if (currentUser.getLevel() == 1) {
            Menu fileMenu = new Menu("Options");
            ImageView logout = new ImageView("/logout.png");
            ImageView editUser = new ImageView("/edit-user.png");
            ImageView newUserr = new ImageView("/newUser.png");
            ImageView newCd = new ImageView("/newCD.png");
            ImageView chartIm = new ImageView("/chart.jpg");
            ImageView addemp = new ImageView("/add-emp.png");
            ImageView editemp = new ImageView("/edit-emp.png");
            ImageView editcd = new ImageView("/editcd.png");
            MenuItem newUser = new MenuItem("Add user", newUserr);
            MenuItem users = new MenuItem("Edit user's", editUser);
            MenuItem addEmp = new MenuItem("Add employee",addemp);
            MenuItem emp = new MenuItem("Edit employees",editemp);
            MenuItem newCD = new MenuItem("Add CD", newCd);
            MenuItem editCd = new MenuItem("Edit CD",editcd);
            MenuItem chart = new MenuItem("Chart",chartIm);
            MenuItem exit = new MenuItem("Log out", logout);


            newUserr.setFitWidth(20);
            newUserr.setFitHeight(20);
            editUser.setFitWidth(20);
            editUser.setFitHeight(20);
            logout.setFitHeight(20);
            logout.setFitWidth(20);
            newCd.setFitHeight(20);
            newCd.setFitWidth(20);
            addemp.setFitHeight(20);
            addemp.setFitWidth(20);
            editemp.setFitHeight(20);
            editemp.setFitWidth(20);
            chartIm.setFitHeight(20);
            chartIm.setFitWidth(20);
            editcd.setFitHeight(20);
            editcd.setFitWidth(20);


            fileMenu.getItems().addAll(newUser, newCD,editCd, users, addEmp, emp,chart, exit);

            newUser.setOnAction(e -> {
                primaryStage.setScene((new AddUser(this.currentUser)).exec(primaryStage));
            });

            chart.setOnAction(e -> {
                primaryStage.setScene((new ProductStat(this.currentUser)).exec(primaryStage));
            });

            newCD.setOnAction(e -> {
                primaryStage.setScene((new AddCD(this.currentUser)).exec(primaryStage));
            });

            users.setOnAction(e -> {
                primaryStage.setScene((new ManageUsers(this.currentUser)).exec(primaryStage));
            });

            editCd.setOnAction(e -> {
                primaryStage.setScene((new EditCd(this.currentUser)).exec(primaryStage));
            });

            emp.setOnAction(e -> {
                primaryStage.setScene((new ManageEmployee(this.currentUser)).exec(primaryStage));
            });

            addEmp.setOnAction(e -> {
                primaryStage.setScene((new AddEmployee(this.currentUser)).exec(primaryStage));
            });

            exit.setOnAction(e -> {
                primaryStage.setScene((new Login()).exec(primaryStage));
            });

            MenuBar mb = new MenuBar();

            mb.getMenus().addAll(fileMenu);
            mainPane.setTop(mb);

            Text credits = new Text("Developed by Armend Ostaku");
            Text stockOnTop = new Text("PROFIT : "+wallet+"$");
            credits.setFill(Color.GREEN);
            credits.setTextAlignment(TextAlignment.LEFT);
            stockOnTop.setFill(Color.PURPLE);
            stockOnTop.setStyle("-fx-font-size: 24px;");
            stockOnTop.setTextAlignment(TextAlignment.CENTER);

            AccessCd au = new AccessCd();
            TableView cdStore = new TableView();
            cdStore.setEditable(true);
            ObservableList<CD> cdList = FXCollections.observableArrayList(au.getCd());

            TableColumn<CD, Integer> CDID = new TableColumn("ID");
            CDID.setCellValueFactory(new PropertyValueFactory<>("cdId"));

            TableColumn<CD, String> CDNAME = new TableColumn("CD NAME");
            CDNAME.setCellValueFactory(new PropertyValueFactory<>("cdName"));

            TableColumn<CD, String> CDAUTHOR = new TableColumn("AUTHOR");
            CDAUTHOR.setCellValueFactory(new PropertyValueFactory<>("cdAuthor"));

            TableColumn<CD, String> CDCATEGORY = new TableColumn("CD CATEGORY");
            CDCATEGORY.setCellValueFactory(new PropertyValueFactory<>("cdCategory"));

            TableColumn<CD, Integer> PUBLISHED = new TableColumn("PUBLISHED");
            PUBLISHED.setCellValueFactory(new PropertyValueFactory<>("cdYear"));

            TableColumn<CD, Integer> QUANTITY = new TableColumn("QUANTITY");
            QUANTITY.setCellValueFactory(new PropertyValueFactory<>("cdQuantity"));

            TableColumn<CD, Double> PRICE = new TableColumn("PRICE");
            PRICE.setCellValueFactory(new PropertyValueFactory<>("cdPrice"));

            TableColumn<CD, Integer> UPC = new TableColumn<>("UPC");
            UPC.setCellValueFactory(new PropertyValueFactory<>("cdUPC"));


            cdStore.setItems(cdList);
            cdStore.getColumns().addAll(CDID, CDNAME, CDAUTHOR, CDCATEGORY, PUBLISHED, QUANTITY, PRICE, UPC);

            vert.getChildren().addAll(stockOnTop, cdStore, credits);
        }

        //MANAGER_MENU
        else if (currentUser.getLevel() == 2) {
            Menu fileMenu = new Menu("Options");
            ImageView logout = new ImageView("/logout.png");
            logout.setFitHeight(20);
            logout.setFitWidth(20);
            MenuItem exit = new MenuItem("Log out", logout);

            fileMenu.getItems().addAll(exit);
            exit.setOnAction(e -> {
                primaryStage.setScene((new Login()).exec(primaryStage));
            });
            MenuBar mb = new MenuBar();

            mb.getMenus().add(fileMenu);
            mainPane.setTop(mb);

            HBox actions = new HBox();
            HBox act = new HBox();
            act.setSpacing(20);
            act.setAlignment(Pos.CENTER);
            actions.setAlignment(Pos.CENTER);
            actions.setSpacing(20);
            Button addBill = new Button("Create");
            Button stock = new Button("Stock");

            Button newCd = new Button("New cd");

            addBill.setOnAction(e -> {
                primaryStage.setScene((new AddBill(this.currentUser)).exec(primaryStage));
            });

            newCd.setOnAction(e -> {
                primaryStage.setScene((new AddCD(this.currentUser)).exec(primaryStage));
            });

            stock.setOnAction(e->{
                primaryStage.setScene((new ManagerStock(this.currentUser)).exec(primaryStage));
            });

            addBill.setStyle("-fx-font-size: 2em;" +
                    "-fx-background-color:#54428E;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");

            newCd.setStyle("-fx-font-size: 2em;" +
                    "-fx-background-color:#94ba63;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");
            stock.setStyle("-fx-font-size: 2em;" +
                    "-fx-background-color:#8963BA;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");
            addBill.setMaxHeight(110);
            addBill.setMinHeight(110);
            addBill.setMaxWidth(200);
            addBill.setMinWidth(200);
            stock.setMaxHeight(110);
            stock.setMinHeight(110);
            stock.setMaxWidth(200);
            stock.setMinWidth(200);
            newCd.setMaxHeight(110);
            newCd.setMinHeight(110);
            newCd.setMaxWidth(200);
            newCd.setMinWidth(200);

            AccessCd ac = new AccessCd();
            ObservableList<CD> cdList = FXCollections.observableArrayList(ac.getCd());
            int items=0;
            Text alert = new Text();
            alert.setVisible(false);

            for(CD c: cdList) {
               if(c.getCdQuantity()<10){
                   items++;
                   alert.setVisible(true);
               }
            }

            actions.getChildren().addAll(addBill, stock,newCd);
            alert.setText("You have "+items+" items that need to moderate! Please check the stock!");
            alert.setFill(Color.RED);
            act.getChildren().add(alert);
            vert.getChildren().addAll(actions,act);
        }


        //CASHIER MENU
        else if (currentUser.getLevel() == 3) {
            Menu fileMenu = new Menu("Options");
            ImageView logout = new ImageView("/logout.png");
            logout.setFitHeight(20);
            logout.setFitWidth(20);
            MenuItem exit = new MenuItem("Log out", logout);

            fileMenu.getItems().addAll(exit);
            exit.setOnAction(e -> {
                primaryStage.setScene((new Login()).exec(primaryStage));
            });
            MenuBar mb = new MenuBar();

            mb.getMenus().add(fileMenu);
            mainPane.setTop(mb);

            HBox actions = new HBox();
            actions.setAlignment(Pos.CENTER);
            actions.setSpacing(20);
            Button addBill = new Button("Create");
            Button stock = new Button("Stock");

            addBill.setOnAction(e -> {
                    primaryStage.setScene((new AddBill(this.currentUser)).exec(primaryStage));
            });


            stock.setOnAction(e->{
                primaryStage.setScene((new Stock(this.currentUser)).exec(primaryStage));
            });

            addBill.setStyle("-fx-font-size: 2em;" +
                    "-fx-background-color:#54428E;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");

            addBill.setMaxHeight(110);
            addBill.setMinHeight(110);
            addBill.setMaxWidth(200);
            addBill.setMinWidth(200);
            stock.setMaxHeight(110);
            stock.setMinHeight(110);
            stock.setMaxWidth(200);
            stock.setMinWidth(200);

            stock.setStyle("-fx-font-size: 2em;" +
                    "-fx-background-color:#8963BA;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");


            actions.getChildren().addAll(addBill, stock);
            vert.getChildren().add(actions);
        }

        ps.setTitle("Menu");
        return scene;
    }

}


