package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Videogioco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String titolo;

    @Enumerated(EnumType.STRING)
    private Genere genere;

    @Column(length = 2000)
    private String descrizione;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataUscita;


    @Enumerated(EnumType.STRING)
    private PegiRating pegi;


    @ManyToOne
    @JoinColumn(name = "casa_produttrice_id")
    private CasaProduttrice casaProduttrice;


    @OneToMany(mappedBy = "videogioco", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recensione> recensioni;


    @OneToOne
    @JoinColumn(name = "copertina_id")  //test per vedere senza immagini
    private Immagine copertina;

    
   
    


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitolo() {
        return titolo;
    }


    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    public Genere getGenere() {
        return genere;
    }


    public void setGenere(Genere genere) {
        this.genere = genere;
    }


    public String getDescrizione() {
        return descrizione;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public LocalDate getDataUscita() {
        return dataUscita;
    }


    public void setDataUscita(LocalDate dataUscita) {
        this.dataUscita = dataUscita;
    }


    public PegiRating getPegi() {
        return pegi;
    }


    public void setPegi(PegiRating pegi) {
        this.pegi = pegi;
    }


    public CasaProduttrice getCasaProduttrice() {
        return casaProduttrice;
    }


    public void setCasaProduttrice(CasaProduttrice casaProduttrice) {
        this.casaProduttrice = casaProduttrice;
    }


    public List<Recensione> getRecensioni() {
        return recensioni;
    }


    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }


    public Immagine getCopertina() {
        return copertina;
    }


    public void setCopertina(Immagine copertina) {
        this.copertina = copertina;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((titolo == null) ? 0 : titolo.hashCode());
        result = prime * result + ((genere == null) ? 0 : genere.hashCode());
        result = prime * result + ((pegi == null) ? 0 : pegi.hashCode());
        result = prime * result + ((casaProduttrice == null) ? 0 : casaProduttrice.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Videogioco other = (Videogioco) obj;
        if (titolo == null) {
            if (other.titolo != null)
                return false;
        } else if (!titolo.equals(other.titolo))
            return false;
        if (genere != other.genere)
            return false;
        if (pegi != other.pegi)
            return false;
        if (casaProduttrice == null) {
            if (other.casaProduttrice != null)
                return false;
        } else if (!casaProduttrice.equals(other.casaProduttrice))
            return false;
        return true;
    }


    

}
