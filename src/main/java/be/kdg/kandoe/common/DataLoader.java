package be.kdg.kandoe.common;

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
        SubTheme subTheme = new SubTheme();
        subTheme.setSubThemeName("Testsubtheme");
        subTheme.setSubThemeDescription("subtesttesttest");
        Theme theme = new Theme();
        theme.setName("TestTheme");
        theme.setDescription("testtesttest");
        subTheme.setTheme(theme);
        List<SubTheme> subThemeList = new ArrayList<SubTheme>();
        subThemeList.add(subTheme);
        theme.setSubThemes(subThemeList);
        this.themeService.addTheme(theme);
        System.out.println(theme.toString());
    }
}
