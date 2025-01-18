package sks.frontend;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import sks.backend.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
            add(new Image(getClass().getResourceAsStream("/sks/frontend/assets/babka_w_sukience.png")));
            add(new Image(getClass().getResourceAsStream("/sks/frontend/assets/chlop.png")));
            add(new Image(getClass().getResourceAsStream("/sks/frontend/assets/chlop_z_papierem_i_z_teczka.png")));
            add(new Image(getClass().getResourceAsStream("/sks/frontend/assets/tinky_winky.png")));
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
    private final PathTransition pathTransition = new PathTransition();

    private Runnable generateClietsLambda = () -> {
        int numberOfClients = 0;
        while(numberOfClients < 100) {
            simulationManager.generateClient();
            numberOfClients++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Interrupting generating clients");
                break;
            }
        }
    };

    private Thread currentGenerator;
    private Map<Table, Pane> tableToPane = new HashMap<>();


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

                List<Table> tableObjects = simulationManager.getTables();
                List<Pane> tablePanes = canteenAnchorPane.getChildren().stream()
                        .filter(node -> node instanceof Pane)
                        .map(node -> (Pane) node)
                        .toList();
                for (int i = 0; i < tableObjects.size(); i++) {
                    tableToPane.put(tableObjects.get(i), tablePanes.get(i));
                }

                tables.forEach(table -> table.getChildren()
                        .forEach(pairOfChairs -> {
                            if (pairOfChairs instanceof Pane row) {
                                row.setVisible(table.getChildren().indexOf(row) < newValue.intValue());
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
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            AnimationDto animation = simulationManager.getAnimation();
            if(animation != null) {
                if(animation.isForTable()) {
                    System.out.println("Current table seat: " + animation + " id = " + animation.getId());
                    int toGoX = 0;
                    int toGoY = 0;
                    ImageView seat = canteenAnchorPane.getChildren().stream()
                            .filter(node -> node instanceof Pane)
                            .flatMap(node -> ((Pane) node).getChildren().stream())
                            .filter(node -> node instanceof Pane)
                            .flatMap(node -> ((Pane) node).getChildren().stream())
                                    .filter(node -> node instanceof ImageView)
                                            .map(node -> (ImageView) node)
                                                    .filter(imageView -> imageView.getId().equals(animation.getTableSeatId()))
                            .findFirst().orElseThrow();

                    Pane rowPane = (Pane) seat.getParent();
                    Pane tablePane = (Pane) rowPane.getParent();
                    toGoX += seat.getLayoutX();
                    toGoY += seat.getLayoutY();
                    toGoX += rowPane.getLayoutX();
                    toGoY += rowPane.getLayoutY();
                    toGoX += tablePane.getLayoutX();
                    toGoY += tablePane.getLayoutY();
                    toGoX += canteenAnchorPane.getLayoutX();
                    toGoY += canteenAnchorPane.getLayoutY();

                    System.out.println("Seat: " + seat.getId() + "coords x = " + toGoX + " y = " + toGoY);

                    int finalToGoX = toGoX;
                    int finalToGoY = toGoY;
                    Platform.runLater(() -> {
                        clientsOnScene.compute(animation.getId(), (id, image) -> {
                            pathTransition.setDuration(Duration.millis(1000));
                            pathTransition.setNode(image);
                            pathTransition.setPath(new Polyline(image.getX(), image.getY(), finalToGoX, finalToGoY));
                            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                            pathTransition.setOnFinished(event -> {
                                System.out.println("Finished animation for client id: " + animation.getId() + " table = " + animation.getTable() + " seat = " + animation.getSeat());
                            });
                            pathTransition.play();
                            image.setX(finalToGoX);
                            image.setY(finalToGoY);

                            return image;
                        });
                    });
                } else {
                    Platform.runLater(() -> {
                        clientsOnScene.compute(animation.getId(), (id, image) -> {
                            if (image == null) {
                            image = new ImageView(characterSkins.get(random.nextInt(characterSkins.size())));
                            canteenAnchorPane.getChildren().add(image);
                            image.setX(animation.getX());
                            image.setFitHeight(75);
                            image.setFitWidth(75);
                            image.setY(animation.getY());
                            image.setVisible(true);
                        }
                        else {
                            PathTransition pathTransition = new PathTransition();
                            pathTransition.setDuration(Duration.millis(1000));
                            pathTransition.setNode(image);
                            Integer toGoX = animation.getX();
                            Integer toGoY = animation.getY();
                            if(toGoX == null || toGoY == null) {
                                image.setVisible(false);
                                canteenAnchorPane.getChildren().remove(image);
                                return null;
                            }
                            pathTransition.setPath(new Polyline(image.getX(), image.getY(), toGoX, toGoY));
                            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                            pathTransition.play();
                            image.setX(animation.getX());
                            image.setY(animation.getY());
                        }

                        return image;
                });
//            ClientDto current = simulationManager.getClientToUpdate();
//            TableSeatDto tableSeatDto = simulationManager.getTableSeatToUpdate();
//            if (current != null) {
//                //System.out.println("Current client: " + current);
//                Platform.runLater(() -> {
//                    clientsOnScene.compute(current.getId(), (id, image) -> {
//                        if (image == null) {
//                            image = new ImageView(characterSkins.get(random.nextInt(characterSkins.size())));
//                            canteenAnchorPane.getChildren().add(image);
//                            image.setX(current.getX());
//                            image.setFitHeight(75);
//                            image.setFitWidth(75);
//                            image.setY(current.getY());
//                            image.setVisible(true);
//                        }
//                        else {
//                            PathTransition pathTransition = new PathTransition();
//                            pathTransition.setDuration(Duration.millis(1000));
//                            pathTransition.setNode(image);
//                            Integer toGoX = current.getX();
//                            Integer toGoY = current.getY();
//                            if(toGoX == null || toGoY == null) {
//                                image.setVisible(false);
//                                canteenAnchorPane.getChildren().remove(image);
//                                return null;
//                            }
//                            pathTransition.setPath(new Polyline(image.getX(), image.getY(), toGoX, toGoY));
//                            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//                            pathTransition.play();
//                            image.setX(current.getX());
//                            image.setY(current.getY());
//                        }
//
//                        return image;
//                    });
//                });
//            }
//            if(tableSeatDto != null) {
//                System.out.println("Current table seat: " + tableSeatDto + " id = " + tableSeatDto.getId());
//                int toGoX = 0;
//                int toGoY = 0;
//                ImageView seat = canteenAnchorPane.getChildren().stream()
//                        .filter(node -> node instanceof Pane)
//                        .flatMap(node -> ((Pane) node).getChildren().stream())
//                        .filter(node -> node instanceof Pane)
//                        .flatMap(node -> ((Pane) node).getChildren().stream())
//                                .filter(node -> node instanceof ImageView)
//                                        .map(node -> (ImageView) node)
//                                                .filter(imageView -> imageView.getId().equals(tableSeatDto.getTableSeatId()))
//                        .findFirst().orElseThrow();
//
//                Pane rowPane = (Pane) seat.getParent();
//                Pane tablePane = (Pane) rowPane.getParent();
//                toGoX += seat.getLayoutX();
//                toGoY += seat.getLayoutY();
//                toGoX += rowPane.getLayoutX();
//                toGoY += rowPane.getLayoutY();
//                toGoX += tablePane.getLayoutX();
//                toGoY += tablePane.getLayoutY();
//                toGoX += canteenAnchorPane.getLayoutX();
//                toGoY += canteenAnchorPane.getLayoutY();
//
//                System.out.println("Seat: " + seat.getId() + "coords x = " + toGoX + " y = " + toGoY);
//
//                int finalToGoX = toGoX;
//                int finalToGoY = toGoY;
//                Platform.runLater(() -> {
//                    clientsOnScene.compute(tableSeatDto.getId(), (id, image) -> {
//                        pathTransition.setDuration(Duration.millis(1000));
//                        pathTransition.setNode(image);
//                        pathTransition.setPath(new Polyline(image.getX(), image.getY(), finalToGoX, finalToGoY));
//                        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//                        pathTransition.setOnFinished(event -> {
//                            System.out.println("Finished animation for client id: " + tableSeatDto.getId() + " table = " + tableSeatDto.getTable() + " seat = " + tableSeatDto.getSeat());
//                        });
//                        pathTransition.play();
//                        image.setX(finalToGoX);
//                        image.setY(finalToGoY);
//
//                        return image;
//                    });
//                });
//            }
                    });
                }
            }
            System.out.println("Current clients on scene: " + clientsOnScene.size());
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    @FXML
    protected void onStopButtonClick() {
        currentGenerator.interrupt();
        simulationManager.stopSimulation();
    }
}