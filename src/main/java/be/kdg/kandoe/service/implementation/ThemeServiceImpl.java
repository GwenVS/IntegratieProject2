package be.kdg.kandoe.service.implementation;

import be.kdg.kandoe.domain.theme.Card;
import be.kdg.kandoe.domain.theme.CardSubTheme;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.repository.declaration.CardRepository;
import be.kdg.kandoe.repository.declaration.CardSubThemeRepository;
import be.kdg.kandoe.repository.declaration.SubThemeRepository;
import be.kdg.kandoe.repository.declaration.ThemeRepository;
import be.kdg.kandoe.service.declaration.ThemeService;
import be.kdg.kandoe.service.exception.InputValidationException;
import be.kdg.kandoe.service.exception.ThemeRepositoryException;
import be.kdg.kandoe.service.exception.ThemeServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Primary
public class ThemeServiceImpl implements ThemeService {

    private ThemeRepository themeRepo;
    private SubThemeRepository subThemeRepo;
    private CardRepository cardRepo;
    private CardSubThemeRepository cardSubThemeRepo;

    @Autowired
    public ThemeServiceImpl(ThemeRepository repository, SubThemeRepository subThemeRepo, CardRepository cardRepo, CardSubThemeRepository cardSubThemeRepo) {
        this.themeRepo = repository;
        this.subThemeRepo = subThemeRepo;
        this.cardRepo = cardRepo;
        this.cardSubThemeRepo = cardSubThemeRepo;
    }

    //ADD-METHODS
    @Override
    public Theme addTheme(Theme theme) throws ThemeRepositoryException {
        if (checkNameLength(theme)) {
            return themeRepo.save(theme);
        } else {
            throw new InputValidationException("Theme name too long");
        }
    }

    @Override
    public SubTheme addSubThemeByThemeId(SubTheme subTheme, long themeId) throws ThemeRepositoryException {
        Theme themeToAdd = themeRepo.findOne(themeId);
        subTheme.setTheme(themeToAdd);
        SubTheme result = subThemeRepo.save(subTheme);
        return result;
    }

    @Override
    public Card addCard(Card card) throws ThemeRepositoryException {
        return cardRepo.save(card);
    }

    @Override
    public SubTheme addCardToSubTheme(long cardId, long subThemeId) {
        SubTheme subThemeForCard = subThemeRepo.findOne(subThemeId);
        Card cardToAdd = cardRepo.findOne(cardId);
        for (CardSubTheme card : subThemeForCard.getCardSubThemes()) {
            if (card.getCard().getCardId() == cardId && card.getSubTheme().getSubThemeId() == subThemeId) {
                throw new ThemeServiceException("Card Already exists in SubTheme");
            }
        }
        CardSubTheme cst = new CardSubTheme(cardToAdd, subThemeForCard);

        CardSubTheme addedCST = cardSubThemeRepo.save(cst);
        subThemeForCard.addCard(addedCST);
        cardToAdd.addCardSubTheme(addedCST);
        SubTheme result1 = subThemeRepo.save(subThemeForCard);
        Card card1 = cardRepo.save(cardToAdd);
        return subThemeRepo.findOne(subThemeId);
    }

    //ADD-METHODS
    //GET-METHODS
    @Override
    public Theme getThemeByName(String name) throws ThemeRepositoryException {
        Theme themeToFind = themeRepo.findThemeByName(name);
        return themeToFind;
    }

    @Override
    public Theme getThemeById(long id) throws ThemeRepositoryException {
        Theme foundTheme = themeRepo.findOne(id);
        return themeRepo.findOne(id);
    }

    @Override
    public SubTheme getSubThemeById(long subThemeId) throws ThemeRepositoryException {
        SubTheme foundSubTheme = subThemeRepo.findOne(subThemeId);
        return foundSubTheme;
    }

    @Override
    public List<Theme> getAllThemes() throws ThemeRepositoryException {
        return themeRepo.findAll();
    }

    @Override
    public List<SubTheme> getAllSubThemes() throws ThemeRepositoryException {
        return subThemeRepo.findAll();
    }

    @Override
    public List<SubTheme> getSubThemesByThemeId(long id) throws ThemeRepositoryException {
        Theme theme = themeRepo.findOne(id);
        return subThemeRepo.findSubThemesByTheme(theme);
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepo.findAll();
    }

    @Override
    public List<Card> getCardsBySubthemeId(long subthemeId) throws ThemeRepositoryException {
        SubTheme subTheme = subThemeRepo.findOne(subthemeId);
        List<CardSubTheme> cardSubThemelist = subTheme.getCardSubThemes();
        List<Card> cards = new ArrayList<>();
        for (CardSubTheme cardSubTheme : cardSubThemelist) {
            Card card = cardSubTheme.getCard();
            cards.add(card);
        }
        return cards;
    }

    @Override
    public Card getCardById(long cardId) throws ThemeRepositoryException {
        Card card = cardRepo.findOne(cardId);
        return card;
    }

    @Override
    public Card editCard(Card card) throws ThemeRepositoryException {
        return cardRepo.save(card);
    }

    @Override
    public void removeCardById(long cardId) throws ThemeRepositoryException {
        cardRepo.delete(cardId);
    }

    //GET-METHODS
    //EDIT-METHODS
    @Override
    public Theme editTheme(Theme theme) throws ThemeRepositoryException {
        if (checkNameLength(theme)) return themeRepo.save(theme);
        throw new InputValidationException("Themename too long");
    }

    @Override
    public SubTheme editSubtheme(SubTheme subTheme) {
        return subThemeRepo.save(subTheme);
    }

    //EDIT-METHODS
    //REMOVE-METHODS

    public void removeAllThemes() {
        themeRepo.deleteAll();
    }

    @Override
    public void removeThemeByThemeId(long themeId) throws ThemeRepositoryException {
        try {
            themeRepo.delete(themeId);
        } catch (ThemeRepositoryException e) {
            throw e;
        }
    }

    @Override
    public void removeSubThemeById(long subThemeId) throws ThemeRepositoryException {
        subThemeRepo.delete(subThemeId);
    }

    @Override
    public void removeSubThemesByThemeId(long themeId) throws ThemeRepositoryException {
        Theme theme = themeRepo.findOne(themeId);
        List<SubTheme> subThemes = subThemeRepo.findSubThemesByTheme(theme);
        for (SubTheme st : subThemes) {
            subThemeRepo.delete(subThemes);
        }
    }

    @Override
    public void removeCardsFromSubTheme(long subThemeId) {
        SubTheme subThemeToEdit = subThemeRepo.findOne(subThemeId);
        subThemeToEdit.setCardSubThemes(new ArrayList<>());
        subThemeRepo.save(subThemeToEdit);
    }
    //REMOVE-METHODS

    /**
     * Checks if the themeName is not longer than 50 characters
     *
     * @param theme
     * @return boolean
     */
    private boolean checkNameLength(Theme theme) {
        if (theme.getName().length() <= 50) {
            return true;
        }
        return false;
    }
}
