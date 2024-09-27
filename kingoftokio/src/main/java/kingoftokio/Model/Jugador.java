package kingoftokio.Model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Jugadores")
public class Jugador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id_jugador;

	@Column(name = "nom", length = 50, nullable = false)
	private String nom;
	@Column(name = "cognom", length = 50, nullable = false)
	private String cognom;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id_Monstre")
	@Column(name = "monstres")
	@JsonManagedReference
	private Set<Monstre> monstres;

	public Jugador() {
		super();
	}

	public Jugador(String nom, String cognom) {
		super();
		this.nom = nom;
		this.cognom = cognom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCognom() {
		return cognom;
	}

	public void setCognom(String cognom) {
		this.cognom = cognom;
	}

	public int getId_jugador() {
		return id_jugador;
	}

	public Set<Monstre> getMonstres() {
		return monstres;
	}

	public void setMonstres(Set<Monstre> monstres) {
		this.monstres = monstres;
	}

	@Override
	public String toString() {
		return "Jugador [id_jugador=" + id_jugador + ", nom=" + nom + "]";
	}

}
