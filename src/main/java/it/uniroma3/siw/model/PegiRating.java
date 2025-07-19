package it.uniroma3.siw.model;

public enum PegiRating {
    

    PEGI_3(3, "Contenuto adatto a tutte le età"),
    PEGI_7(7, "Potrebbero esserci scene spaventose"),
    PEGI_12(12, "Violenza o linguaggio moderato"),
    PEGI_16(16, "Violenza realistica, linguaggio volgare"),
    PEGI_18(18, "Contenuto per adulti, violenza esplicita");

    private final int valore;
    private final String descrizione;

    PegiRating(int valore, String descrizione) {
        this.valore = valore;
        this.descrizione = descrizione;
    }

    public int getValore() {
        return valore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String toString() {
        return "PEGI " + valore;
    }


}
