package vue;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

/**
 * Created by elmhaidara on 26/11/15.
 */
public class PaneZoomable extends Pane {
    /**
     * Propriété pour le facteur de zoom
     */
    private DoubleProperty zoomFactor = new SimpleDoubleProperty(1);

    /**
     * La scrollpane
     */
    private ScrollPane scrollPane;

    public PaneZoomable() {
        super();
    }


    public void initZoom(ScrollPane scrollPane) {
        Scale scale = new Scale(1, 1);
        getTransforms().add(scale);
        this.scrollPane = scrollPane;
        zoomFactor.addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scale.setX(newValue.doubleValue());
                scale.setY(newValue.doubleValue());

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setPrefSize(
                                Math.max(getBoundsInParent().getMaxX(), scrollPane.getViewportBounds().getWidth()),
                                Math.max(getBoundsInParent().getMaxY(), scrollPane.getViewportBounds().getHeight())
                        );
                    }
                });
                requestLayout();
            }
        });
        initialiserListenner();
    }

    private void initialiserListenner() {

        scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            // récuperation du x et y max
            double maxX = 0;
            double maxY = 0;
            for (Node currentNode : getChildren()) {
                maxX = Math.max(maxX, currentNode.getBoundsInParent().getMaxX());
                maxY = Math.max(maxY, currentNode.getBoundsInParent().getMaxY());
            }

            setPrefSize(
                    Math.max(maxX, scrollPane.getViewportBounds().getWidth()),
                    Math.max(maxY, scrollPane.getViewportBounds().getHeight())
            );
        });

    }

    public void setListenerForNode(Node n) {
        n.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                setPrefSize(
                        Math.max(newValue.getMaxX(), scrollPane.getViewportBounds().getWidth()),
                        Math.max(newValue.getMaxY(), scrollPane.getViewportBounds().getHeight())
                );
            }
        });

    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }



    @Override
    protected void layoutChildren() {
        Pos pos = Pos.TOP_LEFT;
        double width = getWidth();
        double height = getHeight();
        double top = getInsets().getTop();
        double right = getInsets().getRight();
        double left = getInsets().getLeft();
        double bottom = getInsets().getBottom();
        double contentWidth = (width - left - right) / zoomFactor.get();
        double contentHeight = (height - top - bottom) / zoomFactor.get();
        layoutInArea(this, left, top,
                contentWidth, contentHeight,
                0, null,
                pos.getHpos(),
                pos.getVpos());
    }

    public final Double getZoomFactor() {
        return zoomFactor.get();
    }

    public final void setZoomFactor(Double zoomFactor) {
        this.zoomFactor.set(zoomFactor);
    }

    public final DoubleProperty zoomFactorProperty() {
        return zoomFactor;
    }
}
