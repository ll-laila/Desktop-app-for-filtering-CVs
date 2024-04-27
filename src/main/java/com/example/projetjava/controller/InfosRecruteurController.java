package com.example.projetjava.controller;

import com.example.projetjava.Apps.Main;
import com.example.projetjava.Models.Recruteur;
import com.example.projetjava.dao.impl.CandidatDaoImpl;
import com.example.projetjava.dao.impl.RecruteurDaoImpl;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import static com.example.projetjava.controller.InscriptionController.hashSHA256;
import static com.example.projetjava.dao.impl.UserDaoImpl.randomvalu;

public class InfosRecruteurController {
    /**
     * on recupere les champs de la view
     **/
    @FXML
    public TextField EntrepriseField;
    @FXML
    public TextField NumPathField;
    @FXML
    public JFXButton ViewProfile;
    @FXML
    public JFXButton HomePage;
    @FXML
    public JFXButton AddOffer;
    @FXML
    public JFXButton OffresPostules;
    @FXML
    public ImageView ViewCv = new ImageView();
    @FXML
    public Button Ouvrir_CV;
    @FXML
    public JFXButton deconnecter;
    @FXML
    public Button ChangeProfileBtn;
    @FXML
    public TextField loginField;

    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;
    private Main app;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;



    public void setMainApp(Main mainApp) {
        this.app = mainApp;
    }
    @FXML
    public void initialize() {


        slider.setTranslateX(-176);
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent ev) -> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });


        ViewProfile.setOnMouseClicked(event -> {
            app.switchToInfosRecruteur();
        });

        AddOffer.setOnMouseClicked(e -> {
            app.switchToAjoutOffre();
        });

        HomePage.setOnMouseClicked(ev -> {
            app.switchToAccuielleRec();
        });

        OffresPostules.setOnMouseClicked(evv -> {
            app.switchToOffresPublies();
        });

        deconnecter.setOnMouseClicked(ev ->
        {
            app.switchToScene1();
        });
        // Exécuter une tâche après un délai de 4 secondes
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(60000);
                // Code à exécuter après le délai de 4 secondes
                profile();
                return null;
            }
        };

        new Thread(task).start();
    }


    public void update() {
        /**
         * on récuper les valeurs saisies
         * */
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String login = loginField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        /**
         *   on genere une cle pour chaque user pour le identifier 'une facone unique
         * **/


        if(nom.isEmpty() || password.isEmpty() || phone.isEmpty() || prenom.isEmpty() || email.isEmpty() || confirmPassword.isEmpty() || login.isEmpty()){
            errorLabel.setText("Vous avez un chmaps vide !!!");
        }else {


            if (!password.equals(confirmPassword)) {
                errorLabel.setText("Passwords do not match");
                return;
            }

            /**
             * on test si la lonueur de mot de passe est au moins 8 chars
             * **/

            if(password.length() < 8){
                errorLabel.setText("Password must be at least 8 character !");
                return;
            }

            password = hashSHA256(password);
            Recruteur recruteur = new Recruteur(nom,prenom,EntrepriseField.getText(),phone,email,"",login,password,randomvalu);

            //si est un condidat inserer les infos dans condidat
            RecruteurDaoImpl condidatDao = new RecruteurDaoImpl();
            condidatDao.updateRecruteur(recruteur);
            profile();


        }

    }
    public void profile(){

        Recruteur condidat;
        condidat = new CandidatDaoImpl().getRecruteuroffreById();

        nomField.setText(condidat.getNom());
        prenomField.setText(condidat.getPrenom());
        loginField.setText(condidat.getLogin());
        emailField.setText(condidat.getEmail());
        phoneField.setText(condidat.getTelephone());
        EntrepriseField.setText(condidat.getNomEntreprise());
        passwordField.setText("");
        confirmPasswordField.setText("");
        errorLabel.setText("");
    }


    /**
     * Ces methodes permet de passer d'une scene a une autre
     * ***/
    public void setApp(Main app) {
        this.app = app;
    }


    public void runCode() {
        // Code spécifique à exécuter dans Controller2
        System.out.println("Code exécuté après la transition vers Controller2");
        // ...
    }
}
