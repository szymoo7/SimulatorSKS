package sks.frontend;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sks.backend.CanteenManager;
import sks.backend.Client;
import sks.backend.ClientDto;
import sks.backend.Line;

import java.util.*;

public class SKSceneManager {
    /* AnchorPanes */
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane canteenAnchorPane;

    /* Buttons */
    @FXML
    private ToggleButton startButton;
    @FXML
    private ToggleButton stopButton;
    @FXML
    private ToggleGroup toggleGroup;

    /* ImageViews - Scene */
    @FXML
    private ImageView canteenImageView;

    private List<Image> characterSkins = new ArrayList<>() {
        {
            add(new Image("C:\\Users\\szyme\\IdeaProjects\\SimulatorSKS\\frontend\\src\\main\\resources\\sks\\frontend\\assets\\babka_w_sukience.png"));
//            add(new Image("chlop_z_papierem_i_z_teczka.png").getUrl());
//            add(new Image("tinky_winky.png").getUrl());
//            add(new Image("new_character.png").getUrl());
        }
    };

    /* Simulation settings panel */
    @FXML
    private Label simulationSettingsLabel;
    @FXML
    private Slider simulationSpeedSlider;
    @FXML
    private Slider simulationNChairsSlider;

    /* Panes */
    @FXML
    private Pane tableTopLeftCornerPane;
    @FXML
    private Pane tableCenterTopLeftCornerPane;
    @FXML
    private Pane tableCenterTopRightCornerPane;
    @FXML
    private Pane tableTopRightCornerPane;
    @FXML
    private Pane tableBottomLeftCornerPane;
    @FXML
    private Pane tableCenterBottomLeftCornerPane;
    @FXML
    private Pane tableCenterBottomRightCornerPane;
    @FXML
    private Pane tableBottomRightCornerPane;

    /* Panes - chairs rows */
    @FXML
    private Pane table1row1;
    @FXML
    private Pane table1row2;
    @FXML
    private Pane table1row3;
    @FXML
    private Pane table1row4;

    @FXML
    private Pane table2row1;
    @FXML
    private Pane table2row2;
    @FXML
    private Pane table2row3;
    @FXML
    private Pane table2row4;

    @FXML
    private Pane table3row1;
    @FXML
    private Pane table3row2;
    @FXML
    private Pane table3row3;
    @FXML
    private Pane table3row4;

    @FXML
    private Pane table4row1;
    @FXML
    private Pane table4row2;
    @FXML
    private Pane table4row3;
    @FXML
    private Pane table4row4;

    @FXML
    private Pane table5row1;
    @FXML
    private Pane table5row2;
    @FXML
    private Pane table5row3;
    @FXML
    private Pane table5row4;

    @FXML
    private Pane table6row1;
    @FXML
    private Pane table6row2;
    @FXML
    private Pane table6row3;
    @FXML
    private Pane table6row4;

    @FXML
    private Pane table7row1;
    @FXML
    private Pane table7row2;
    @FXML
    private Pane table7row3;
    @FXML
    private Pane table7row4;

    @FXML
    private Pane table8row1;
    @FXML
    private Pane table8row2;
    @FXML
    private Pane table8row3;
    @FXML
    private Pane table8row4;

    /* ImageViews - chairs */
    @FXML
    private ImageView table1row1chairLeft;
    @FXML
    private ImageView table1row1chairRight;
    @FXML
    private ImageView table1row2chairLeft;
    @FXML
    private ImageView table1row2chairRight;
    @FXML
    private ImageView table1row3chairLeft;
    @FXML
    private ImageView table1row3chairRight;
    @FXML
    private ImageView table1row4chairLeft;
    @FXML
    private ImageView table1row4chairRight;

    @FXML
    private ImageView table2row1chairLeft;
    @FXML
    private ImageView table2row1chairRight;
    @FXML
    private ImageView table2row2chairLeft;
    @FXML
    private ImageView table2row2chairRight;
    @FXML
    private ImageView table2row3chairLeft;
    @FXML
    private ImageView table2row3chairRight;
    @FXML
    private ImageView table2row4chairLeft;
    @FXML
    private ImageView table2row4chairRight;

    @FXML
    private ImageView table3row1chairLeft;
    @FXML
    private ImageView table3row1chairRight;
    @FXML
    private ImageView table3row2chairLeft;
    @FXML
    private ImageView table3row2chairRight;
    @FXML
    private ImageView table3row3chairLeft;
    @FXML
    private ImageView table3row3chairRight;
    @FXML
    private ImageView table3row4chairLeft;
    @FXML
    private ImageView table3row4chairRight;

