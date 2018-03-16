package be.kdg.kandoe.dto.converter;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.theme.ThemeDto;

public abstract class DtoConverter {

    public static ThemeDto toThemeDto(Theme theme){
        ThemeDto newDto = new ThemeDto();
        newDto.setThemeId(theme.getThemeId());
        newDto.setName(theme.getName());
        newDto.setDescription(theme.getDescription());
        return newDto;
    }
}
