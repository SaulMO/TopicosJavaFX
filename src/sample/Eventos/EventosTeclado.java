package sample.Eventos;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class EventosTeclado implements EventHandler<KeyEvent>
{
    @Override
    public void handle(KeyEvent event) {
        System.out.print(event.getCode().getName());
    }
}