package be.kdg.kandoe.repository.implementation;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.repository.declaration.ThemeRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ThemeRepositoryImpl implements ThemeRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public ThemeRepositoryImpl(EntityManager entityManager) {
        em = entityManager;
    }


    //todo : methods
}

