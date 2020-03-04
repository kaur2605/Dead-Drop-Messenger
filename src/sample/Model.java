package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
    private ObservableList<CharSequence> paragraph = FXCollections.observableArrayList();

    public final ObservableList<CharSequence> getParagraph() {
        return this.paragraph;
    }

    public final byte[] getParagraphBytes() {
        return String.join("\n", this.getParagraph()).getBytes();
    }




}
