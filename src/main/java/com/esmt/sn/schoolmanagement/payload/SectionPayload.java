package com.esmt.sn.schoolmanagement.payload;

import com.esmt.sn.schoolmanagement.models.enums.SectionType;

public class SectionPayload {

    private SectionType sectionType;
    private String name;

    public SectionPayload() {
    }

    public SectionPayload(SectionType sectionType, String name) {
        this.sectionType = sectionType;
        this.name = name;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
