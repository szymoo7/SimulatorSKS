package sks.frontend;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sks.backend.CanteenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SKSceneManager {
    /* AnchorPanes */
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane canteenAnchorPane;

    /* ImageViews - Scene */
    @FXML
    private ImageView canteenImageView;

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

    //TODO: Naprawić
    private final List<Pane> tables = new ArrayList<>(List.of(
        tableTopLeftCornerPane,
        tableCenterTopLeftCornerPane,
        tableCenterTopRightCornerPane,
        tableTopRightCornerPane,
        tableBottomLeftCornerPane,
        tableCenterBottomLeftCornerPane,
        tableCenterBottomRightCornerPane,
        tableBottomRightCornerPane
    ));
    private final List<Pane> pairsOfChairs = new ArrayList<>(List.of(
            table1row1, table1row2, table1row3, table1row4,
            table2row1, table2row2, table2row3, table2row4,
            table3row1, table3row2, table3row3, table3row4,
            table4row1, table4row2, table4row3, table4row4,
            table5row1, table5row2, table5row3, table5row4,
            table6row1, table6row2, table6row3, table6row4,
            table7row1, table7row2, table7row3, table7row4,
            table8row1, table8row2, table8row3, table8row4
    ));

    public void initialize() {
        simulationSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                simulationManager.setSimulationSpeed(newValue.intValue());
                simulationManager.setNSeats(newValue.intValue());
                tables.forEach(table -> table.getChildren()
                        .stream().forEach(pairOfChairs -> {
                            if(pairOfChairs instanceof ImageView row) {
                                row.setVisible(false);
                            }
                        })
                );
                tables.forEach(table -> table.getChildren()
                        .stream().limit(newValue.intValue())
                        .forEach(pairOfChairs -> {
                            if(pairOfChairs instanceof ImageView row) {
                                row.setVisible(true);
                            }
                        })
                );
            }
        });
    }
}