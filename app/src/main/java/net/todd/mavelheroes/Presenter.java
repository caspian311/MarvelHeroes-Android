package net.todd.mavelheroes;

public class Presenter<T extends PresenterView> {
    private T view;

    public void setView(T view) {
        this.view = view;
    }

    public T getView() {
        return this.view;
    }
}
