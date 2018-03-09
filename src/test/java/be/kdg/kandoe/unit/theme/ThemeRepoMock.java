package be.kdg.kandoe.unit.theme;

import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.repository.declaration.ThemeRepository;
import be.kdg.kandoe.service.declaration.ThemeService;
import be.kdg.kandoe.service.exception.ThemeRepositoryException;
import be.kdg.kandoe.service.exception.ThemeServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

public class ThemeRepoMock implements ThemeRepository {
    List<Theme> themes = new ArrayList<>();
    List<SubTheme> subThemes = new ArrayList<>();

    public Theme findThemeByName(String name) {
        for (Theme t: themes
             ) {
           if(t.getName().equals(name)) {
               return t;
           }
        }
        return null;
    }

    public Theme findThemeById(long id) {
        for (Theme t: themes
             ) {
            if(t.getThemeId() == id) {
                return t;
            }
        }
        return null;
    }

    public SubTheme findSubThemeById(long id){
        for (SubTheme st:subThemes
             ) {
            if(st.getSubThemeId()==(id)){
                return st;
            }
        }
        return null;
    }

    public Theme createTheme(Theme theme) {
        Theme themeToAdd = theme;
        Long size = Long.parseLong(String.valueOf(themes.size()+1));
        themeToAdd.setThemeId(size);
        themes.add(themeToAdd);
        return themes.get(themes.indexOf(themeToAdd));
    }

    @Override
    public SubTheme createSubTheme(SubTheme subTheme) {
        subThemes.add(subTheme);
        return subThemes.get(subThemes.indexOf(subTheme));
    }

    public Theme editTheme(Theme theme) {
        Theme themeToFind =null;
        for (Theme t:themes
             ) {
            if(t.getThemeId()==theme.getThemeId()){
                t.setName(theme.getName());
                t.setDescription(theme.getDescription());
                themeToFind=t;
            }
        }
        if (themeToFind==null){
            return null;
        }
        return themeToFind;
    }

    @Override
    public SubTheme editSubTheme(SubTheme subTheme) {
        if(themes.contains(subTheme)){
            SubTheme subTheme1 = subThemes.get(subThemes.indexOf(subTheme));
            subTheme1.setSubThemeName(subTheme.getSubThemeName());
            subTheme1.setSubThemeDescription(subTheme.getSubThemeDescription());
            subTheme1.setTheme(subTheme.getTheme());
            return subTheme1;
        }
        return null;
    }

    public Theme deleteThemeByName(String name) {
        Theme themeToFind =null;
        for (Theme t: themes
             ) {
            if(t.getName().equals(name)){
                themeToFind=t;
            }
        }
        if(themeToFind==null){
            return null;
        }
        themes.remove(themeToFind);
        return themeToFind;
    }


    public Theme deleteThemeByThemeId(Long themeId) {
        Theme themeToFind =null;
        for (Theme t: themes
                ) {
            if(t.getThemeId()==themeId){
                themeToFind=t;
            }
        }
        if(themeToFind==null){
            return null;
        }
        themes.remove(themeToFind);
        return themeToFind;
    }

    @Override
    public SubTheme deleteSubTheme(SubTheme subTheme){
        for (SubTheme st:subThemes
             ) {
            if(st.equals(subTheme)){
                subThemes.remove(subTheme);
                return subTheme;
            }
        }
        return null;
    }
    @Override
    public Theme deleteTheme(Theme theme) {
        themes.remove(theme);
        return theme;
    }

    @Override
    public void deleteAll(){
        themes = new ArrayList<>();
    }
    @Override
    public List<Theme> findAllThemes() {
        if(themes!=null){
            return themes;
        }
        else return null;
    }

    @Override
    public List<SubTheme> findAllSubThemes() {
        if(themes!=null){
            return subThemes;
        }
        return null;
    }

    @Override
    public List<SubTheme> findSubThemesByThemeId(long id){
        List<SubTheme> subThemes = new ArrayList<>();
        for(SubTheme st: this.subThemes){
            if(st.getTheme().getThemeId()==id){
                subThemes.add(st);
            }
        }
        return subThemes;
    }


}
