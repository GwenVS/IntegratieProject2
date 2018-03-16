package be.kdg.kandoe.repository.declaration;

import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.repository.jpa.SubThemeJpa;
import be.kdg.kandoe.repository.jpa.ThemeJpa;

import java.util.List;

public interface ThemeRepository {
    Theme findThemeByName(String name);
    Theme findThemeById(long id);
    SubTheme findSubThemeById(long subThemeId);

    Theme createTheme(Theme theme);
    ThemeJpa createThemeNoConvert(ThemeJpa theme);
    SubTheme createSubTheme(SubTheme subTheme);
    SubThemeJpa createSubThemeNoConvert(SubThemeJpa subThemeJpa);

    Theme deleteTheme(Theme theme);
    SubTheme deleteSubTheme(SubTheme subTheme);
    void deleteAll();

    Theme editTheme(Theme theme);
    SubTheme editSubTheme(SubTheme subTheme);

    List<Theme> findAllThemes();
    List<SubTheme> findAllSubThemes();
    List<SubTheme> findSubThemesByThemeId(long id);
}
