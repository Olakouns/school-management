package com.esmt.sn.schoolmanagement.front.frontOffice.classes;

import com.esmt.sn.schoolmanagement.front.listeners.CallBack;
import com.esmt.sn.schoolmanagement.front.listeners.Loadable;
import com.esmt.sn.schoolmanagement.models.Classe;
import com.esmt.sn.schoolmanagement.models.enums.SectionType;
import com.esmt.sn.schoolmanagement.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClassDetailsController implements Initializable, Loadable {

    public ImageView bgClassImages;
    public Label classLabelName;
    private Classe currentClass;

    private CallBack callBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void loadData() {

    }

    public Classe getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(Classe currentClass) {
        this.currentClass = currentClass;
        this.populateData();
    }

    private void populateData(){
        this.classLabelName.setText(this.currentClass.getName());

        if (Constants.CURRENT_SECTION.getSectionType() == SectionType.MATERNELLE) {
            bgClassImages.setImage(new Image("/asserts/section-maternelle.png"));
        } else if (Constants.CURRENT_SECTION.getSectionType() == SectionType.ELEMENTAIRE) {
            bgClassImages.setImage(new Image("/asserts/section-elementaire.png"));
        } else if (Constants.CURRENT_SECTION.getSectionType() == SectionType.COLLEGE) {
            bgClassImages.setImage(new Image("/asserts/section-college.png"));
        } else {
            bgClassImages.setImage(new Image("/asserts/section-froid.png"));
        }
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void onBack(ActionEvent actionEvent) {
        this.callBack.onCall();
    }
}
