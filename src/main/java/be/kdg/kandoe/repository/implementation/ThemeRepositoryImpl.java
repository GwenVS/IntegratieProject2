package be.kdg.kandoe.repository.implementation;

import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.repository.declaration.ThemeRepository;
import be.kdg.kandoe.repository.jpa.SubThemeJpa;
import be.kdg.kandoe.repository.jpa.ThemeJpa;
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
        em.merge(jpa);
        return jpa.toTheme();
    }

    @Transactional
    @Override
    public SubTheme createSubTheme(SubTheme subTheme) {
        SubThemeJpa jpa = SubThemeJpa.fromSubTheme(subTheme);
        em.merge(jpa);
        return jpa.toSubTheme();
    }

    @Transactional
    @Override
    public Theme editTheme(Theme theme) {
        ThemeJpa jpa = ThemeJpa.fromTheme(theme);
        em.merge(jpa);
        return jpa.toTheme();
    }

    @Transactional
    @Override
    public SubTheme editSubTheme(SubTheme subTheme){
        SubThemeJpa jpa = SubThemeJpa.fromSubTheme(subTheme);
        em.merge(jpa);
        return jpa.toSubTheme();
    }


    @Transactional
    @Override
    public Theme deleteTheme(Theme theme){
        ThemeJpa jpa = ThemeJpa.fromTheme(theme);
        em.remove(em.contains(jpa) ?  jpa :em.merge(jpa));
        return jpa.toTheme();
    }
    @Transactional
    @Override
    public SubTheme deleteSubTheme(SubTheme subTheme){
        SubThemeJpa jpa = SubThemeJpa.fromSubTheme(subTheme);
        em.remove(em.contains(jpa) ? jpa:em.merge(jpa));
        return jpa.toSubTheme();
    }

    @Transactional
    @Override
    public void deleteAll(){
        em.createQuery("DELETE from SubThemeJpa").executeUpdate();
        em.createNativeQuery("ALTER TABLE SUBTHEME ALTER COLUMN sub_Theme_id RESTART WITH 1").executeUpdate();
        em.createQuery("DELETE FROM ThemeJpa ").executeUpdate();
        em.createNativeQuery("ALTER TABLE THEME ALTER COLUMN theme_id RESTART WITH 1").executeUpdate();
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
}
