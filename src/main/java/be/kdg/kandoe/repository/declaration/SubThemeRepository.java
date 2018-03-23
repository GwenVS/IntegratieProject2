package be.kdg.kandoe.repository.declaration;

import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubThemeRepository extends JpaRepository<SubTheme, Long> {
    List<SubTheme> findSubThemesByTheme(Theme theme);
}
