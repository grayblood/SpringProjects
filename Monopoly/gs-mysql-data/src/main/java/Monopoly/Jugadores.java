package Monopoly;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Jugadores")
public class Jugadores {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_jugador")
	private int id_jugador;

	@Column(name = "Nombre")
	private String nom;

	@Column(name = "Bancarota")
	private boolean Bancarrota;

	@Column(name = "Turn")
	private int turn;

	@Column(name = "dinero")
	private int dinero;
	@Column(name = "NumPropiedades")
	private int numPropiedades;

	@Column(name = "Victorias")
	private int Wins;

	@Column(name = "CasillaActual")
	private int casilla;
	// OneToMany. Aqui diem que volem el set (i per tant la taula), i quina variable
	// ens far referencia, es a dir, quina variable representa la clau foranea.
	// la variable s'ha de dir com la variable de JAVA, no com la columna de SQL

	@OneToMany(mappedBy = "id_propiedad")
	private Set<Propiedades> propiedad;

	// propiedad ferrocarril

	public Jugadores() {
	}
	
	public Jugadores(String nom, boolean vivo, int orden, int numPropiedades, int dinero, int wins,int casilla,
			Set<Propiedades> propiedad) {
		super();
		this.nom = nom;
		this.Bancarrota = vivo;
		this.turn = orden;
		this.numPropiedades = numPropiedades;
		this.dinero = dinero;
		this.Wins = wins;
		this.casilla = casilla;
		this.propiedad = propiedad;
	}

	public int getId() {
		return id_jugador;
	}

	public void setId(int id) {
		this.id_jugador = id;
	}

	public String getnom() {
		return nom;
	}

	public void setnom(String nom) {
		this.nom = nom;
	}

	public Set<Propiedades> getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(Set<Propiedades> propiedad) {
		this.propiedad = propiedad;
	}

	public boolean isBancarrota() {
		return Bancarrota;
	}

	public void setBancarrota(boolean vivo) {
		this.Bancarrota = vivo;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int Turn) {
		this.turn = Turn;
	}

	public int getNumPropiedades() {
		return numPropiedades;
	}

	public void setNumPropiedades(int numPropiedades) {
		this.numPropiedades = numPropiedades;
	}

	public int getDinero() {
		return dinero;
	}

	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	public int getWins() {
		return Wins;
	}

	public void setWins(int wins) {
		Wins = wins;
	}

		public int getCasilla() {
		return casilla;
	}

	public void setCasilla(int casilla) {
		this.casilla = casilla;
	}


	public Set<Propiedades> getPropiedades() {
		return propiedad;
	}

	public void setPropiedades(Set<Propiedades> propiedad) {
		this.propiedad = propiedad;
	}

	@Override
	public String toString() {
		return "Jugador [id=" + id_jugador + ", nom=" + nom + ", Bancarrota=" + Bancarrota + ", Torn=" + turn
				+ ", numPropiedades=" + numPropiedades + ", dinero=" + dinero + ", Wins=" + Wins + ", propiedad="
				+ propiedad + "]";
	}

}
