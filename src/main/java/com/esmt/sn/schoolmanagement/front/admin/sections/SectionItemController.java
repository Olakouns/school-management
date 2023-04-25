package com.esmt.sn.schoolmanagement.front.admin.sections;

import com.esmt.sn.schoolmanagement.SchoolManagementApplication;
import com.esmt.sn.schoolmanagement.models.Section;
import com.esmt.sn.schoolmanagement.models.enums.SectionType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SectionItemController implements Initializable {
    public Label sectionName;
    public ImageView imageView;
    public Label nbrClass;
    public Label nbrRubrics;
    private Section section;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setSection(Section section) {
        this.section = section;
        this.nbrClass.setText(this.section.getClasses().size()+"");
        this.nbrRubrics.setText(this.section.getRubriques().size()+"");
        this.populateDate();
    }

    private void populateDate() {
        this.sectionName.setText(this.section.getNom());
        if (this.section.getSectionType() == SectionType.MATERNELLE) {
            imageView.setImage(new Image("/asserts/maternelle.png"));

        } else if (this.section.getSectionType() == SectionType.ELEMENTAIRE) {
            imageView.setImage(new Image("/asserts/school.png"));
        } else if (this.section.getSectionType() == SectionType.COLLEGE) {
            imageView.setImage(new Image("/asserts/college.png"));
        } else {
            imageView.setImage(new Image("/asserts/froid.png"));
        }
    }

    public void onEdit(ActionEvent actionEvent) {
    }

    public void onDelete(ActionEvent actionEvent) {
    }
}
