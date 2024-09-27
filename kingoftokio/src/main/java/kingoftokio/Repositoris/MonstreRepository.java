package kingoftokio.Repositoris;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kingoftokio.Model.Monstre;
import kingoftokio.Model.Partida;

@Repository
public interface MonstreRepository extends JpaRepository<kingoftokio.Model.Monstre, Integer> {

//	public List<Monstre> findbyPartidaID(int idpartida);
//
//	public void delete(Integer id);
//
//	public Monstre Insert(Monstre p);
//
//	public Monstre Update(Monstre p);
//
//	public void createPowerMonsters(Partida partida);
//
//	public boolean HiHaMonstreTokio(Integer id);
//
//	public Monstre GetMonstreTokio(Integer partida);
//
//	public Monstre SetMonstreTokioAleatori(Integer id);
//
//	public List<Monstre> ListMostresVius(Integer id);
//
//	public List<Monstre> ListMonstresViusContrincants(Monstre m, Integer id);
//
//	public void SetMonstreTokioAleatori(Partida partida);
//
//	public Boolean ActualitzarMonstresVius(Integer id);
//
//	public Monstre MonstreJugador(Integer jugador, Integer partida);
//
//	public List<Monstre> ListMonstrePoderLliure(Integer partida);
//
//	public void SolvePowerCarts(Monstre jug, Integer partida);
//
//	public int CountMostresVius(Integer partida);
//
//	public Boolean Reassign(Integer partida);
//
//	public Monstre MonstreViu(Integer partida);
//
//	public boolean ComprarCarta(Monstre carta, Monstre comprador);
//
//	public Monstre MonstreMaxPuntVictoria(Partida partida);

}