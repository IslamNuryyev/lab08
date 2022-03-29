package lab08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class Main extends Application {
    private BorderPane layout;
    private TableView<StudentRecord> table;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Lab 08");

        Menu menu = new Menu("File");

        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction((event) -> {
            ObservableList<StudentRecord> emptyData = FXCollections.<StudentRecord>observableArrayList();
            table.setItems(emptyData);
        });
        menu.getItems().add(newFile);

        MenuItem open = new MenuItem("Open");
        open.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose a file for loading");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File selected = fileChooser.showOpenDialog(primaryStage);
            if (selected != null) {
                this.currentFileName = selected;
                load();
            }
        });
        menu.getItems().add(open);

        MenuItem save = new MenuItem("Save");
        save.setOnAction((event) -> save());
        menu.getItems().add(save);

        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction((event) -> saveAs());
        menu.getItems().add(saveAs);

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((event) -> System.exit(0));
        menu.getItems().add(exit);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);


        table = new TableView<>();
        table.setItems(DataSource.getAllMarks());
        System.out.println(DataSource.getAllMarks());
        table.setEditable(true);


        TableColumn<StudentRecord, String> studentID_column = null;
        studentID_column = new TableColumn<>("SID");
        studentID_column.setMinWidth(120);
        studentID_column.setCellValueFactory(new PropertyValueFactory<StudentRecord, String>("studentid"));

        TableColumn<StudentRecord, Float> assignments_column = null;
        assignments_column = new TableColumn<>("Assignments");
        assignments_column.setMinWidth(120);
        assignments_column.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("assignments"));

        TableColumn<StudentRecord, Float> midterm_column = null;
        midterm_column = new TableColumn<>("Midterm");
        midterm_column.setMinWidth(120);
        midterm_column.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("midterm"));

        TableColumn<StudentRecord, Float> final_exam_column = null;
        final_exam_column = new TableColumn<>("Final Exam");
        final_exam_column.setMinWidth(120);
        final_exam_column.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("finalExam"));

        TableColumn<StudentRecord, Float> final_mark_column = null;
        final_mark_column = new TableColumn<>("Final Mark");
        final_mark_column.setMinWidth(120);
        final_mark_column.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("finalMark"));
        
        TableColumn<StudentRecord, Character> letter_grade_column = null;
        letter_grade_column = new TableColumn<>("Letter Grade");
        letter_grade_column.setMinWidth(120);
        letter_grade_column.setCellValueFactory(new PropertyValueFactory<StudentRecord, Character>("letterGrade"));

        table.getColumns().add(studentID_column);
        table.getColumns().add(assignments_column);
        table.getColumns().add(midterm_column);
        table.getColumns().add(final_exam_column);
        table.getColumns().add(final_mark_column);
        table.getColumns().add(letter_grade_column);

        GridPane editForm = new GridPane();
        editForm.setPadding(new Insets(10, 10, 10, 10));
        editForm.setVgap(10);
        editForm.setHgap(20);

        Label studentidLabel = new Label("SID:");
        editForm.add(studentidLabel, 0, 0);
        TextField studentidField = new TextField();
        studentidField.setPromptText("SID");
        editForm.add(studentidField, 1, 0);

        Label assignmentsLabel = new Label("Assignments:");
        editForm.add(assignmentsLabel, 2, 0);
        TextField assignmentsField = new TextField();
        assignmentsField.setPromptText("Assignments/100");
        editForm.add(assignmentsField, 3, 0);

        Label midtermLabel = new Label("Midterm:");
        editForm.add(midtermLabel, 0, 1);
        TextField midtermField = new TextField();
        midtermField.setPromptText("Midterm/100");
        editForm.add(midtermField, 1, 1);

        Label finalExamLabel = new Label("Final Exam:");
        editForm.add(finalExamLabel, 2, 1);
        TextField finalExamField = new TextField();
        finalExamField.setPromptText("Assignments/100");
        editForm.add(finalExamField, 3, 1);

        Button addButton = new Button("Add");
        addButton.setOnAction((event) -> {
            String studentid = studentidField.getText();
            float assignments = Float.parseFloat(assignmentsField.getText());
            float midterm = Float.parseFloat(midtermField.getText());
            float finalExam = Float.parseFloat(finalExamField.getText());

            table.getItems().add(new StudentRecord(studentid, assignments, midterm, finalExam));

            studentidField.setText("");
            assignmentsField.setText("");
            midtermField.setText("");
            finalExamField.setText("");
        });
        editForm.add(addButton, 1, 4);
        
        layout = new BorderPane();
        layout.setCenter(table);
        layout.setTop(menuBar);
        layout.setBottom(editForm);

        Scene scene = new Scene(layout, 720, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private File currentFileName = null;


    private void save() {
        if (this.currentFileName == null) {
            saveAs();
            return;
        } else {
            try {
                PrintWriter out = new PrintWriter(this.currentFileName);

                ObservableList<StudentRecord> students = this.table.getItems();
                for (StudentRecord student : students) {
                    out.println(student.getStudentid() + "," +
                    student.getAssignments() + "," +
                    student.getMidterm() + "," +
                    student.getFinalExam());    
                }

                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
            new FileChooser.ExtensionFilter("All Files", "*.")
        );
        File selected = fileChooser.showSaveDialog(primaryStage);
        if (selected != null) {
            this.currentFileName = selected;
            save();
        }
    }

    private void load() {
        try {
            if (this.currentFileName.exists()) {
                ObservableList<StudentRecord> students = FXCollections.observableArrayList();
                BufferedReader in = new BufferedReader(new FileReader(this.currentFileName));
                String line = null;
                while((line = in.readLine()) != null) {
                    String[] fields = line.split(",");
                    String studentid = fields[0];
                    float assignments = Float.parseFloat(fields[1]);
                    float midterm = Float.parseFloat(fields[2]);
                    float finalExam = Float.parseFloat(fields[3]);
                    students.add(new StudentRecord(studentid, assignments, midterm, finalExam));
                }
                in.close();
                table.setItems(students);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }  
}
