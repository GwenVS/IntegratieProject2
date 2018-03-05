package be.kdg.kandoe.domain.theme;

import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.repository.jpa.ThemeJpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Theme {
    private Long themeId;
    private String name;
    private String description;


    public Theme(){

    }
    public Theme(ThemeDto themeDto){
        this.themeId=themeDto.getThemeId();
        this.name=themeDto.getName();
        this.description=themeDto.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getThemeId() {
        return themeId;
    }
    public void setThemeId(Long id){
        this.themeId=id;
    }

}
