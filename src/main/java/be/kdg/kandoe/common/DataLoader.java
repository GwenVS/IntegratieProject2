package be.kdg.kandoe.common;

import be.kdg.kandoe.domain.theme.Card;
import be.kdg.kandoe.domain.theme.CardSubTheme;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.domain.user.Gender;
import be.kdg.kandoe.domain.user.User;
import be.kdg.kandoe.service.implementation.CustomUserDetailsService;
import be.kdg.kandoe.service.implementation.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private ThemeServiceImpl themeService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.userService.addUser(new User("sven", "matthys", "sveneman", "matthys.sven@gmail.com", 6, 3, 1997, "testtest", Gender.Male, null));
        this.userService.addUser(new User("beau", "boeynaems", "beauke", "beau@gmail.com", 1, 2, 1996, "lollollol", Gender.Male, null));
        this.userService.addUser(new User("gino", "moukhi", "ginothepino", "gino@gmail.com", 4, 4, 1997, "snor123snor123", Gender.Male, null));

        SubTheme subTheme1 = new SubTheme();
        subTheme1.setSubThemeName("Belgische Bieren");
        subTheme1.setSubThemeDescription("enkel Belgische bieren zijn welkom hier!");

        SubTheme subTheme2 = new SubTheme();
        subTheme2.setSubThemeName("Duitse bieren");
        subTheme2.setSubThemeDescription("enkel Duitse bieren zijn welkom hier!");

        SubTheme subTheme3 = new SubTheme();
        subTheme2.setSubThemeName("Favoriete bieren");
        subTheme2.setSubThemeDescription("enkel jouw favoriete bieren zijn welkom hier!");

        Theme theme = new Theme();
        theme.setName("TestTheme : Bier");
        theme.setDescription("Bieren over de hele wereld vergelijken");

        subTheme1.setTheme(theme);
        subTheme2.setTheme(theme);
        subTheme3.setTheme(theme);
        List<SubTheme> subThemeList = new ArrayList<SubTheme>();
        subThemeList.add(subTheme1);
        subThemeList.add(subTheme2);
        subThemeList.add(subTheme3);

        theme.setSubThemes(subThemeList);
        this.themeService.addTheme(theme);


        Card card1 = new Card();
        card1.setName("Duvel");
        card1.setDefaultCard(true);
        card1.setDescription("bierke uit Breendonk");
        CardSubTheme cardSubTheme1 = new CardSubTheme(card1,subTheme1);
        List<CardSubTheme> card1Subthemes = new ArrayList<>();
        card1Subthemes.add(cardSubTheme1);
        card1.setCardSubThemes(card1Subthemes);

        Card card2 = new Card();
        card2.setName("Triple Karmeliet");
        card2.setDefaultCard(true);
        card2.setDescription("bierke uit Dendermonde");
        CardSubTheme cardSubTheme2 = new CardSubTheme(card2,subTheme1);
        CardSubTheme cardSubTheme2bis = new CardSubTheme(card2,subTheme3);
        List<CardSubTheme> card2Subthemes = new ArrayList<>();
        card2Subthemes.add(cardSubTheme2);
        card2Subthemes.add(cardSubTheme2bis);
        card2.setCardSubThemes(card2Subthemes);

        Card card3 = new Card();
        card3.setName("Duits bierke");
        card3.setDefaultCard(true);
        card3.setDescription("bierke uit een onbekend duits dorpke");
        CardSubTheme cardSubTheme3 = new CardSubTheme(card3,subTheme2);
        List<CardSubTheme> card3Subthemes = new ArrayList<>();
        card3Subthemes.add(cardSubTheme3);
        card3.setCardSubThemes(card3Subthemes);
    }
}
