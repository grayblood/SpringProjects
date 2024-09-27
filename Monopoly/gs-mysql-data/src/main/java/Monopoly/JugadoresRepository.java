package Monopoly;

import org.springframework.data.repository.CrudRepository;

import Monopoly.Jugadores;

public interface JugadoresRepository extends CrudRepository<Jugadores, Integer>  {
	boolean pagarLloguer(int idJ, int idP);
	void modificarDiners(int idJ, int i);
}