    @FXML
    private ImageView table4row1chairLeft;
    @FXML
    private ImageView table4row1chairRight;
    @FXML
    private ImageView table4row2chairLeft;
    @FXML
    private ImageView table4row2chairRight;
    @FXML
    private ImageView table4row3chairLeft;
    @FXML
    private ImageView table4row3chairRight;
    @FXML
    private ImageView table4row4chairLeft;
    @FXML
    private ImageView table4row4chairRight;

    @FXML
    private ImageView table5row1chairLeft;
    @FXML
    private ImageView table5row1chairRight;
    @FXML
    private ImageView table5row2chairLeft;
    @FXML
    private ImageView table5row2chairRight;
    @FXML
    private ImageView table5row3chairLeft;
    @FXML
    private ImageView table5row3chairRight;
    @FXML
    private ImageView table5row4chairLeft;
    @FXML
    private ImageView table5row4chairRight;

    @FXML
    private ImageView table6row1chairLeft;
    @FXML
    private ImageView table6row1chairRight;
    @FXML
    private ImageView table6row2chairLeft;
    @FXML
    private ImageView table6row2chairRight;
    @FXML
    private ImageView table6row3chairLeft;
    @FXML
    private ImageView table6row3chairRight;
    @FXML
    private ImageView table6row4chairLeft;
    @FXML
    private ImageView table6row4chairRight;

    @FXML
    private ImageView table7row1chairLeft;
    @FXML
    private ImageView table7row1chairRight;
    @FXML
    private ImageView table7row2chairLeft;
    @FXML
    private ImageView table7row2chairRight;
    @FXML
    private ImageView table7row3chairLeft;
    @FXML
    private ImageView table7row3chairRight;
    @FXML
    private ImageView table7row4chairLeft;
    @FXML
    private ImageView table7row4chairRight;

    @FXML
    private ImageView table8row1chairLeft;
    @FXML
    private ImageView table8row1chairRight;
    @FXML
    private ImageView table8row2chairLeft;
    @FXML
    private ImageView table8row2chairRight;
    @FXML
    private ImageView table8row3chairLeft;
    @FXML
    private ImageView table8row3chairRight;
    @FXML
    private ImageView table8row4chairLeft;
    @FXML
    private ImageView table8row4chairRight;

    private final CanteenManager simulationManager = new CanteenManager();
    private final Random random = new Random();

    private List<Pane> tables = new ArrayList<>();
    private Map<Integer, ImageView> clientsOnScene = new HashMap<>();

    private Runnable generateClietsLambda = () -> {
        while(true) {
            simulationManager.generateClient();
            try {
                Thread.sleep(simulationManager.getClientEveryNSeconds() * 1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupting generating clients");
                break;
            }
        }
    };

    private Thread currentGenerator;


    public void initialize() {
        tables.addAll(canteenAnchorPane.getChildren().stream()
                .filter(node -> node instanceof Pane)
                .map(node -> (Pane) node)
                .toList());

        simulationSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                simulationManager.setSimulationSpeed(newValue.intValue());
            }
        });

        simulationNChairsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                simulationManager.setNSeats(newValue.intValue());

                tables.forEach(table -> table.getChildren().forEach(pairOfChairs -> {
                            if(pairOfChairs instanceof Pane row) {
                                row.setVisible(false);
                            }
                        })
                );

                tables.forEach(table -> table.getChildren()
                        .stream().limit(newValue.intValue())
                        .forEach(pairOfChairs -> {
                            if(pairOfChairs instanceof Pane row) {
                                row.setVisible(true);
                            }
                        })
                );
            }
        });

        startButton.setToggleGroup(toggleGroup);
        stopButton.setToggleGroup(toggleGroup);
        startButton.setSelected(false);
        stopButton.setSelected(true);

    }

    @FXML
    protected void onStartButtonClick() {
        simulationManager.startSimulation();
        currentGenerator = new Thread(generateClietsLambda);
        currentGenerator.start();

        //TODO: Zrobić kanał np. arrayliste gdzie przekazuje klientów i wspolrzedne do zaktualizowania
        Platform.runLater( () -> {
            while (true) {
                ClientDto current = simulationManager.getClientToUpdate();
                if (current != null) {
                    System.out.println("Current client: " + current);
                    clientsOnScene.compute(current.getId(), (id, image) -> {
                        if (image == null) {
                            image = new ImageView(characterSkins.get(0));
                        }
                        image.setX(current.getX());
                        image.setY(current.getY());
                        return image;
                    });
                    System.out.println("Chuj kurwa nie ma obrazu");
                }
            }
        });
    }

    @FXML
    protected void onStopButtonClick() {
        currentGenerator.interrupt();
        simulationManager.stopSimulation();
    }
}