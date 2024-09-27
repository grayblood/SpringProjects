package kingoftokio.Model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "Partida")
public class Partida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonBackReference
	private int id_Partida;

	@Column(name = "torn")
	private int torn;

	@Column(name = "quantitat_Jugadors")
	private int Jugadors;

	public Partida(int numJ) {
		this.torn = 0;
		this.Jugadors = numJ;
	}

	public Partida() {
		super();
	}

	@Override
	public String toString() {
		return "Partida [id_Partida=" + id_Partida + ", torn=" + torn + ", Jugadors=" + Jugadors + "]";
	}

	public int getId_Partida() {
		return id_Partida;
	}

	public int getTorn() {
		return torn;
	}

	public void setTorn(int torn) {
		this.torn = torn;
	}

	public int getJugadors() {
		return Jugadors;
	}

	public void setJugadors(int jugadors) {
		Jugadors = jugadors;
	}

}
