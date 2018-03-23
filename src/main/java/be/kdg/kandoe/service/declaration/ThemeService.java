package be.kdg.kandoe.service.declaration;


import be.kdg.kandoe.domain.theme.Card;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.theme.CardDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ThemeService {

    Theme addTheme(Theme theme1);
    SubTheme addSubThemeByThemeId(SubTheme subTheme,long themeId);
    Theme getThemeByName(String name);
    Theme getThemeById(long id);
    SubTheme getSubThemeById(long subThemeId);

    Theme editTheme(Theme theme);
    SubTheme editSubtheme(SubTheme subTheme);

    void removeThemeByThemeId(long themeId);
    void removeSubThemeById(long subThemeId);
    void removeSubThemesByThemeId(long themeId);
    void removeAllThemes();

    List<Theme> getAllThemes();
    List<SubTheme> getAllSubThemes();
    List<SubTheme> getSubThemesByThemeId(long id);

    List<Card> getCardsBySubthemeId(long subthemeId);
    Card getCardById(long cardId);
    Card editCard(Card card);
    void removeCardById(long cardId);

    Card addCard(Card newCard);
    SubTheme addCardToSubTheme(long cardId, long subThemeId);
    void removeCardsFromSubTheme(long i);
    List<Card> getAllCards();
}
