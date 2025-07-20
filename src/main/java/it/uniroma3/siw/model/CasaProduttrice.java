package it.uniroma3.siw.model;

import java.time.Year;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class CasaProduttrice {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String nome;

    @Column(length = 2000)
    private String testo;


    @Column(name = "anno_pubblicazione")
    private Year annoPubblicazione;

    @OneToOne
    private Immagine logo;

    @OneToMany(mappedBy = "casaProduttrice",cascade = CascadeType.ALL)
    private List<Videogioco> videoGiochi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Year getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(Year annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public Immagine getLogo() {
        return logo;
    }

    public void setLogo(Immagine logo) {
        this.logo = logo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((annoPubblicazione == null) ? 0 : annoPubblicazione.hashCode());
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
        CasaProduttrice other = (CasaProduttrice) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (annoPubblicazione == null) {
            if (other.annoPubblicazione != null)
                return false;
        } else if (!annoPubblicazione.equals(other.annoPubblicazione))
            return false;
        return true;
    }


    

}
