package be.kdg.kandoe.dto;

import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import com.google.gson.Gson;

public class SubThemeDto {
    private long subThemeId;
    private ThemeDto theme;
    private String subThemeName;
    private String subThemeDescription;

    public SubThemeDto(){
        this.subThemeName="Default";
        this.subThemeDescription="Default description";
        this.theme=null;
    }

    public SubThemeDto(long subThemeId,ThemeDto theme,String subThemeName,String subThemeDescription){
        this.subThemeId=subThemeId;
        this.theme=theme;
        this.subThemeName=subThemeName;
        this.subThemeDescription=subThemeDescription;
    }

    public Long getSubThemeId() {
        return subThemeId;
    }

    public void setSubThemeId(Long subThemeId) {
        this.subThemeId = subThemeId;
    }

    public ThemeDto getTheme() {
        return theme;
    }

    public void setTheme(ThemeDto theme) {
        this.theme = theme;
    }

    public String getSubThemeName() {
        return subThemeName;
    }

    public void setSubThemeName(String subThemeName) {
        this.subThemeName = subThemeName;
    }

    public String getSubThemeDescription() {
        return subThemeDescription;
    }

    public void setSubThemeDescription(String subThemeDescription) {
        this.subThemeDescription = subThemeDescription;
    }

    public String toJsonString(){
        String JSON = new Gson().toJson(this);
        return JSON;
    }

    public SubTheme toSubTheme(){
        SubTheme subTheme = new SubTheme();
        subTheme.setSubThemeDescription(this.subThemeDescription);
        subTheme.setSubThemeId(this.subThemeId);
        subTheme.setTheme(new Theme(this.theme));
        subTheme.setSubThemeName(this.subThemeName);
        return subTheme;
    }

    public static SubThemeDto fromSubTheme(SubTheme subTheme){
        return new SubThemeDto(subTheme.getSubThemeId(),ThemeDto.fromTheme(subTheme.getTheme()),subTheme.getSubThemeName(),subTheme.getSubThemeDescription());
    }
}
