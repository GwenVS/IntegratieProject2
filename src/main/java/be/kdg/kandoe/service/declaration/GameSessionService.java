package be.kdg.kandoe.service.declaration;

import be.kdg.kandoe.domain.GameSession;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.user.User;

import java.util.List;

public interface GameSessionService {
    List<GameSession> getAllGameSessions();
    List<GameSession> getUserGameSessions(String username);
    GameSession addGameSession(GameSession gameSession); //save
    GameSession getGameSessionWithId(Long id);
    GameSession updateGameSession(GameSession gameSession);
    GameSession addUserToGameSession(Long id, User user);
    void start(long gameSessionId);
    void stop(long gameSessionId);

    SubTheme getGameSessionSubTheme(long sessionId);
//    GameSessionRole getRoleOfUserInGameSession(long id, String username);
//    GameSession upgradeAccessLevelOfUserInGameSession(long id, User user);
}
