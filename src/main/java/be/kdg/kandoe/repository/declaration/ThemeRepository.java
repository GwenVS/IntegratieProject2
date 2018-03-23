package be.kdg.kandoe.repository.declaration;

import be.kdg.kandoe.domain.theme.Card;
import be.kdg.kandoe.domain.theme.CardSubTheme;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long>, ThemeRepositoryCustom {
    Theme findThemeByThemeId(long themeId);
    Theme findThemeByName(String name);
}
