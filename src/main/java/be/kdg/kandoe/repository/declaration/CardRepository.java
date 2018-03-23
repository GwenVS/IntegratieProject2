package be.kdg.kandoe.repository.declaration;

import be.kdg.kandoe.domain.theme.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findCardsByCardSubThemes();
}
