package br.unaerp.websocket;

import br.unaerp.vo.Player;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ServerEndpoint("/server")
public class ServerSocket {
    private static final List<Player> players = new ArrayList<>();

    @OnOpen
    public void onOpen (Session session) throws IOException {
        _addPlayer(session.getId());
        session.getBasicRemote().sendText("Players:" + _getQtdPlayersOnline());
    }

    @OnClose
    public void onClose (Session session) {
        _removePlayer(session.getId());
    }

    @OnMessage
    public void onMessage (String message, Session session) throws IOException {
        _bet(Double.valueOf(message), session);
    }

    private void _bet (Double betValue, Session session) throws IOException {
        Optional<Player> player = _findPlayerBySessionId(session.getId());

        if (player.isPresent() && _amountLessThanBetValue(player.get(), betValue)) {
            session.getBasicRemote().sendText("Saldo insuficiente!");
        }else if (player.isPresent()) {
            session.getBasicRemote().sendText(_bet(player.get(), betValue));
        }
    }

    private String _bet (Player player, Double betValue) {
        Integer firstElement = _getRandomNumber();
        Integer secondElement = _getRandomNumber();
        Integer thirdElement = _getRandomNumber();

        if(_win(firstElement, secondElement, thirdElement)) {
            player.setAmount(player.getAmount() + (betValue * 2));
            return _getDefaultResponse(firstElement, secondElement, thirdElement, player) + "Ganhou";
        } else {
            player.setAmount(player.getAmount() - betValue);
            return _getDefaultResponse(firstElement, secondElement, thirdElement, player) + "Perdeu";
        }
    }

    private String _getDefaultResponse (Integer firstElement, Integer secondElement, Integer thirdElement, Player player) {
        return firstElement + "," + secondElement + "," + thirdElement + "," + player.getAmount() + "," + _getQtdPlayersOnline() + ",";
    }

    private Boolean _win (Integer firstElement, Integer secondElement, Integer thirdElement) {
        return Objects.equals(firstElement, secondElement) && Objects.equals(secondElement, thirdElement);
    }

    private Boolean _amountLessThanBetValue (Player player, Double betValue) {
        return player.getAmount() < betValue;
    }

    private Optional<Player> _findPlayerBySessionId (String id) {
        return players.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    private void _addPlayer (String id) {
        players.add(new Player(id, 200.00));
    }

    private void _removePlayer (String id) {
        Optional<Player> player = _findPlayerBySessionId(id);
        player.ifPresent(p -> players.remove(p));
    }

    private Integer _getQtdPlayersOnline () {
        return players.size();
    }

    private Integer _getRandomNumber () {
        return (int) (Math.random() * 18);
    }
}