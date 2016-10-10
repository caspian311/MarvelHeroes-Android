package net.todd.mavelheroes;

public class Presenter<T extends PresenterView> {
    private T view;

    void setView(T view) {
        this.view = view;
    }

    T getView() {
        return this.view;
    }
}
