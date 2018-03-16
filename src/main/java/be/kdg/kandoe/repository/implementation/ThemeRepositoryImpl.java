package be.kdg.kandoe.repository.implementation;

import be.kdg.kandoe.domain.theme.Card;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.repository.declaration.ThemeRepository;
import be.kdg.kandoe.repository.jpa.SubThemeJpa;
import be.kdg.kandoe.repository.jpa.ThemeJpa;
import be.kdg.kandoe.service.exception.ThemeRepositoryException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ThemeRepositoryImpl implements ThemeRepository {

     @PersistenceContext
    private final EntityManager em;

    @Autowired
    public ThemeRepositoryImpl(EntityManager entityManager) {
        em = entityManager;
    }

    @Transactional
    @Override
    public Theme findThemeByName(@Param("name")String name) {
        Query query = em.createQuery("SELECT theme from ThemeJpa theme WHERE name = :name",ThemeJpa.class).setParameter("name",name);
        System.out.println(query.getResultList().get(0));
        if(query.getResultList()==null || query.getResultList().get(0)==null){
            return null;
        }
        ThemeJpa jpa = (ThemeJpa)query.getResultList().get(0);
        return jpa.toTheme();
    }

    @Transactional
    @Override
    public Theme findThemeById(@Param("themeId")long themeId) {
        Query query= em.createQuery("SELECT theme FROM ThemeJpa theme WHERE themeId=:themeId",ThemeJpa.class).setParameter("themeId",themeId);
        if(query.getResultList().isEmpty() || query.getResultList().get(0)==null){
            return null;
        }
        ThemeJpa jpa = (ThemeJpa)query.getResultList().get(0);
        return jpa.toTheme();
    }

    @Transactional
    @Override
    public SubTheme findSubThemeById(@Param("subThemeId")long subThemeId){
        Query query= em.createQuery("SELECT subTheme FROM SubThemeJpa subTheme WHERE subThemeId=:subThemeId").setParameter("subThemeId",subThemeId);
        if (query.getResultList().isEmpty()){
            return null;
        }
        SubThemeJpa jpa = (SubThemeJpa)query.getResultList().get(0);
        return jpa.toSubTheme();
    }

    @Transactional
    @Override
    public Theme createTheme(Theme theme) {
        ThemeJpa jpa = ThemeJpa.fromTheme(theme);
        ThemeJpa o = em.merge(jpa);
        return o.toTheme();
    }

    @Transactional
    @Override
    public ThemeJpa createThemeNoConvert(ThemeJpa theme) {
        em.persist(theme);
        return theme;
    }

    @Transactional
    @Override
    public SubTheme createSubTheme(SubTheme subTheme) {
        SubThemeJpa jpa = SubThemeJpa.fromSubTheme(subTheme);
        SubThemeJpa reply = em.merge(jpa);;
        return reply.toSubTheme();
    }

    @Override
    @Transactional
    public SubThemeJpa createSubThemeNoConvert(SubThemeJpa subThemeJpa) {
        em.persist(subThemeJpa);
        return subThemeJpa;
    }

    @Transactional
    @Override
    public Theme editTheme(Theme theme) {
        ThemeJpa jpa = ThemeJpa.fromTheme(theme);
        ThemeJpa reply  = em.merge(jpa);
        return jpa.toTheme();
    }

    @Transactional
    @Override
    public SubTheme editSubTheme(SubTheme subTheme){
        SubThemeJpa jpa = SubThemeJpa.fromSubTheme(subTheme);
        SubThemeJpa result = em.merge(jpa);
        return result.toSubTheme();
    }


    @Transactional
    @Override
    public Theme deleteTheme(Theme theme){
        Query querySubTheme = em.createQuery("DELETE FROM SubThemeJpa WHERE subThemeId=:theme_subthemeId").setParameter("theme_subthemeId",theme.getThemeId());
        querySubTheme.executeUpdate();
        Query queryTheme = em.createQuery("DELETE FROM ThemeJpa WHERE themeId=:themeId").setParameter("themeId",theme.getThemeId());
        queryTheme.executeUpdate();
        return theme;
    }
    @Transactional
    @Override
    public SubTheme deleteSubTheme(SubTheme subTheme){
        SubThemeJpa jpa = SubThemeJpa.fromSubTheme(subTheme);
        em.remove(em.contains(jpa) ? jpa:em.merge(jpa));
        em.flush();
        return jpa.toSubTheme();
    }

    @Transactional
    @Override
    public void deleteAll(){
        em.createQuery("DELETE FROM SubThemeJpa").executeUpdate();
        em.flush();
        em.createQuery("DELETE FROM ThemeJpa ").executeUpdate();
        em.flush();
    }

    @Transactional
    @Override
    public List<Theme> findAllThemes() {
        Query query = em.createQuery("SELECT theme FROM ThemeJpa theme");
        List<ThemeJpa> jpas = query.getResultList();
        List<Theme> themes = new ArrayList<>();
        for(ThemeJpa jpa:jpas){
            themes.add(jpa.toTheme());
        }
        return themes;
    }
    @Transactional
    @Override
    public List<SubTheme> findAllSubThemes(){
        Query query= em.createQuery("SELECT subtheme FROM SubThemeJpa subtheme");
        List<SubThemeJpa> jpas = query.getResultList();
        return jpas.stream().map(jpa->jpa.toSubTheme()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<SubTheme> findSubThemesByThemeId(long id){
        throw new NotImplementedException();
    }




    @Override
    @Transactional
    public List<Card> findCardsBySubthemeId(long subthemeId) {
        TypedQuery<CardSubThemeJpa> q = em.createQuery("SELECT card FROM CardSubThemeJpa card WHERE card.subTheme.subThemeId=:subThemeId", CardSubThemeJpa.class).setParameter("subThemeId", subthemeId);
        if (q.getResultList().isEmpty()) {
            throw new ThemeRepositoryException("No Cards found for SubTheme with ID: " + subthemeId);
        }
        List<Card> cards = new ArrayList<>();
        for (CardSubThemeJpa jpa : q.getResultList()) {
            cards.add(JpaConverter.toCard(jpa.getCard(), false));
        }
        return cards;
    }

    @Override
    @Transactional
    public CardSubTheme createCardSubTheme(CardSubTheme cst) {
        CardSubThemeJpa jpa = JpaConverter.toCardSubThemeJpa(cst);
        CardSubThemeJpa result = em.merge(jpa);
        return JpaConverter.toCardSubTheme(result);
    }

    @Override
    @Transactional
    public List<Card> findAllCards() {
        TypedQuery<CardJpa> query = em.createQuery("SELECT card FROM CardJpa card", CardJpa.class);
        if (query.getResultList().isEmpty()) {
            throw new ThemeRepositoryException("No cards found");
        }
        return query.getResultList().stream().map(c -> JpaConverter.toCard(c, false)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Card findCardById(@Param("cardId") long cardId) {
        TypedQuery<CardJpa> q = em.createQuery("SELECT c from CardJpa c where c.cardId = :cardId", CardJpa.class);
        q.setParameter("cardId", cardId);
        if (q.getResultList().isEmpty()) {
            throw new ThemeRepositoryException("No Card found for ID: " + cardId);
        }
        return JpaConverter.toCard(q.getSingleResult(), false);
    }

    @Override
    @Transactional
    public Card createCard(Card card) {
        CardJpa jpa = JpaConverter.toCardJpa(card, false);
        em.persist(jpa);
        return JpaConverter.toCard(jpa, false);
    }

    @Override
    @Transactional
    public Card delete(Card card) {
        CardJpa jpa = JpaConverter.toCardJpa(card, false);
        em.remove(em.contains(jpa) ? jpa : em.merge(jpa));
        return JpaConverter.toCard(jpa, false);
    }

}

