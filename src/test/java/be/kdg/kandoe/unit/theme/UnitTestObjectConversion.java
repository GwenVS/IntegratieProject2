package be.kdg.kandoe.unit.theme;

import be.kdg.kandoe.domain.theme.Card;
import be.kdg.kandoe.domain.theme.CardSubTheme;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.converter.DtoConverter;
import be.kdg.kandoe.dto.theme.CardDto;
import be.kdg.kandoe.dto.theme.CardSubThemeDto;
import be.kdg.kandoe.dto.theme.SubThemeDto;
import be.kdg.kandoe.dto.theme.ThemeDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UnitTestObjectConversion {
    private Theme themeToTest;
    private ThemeDto themeDtoToTest;

    private SubTheme subThemeToTest;
    private SubTheme subThemeToTestCards;
    private SubThemeDto subThemeDtoToTestCards;
    private SubThemeDto subThemeDtoToTest;

    private Card cardToTest;
    private CardDto cardDtoToTest;

    @Before
    public void Setup() {
        themeToTest = new Theme();
        themeToTest.setThemeId(new Long(1));
        themeToTest.setName("School");
        themeToTest.setDescription("Theme to test conversion");

        themeDtoToTest = new ThemeDto(2, "Industry", "Testing if this theme stays the same");

        subThemeToTest = new SubTheme();
        subThemeToTest.setSubThemeId(new Long(1));
        subThemeToTest.setSubThemeName("Product Quality");
        subThemeToTest.setSubThemeDescription("Subtheme for theme: Industry");
        subThemeToTest.setTheme(themeToTest);

        subThemeDtoToTest = new SubThemeDto(2, themeDtoToTest, "Employee hapiness", "2nd subtheme for theme: Industry", new ArrayList<>());

        subThemeToTestCards = new SubTheme();
        subThemeToTestCards.setSubThemeId(new Long(4));
        subThemeToTestCards.setSubThemeName("Development");
        subThemeToTestCards.setSubThemeDescription("Improving development");
        subThemeToTestCards.setTheme(themeToTest);

        subThemeDtoToTestCards = new SubThemeDto(new Long(6), "Development", "Improving development");
        CardSubThemeDto cstDto = new CardSubThemeDto(3, cardDtoToTest, subThemeDtoToTest);
        cardDtoToTest.setCardSubThemes(Arrays.asList(new CardSubThemeDto[]{cstDto}));
        subThemeDtoToTest.setCardSubThemes(Arrays.asList(new CardSubThemeDto[]{cstDto}));

        cardToTest = new Card();
        cardToTest.setCardId(1);
        cardToTest.setName("Better packaging");
        cardToTest.setDescription("Longer lasting product");
        cardToTest.setDefaultCard(false);
        CardSubTheme cst = new CardSubTheme(1, cardToTest, subThemeToTest);
        cardToTest.setCardSubThemes(Arrays.asList(new CardSubTheme[]{cst}));
        subThemeToTest.setCardSubThemes(Arrays.asList(new CardSubTheme[]{cst}));

        cardDtoToTest = new CardDto(2, "Bigger platform", "Expanding platform size", false);

    }

    @Test
    public void TestThemeToThemeDto() {
        ThemeDto dto = DtoConverter.toThemeDto(themeToTest, false);
        Assert.assertThat(dto.getThemeId(), equalTo(themeToTest.getThemeId()));
        Assert.assertThat(dto.getName(), equalTo(themeToTest.getName()));
        Assert.assertThat(dto.getDescription(), equalTo(themeToTest.getDescription()));
    }

    @Test
    public void TestThemeDtoToTheme() {
        Theme theme = DtoConverter.toTheme(themeDtoToTest, false);
        Assert.assertThat(theme.getThemeId(), equalTo(themeDtoToTest.getThemeId()));
        Assert.assertThat(theme.getName(), equalTo(themeDtoToTest.getName()));
        Assert.assertThat(theme.getDescription(), equalTo(themeDtoToTest.getDescription()));
    }

    @Test
    public void TestSubThemeToSubThemeDto() {
        SubThemeDto dto = DtoConverter.toSubThemeDto(subThemeToTest, false);
        Assert.assertThat(dto.getTheme().getThemeId(), equalTo(subThemeToTest.getTheme().getThemeId()));
        Assert.assertThat(dto.getTheme().getName(), equalTo(subThemeToTest.getTheme().getName()));
        Assert.assertThat(dto.getTheme().getDescription(), equalTo(subThemeToTest.getTheme().getDescription()));
        Assert.assertThat(dto.getSubThemeId(), equalTo(subThemeToTest.getSubThemeId()));
        Assert.assertThat(dto.getSubThemeName(), equalTo(subThemeToTest.getSubThemeName()));
        Assert.assertThat(dto.getSubThemeDescription(), equalTo(subThemeToTest.getSubThemeDescription()));
        Assert.assertThat(dto.getCardSubThemes().size(), equalTo(subThemeToTest.getCardSubThemes().size()));
        Assert.assertThat(dto.getCardSubThemes().get(0).getSubTheme(), equalTo(cardToTest.getCardId()));
    }

    @Test
    public void TestSubThemeDtoToSubTheme() {
        SubTheme subTheme = DtoConverter.toSubTheme(subThemeDtoToTest, false);
        Assert.assertThat(subTheme.getTheme().getClass(), not(subThemeDtoToTest.getTheme()));
        Assert.assertThat(subTheme.getTheme().getThemeId(), equalTo(subThemeDtoToTest.getTheme().getThemeId()));
        Assert.assertThat(subTheme.getTheme().getName(), equalTo(subThemeDtoToTest.getTheme().getName()));
        Assert.assertThat(subTheme.getTheme().getDescription(), equalTo(subThemeDtoToTest.getTheme().getDescription()));
        Assert.assertThat(subTheme.getSubThemeId(), equalTo(subThemeDtoToTest.getSubThemeId()));
        Assert.assertThat(subTheme.getSubThemeName(), equalTo(subThemeDtoToTest.getSubThemeName()));
        Assert.assertThat(subTheme.getSubThemeDescription(), equalTo(subThemeDtoToTest.getSubThemeDescription()));
        Assert.assertThat(subTheme.getCardSubThemes().size(), equalTo(subThemeDtoToTest.getCardSubThemes().size()));
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}
