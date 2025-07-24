package it.uniroma3.siw.model;



public enum Genere{
    ACTION("Azione"),
    ADVENTURE("Avventura"),
    RPG("Gioco di ruolo"),
    FPS("Sparatutto in prima persona"),
    STRATEGY("Strategia"),
    HORROR("Horror"),
    PUZZLE("Rompicapo"),
    SPORTS("Sportivo"),
    RACING("Corsa"),
    SIMULATION("Simulazione"),
    PLATFORM("Platform"),
    MMO("Massively Multiplayer Online");

    private final String descrizione;

    Genere(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String toString() {
        return descrizione;
    }

    
}
