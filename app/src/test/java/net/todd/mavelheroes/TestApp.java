package net.todd.mavelheroes;

public class TestApp extends DaggerApp {
    private ApplicationComponent testApplicationComponent;

    @Override
    public ApplicationComponent getApplicationComponent() {
        return testApplicationComponent;
    }

    public void setComponent(ApplicationComponent testApplicationComponent) {
        this.testApplicationComponent = testApplicationComponent;
    }
}