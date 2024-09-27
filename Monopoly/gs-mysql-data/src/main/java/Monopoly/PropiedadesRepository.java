package Monopoly;

import org.springframework.data.repository.CrudRepository;

public interface PropiedadesRepository  extends CrudRepository<Propiedades, Integer>{
	Jugadores ComprovarPropietario(int idP);
	boolean Comprar(int idJ, int idP);
	int Edificar(int idJ, int idP);
	boolean Hipotecar(int idJ, int idP);
	boolean Deshipotecar(int idJ, int idP);
}
