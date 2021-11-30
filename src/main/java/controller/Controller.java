package controller;

import model.Respond;

import java.util.concurrent.Callable;

public abstract class Controller {
    public Respond request(Callable<Respond> func) {
        try {
            return func.call();
        } catch (Exception e) {
            return new Respond(false, e.getMessage());
        }
    }
}
