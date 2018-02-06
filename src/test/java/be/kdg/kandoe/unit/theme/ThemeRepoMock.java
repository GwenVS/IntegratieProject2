package be.kdg.kandoe.unit.theme;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.repository.declaration.ThemeRepository;

import java.util.ArrayList;
import java.util.List;

public class ThemeRepoMock implements ThemeRepository {

    List<Theme> themes = new ArrayList<>();

    @Override
    public Theme findThemeByName(String name) {
        for (Theme t: themes
             ) {
           if(t.getName().equals(name)) {
               return t;
           }
        }
        return null;
    }

    @Override
    public Theme findThemeById(Long id) {
        for (Theme t: themes
             ) {
            if(t.getThemeId() == id) {
                return t;
            }
        }
        return null;
    }

    @Override
    public Theme createTheme(Theme theme) {
        themes.add(theme);
        return themes.get(themes.indexOf(theme));
    }

    @Override
    public Theme editTheme(Theme theme) {
        Theme themeToReturn=null;
        for (Theme t:themes){
            if(theme.getThemeId()==t.getThemeId()){
                t.setName(theme.getName());
                t.setDescription(theme.getDescription());
                themeToReturn= t;
            }
        }
        return themeToReturn;
    }
}
