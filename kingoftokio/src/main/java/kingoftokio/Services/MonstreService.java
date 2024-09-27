package kingoftokio.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kingoftokio.Model.Jugador;
import kingoftokio.Model.Monstre;
import kingoftokio.Model.Partida;
import kingoftokio.Repositoris.MonstreRepository;
import kingoftokio.Utils.MonstresNames;

@Service
public class MonstreService {

	@Autowired
	MonstreRepository monstreRepository;

	static Random rand;

	public Monstre findById(Integer id) {
		return monstreRepository.findById(id).orElse(null);
	}

	public List<Monstre> findbyPartidaID(int idpartida) {
		List<Monstre> a = monstreRepository.findAll();
		List<Monstre> b = new ArrayList<Monstre>();
		for (Monstre monstre : a) {
			if (monstre.getId_Partida().getId_Partida() == idpartida) {
				b.add(monstre);
			}
		}
		return b;
	}

	public List<Monstre> findAll() {
		List<Monstre> a = monstreRepository.findAll();
		return a;
	}

	public void delete(Integer id) {

		monstreRepository.deleteById(id);
	}

	public Monstre Insert(Monstre p) {
		return monstreRepository.save(p);
	}

	public Monstre Update(Monstre p) {
		return monstreRepository.save(p);
	}

	public void createPowerMonsters(Partida partida) {
		Monstre AlientoFlamigero = new Monstre(MonstresNames.AlientoFlamigero);
		Monstre Mimetismo = new Monstre(MonstresNames.Mimetismo);
		Monstre MonstruoConRayoReductor = new Monstre(MonstresNames.MonstruoConRayoReductor);
		Monstre MonstruoEscupidosDeVeneno = new Monstre(MonstresNames.MonstruoEscupidosDeVeneno);
		Insert(AlientoFlamigero);
		Insert(Mimetismo);
		Insert(MonstruoConRayoReductor);
		Insert(MonstruoEscupidosDeVeneno);
		AlientoFlamigero.setEnergia(3);
		Mimetismo.setEnergia(8);
		MonstruoConRayoReductor.setEnergia(6);
		MonstruoEscupidosDeVeneno.setEnergia(4);

		AlientoFlamigero.setId_Partida(partida);
		Mimetismo.setId_Partida(partida);
		MonstruoConRayoReductor.setId_Partida(partida);
		MonstruoEscupidosDeVeneno.setId_Partida(partida);

		Update(AlientoFlamigero);
		Update(Mimetismo);
		Update(MonstruoConRayoReductor);
		Update(MonstruoEscupidosDeVeneno);
	}

	public boolean HiHaMonstreTokio(Integer id) {
		List<Monstre> monstres = findAll();
		System.out.println(monstres);
		for (Monstre m : monstres) {
			if (m.getOn_Tokio() && m.getId_Partida().getId_Partida() == id) {
				return true;
			}
		}
		return false;
	}

	public Monstre GetMonstreTokio(Integer partida) {
		if (HiHaMonstreTokio(partida)) {
			List<Monstre> monstres = ListMostresVius(partida);
			for (Monstre m : monstres) {
				if (m.getOn_Tokio() && !m.getEliminat() && m.getId_Jugador() != null
						&& m.getId_Partida().getId_Partida() == partida)
					return m;
			}
		}
		return null;
	}

	public Monstre SetMonstreTokioAleatori(Integer id) {
		List<Monstre> monstres = findAll();
		List<Monstre> filtered = new ArrayList<Monstre>();
		for (Monstre monstre : monstres) {
			if (monstre.getId_Jugador() != null && monstre.getId_Partida().getId_Partida() == id) {
				filtered.add(monstre);
			}
		}
		rand = new Random();
		int randomNum = rand.nextInt((filtered.size() - 0) + 1);
		if (!HiHaMonstreTokio(id)) {
			Monstre m = filtered.get(randomNum);
			m.setOn_Tokio(true);
			Update(m);
			return m;
		} else {
			Monstre n = GetMonstreTokio(id);
			n.setOn_Tokio(false);
			Monstre m = filtered.get(randomNum);
			m.setOn_Tokio(true);
			Update(n);
			Update(m);
			return m;
		}
	}

	public List<Monstre> ListMostresVius(Integer id) {
		List<Monstre> monstres = findAll();
		List<Monstre> monstVius = new ArrayList<Monstre>();
		for (Monstre m : monstres) {
			if (!m.getEliminat() && m.getId_Jugador() != null && m.getId_Partida().getId_Partida() == id) {
				monstVius.add(m);
			}
		}
		return monstVius;
	}

	// Retorna una llista els monstres vius contrincants al que li toca el torn de
	// la partida.
	public List<Monstre> ListMonstresViusContrincants(Integer pid, Integer id) {
		List<Monstre> listaVivos = ListMostresVius(id);
		System.out.println(listaVivos);
		for (Monstre monstre : listaVivos) {
			if (monstre.getId_Jugador().getId_jugador() == pid) {
				listaVivos.remove(listaVivos.indexOf(monstre));
				return listaVivos;
			}
		}
		System.out.println("No me encuentro");
		return listaVivos;
	}

	public void SetMonstreTokioAleatori(Partida partida) {
		List<Monstre> monstres = findAll();
		List<Monstre> mon = new ArrayList<Monstre>();
		for (Monstre monstre : monstres) {
			if (monstre.getId_Jugador() == null && monstre.getId_Partida() != partida) {
				mon.add(monstre);
			}
		}
		Random rand = new Random();
		int randomNum = rand.nextInt(((monstres.size() - 1) - 0) + 1) + 0;
		if (!HiHaMonstreTokio(partida.getId_Partida())) {
			Monstre m = monstres.get(randomNum);
			m.setOn_Tokio(true);
			Update(m);
			System.out.println("Monstre random asignat" + m.getNom());
		} else {
			Monstre m = GetMonstreTokio(partida.getId_Partida());
			m.setOn_Tokio(false);
			Update(m);
			m = monstres.get(randomNum);
			m.setOn_Tokio(true);
			Update(m);
			System.out.println("Monstre random asignat" + m.getNom());
		}
	}

	public Boolean ActualitzarMonstresVius(Integer id) {
		List<Monstre> listaVivos = ListMostresVius(id);
		for (Monstre m : listaVivos) {
			if (m.getVida() <= 0 && !m.getEliminat()) {
				m.setOn_Tokio(false);
				m.setEliminat(true);
				if (m.getPoderasociat() != null) {
					m.getPoderasociat().setPoderasociat(null);
					m.setPoderasociat(null);
				}
				System.out.println("El monstre " + m.getNom() + " Esta mort");
				Update(m);
				return true;
			}
		}
		return false;
	}

	public Monstre MonstreJugador(Integer jugador, Integer partida) {
		List<Monstre> monstres = ListMostresVius(partida);
		for (Monstre m : monstres) {
			if (m.getId_Jugador().getId_jugador() == jugador) {
				System.out.println("Monstre Jugador " + m);
				return m;
			}
		}
		System.out.println("No lo encontre");
		return null;
	}

	public List<Monstre> ListMonstrePoderLliure(Integer partida) {
		List<Monstre> listaVivos = findAll();
		List<Monstre> listadisponibles = new ArrayList<Monstre>();
		for (int i = 0; i < listaVivos.size(); i++) {
			if (listaVivos.get(i).getPoderasociat() == null && listaVivos.get(i).getId_Jugador() == null) {
				listadisponibles.add(listaVivos.get(i));
			}
		}
		return listadisponibles;
	}

	public String SolvePowerCarts(Integer idj, Integer partida) {
		Monstre monst = MonstreJugador(idj, partida);
		List<Monstre> contrincants = ListMonstresViusContrincants(idj, partida);
		int idTarget;
		Monstre target;
		Monstre aux;
		Random rand = new Random();
		System.out.println(" MonstreName: " + monst.getNom() + "Poder Asociat" + monst.getPoderasociat().getNom());
		if (monst.getPoderasociat().getNom().equals(MonstresNames.AlientoFlamigero.toString())) {
			System.out.println("utilitzant: " + MonstresNames.AlientoFlamigero.toString());
			for (Monstre monstre : contrincants) {
				monstre.setVida(monstre.getVida() - 1);
				Update(monstre);
			}
		} else if (monst.getPoderasociat().getNom().equals(MonstresNames.Mimetismo.toString())) {
			// Intercanvia les vides i els punts del jugador que vulguis per les teves.

			System.out.println("utilitzant: " + MonstresNames.Mimetismo.toString());
			// random
			idTarget = rand.nextInt(((contrincants.size() - 1) - 0) + 1) + 0;
			target = contrincants.get(idTarget);

			aux = monst;
			monst.setPunts_Victoria(target.getPunts_Victoria());
			monst.setVida(target.getVida());
			target.setPunts_Victoria(aux.getPunts_Victoria());
			target.setVida(aux.getVida());

		} else if (monst.getPoderasociat().getNom().equals(MonstresNames.MonstruoConRayoReductor.toString())) {
			// Al monstre que tu decideixis, li treus una vida.
			System.out.println("utilitzant: " + MonstresNames.MonstruoConRayoReductor.toString());
			idTarget = rand.nextInt(((contrincants.size() - 1) - 0) + 1) + 0;
			target = contrincants.get(idTarget);
			target.setVida(target.getVida() - 1);

		} else if (monst.getPoderasociat().getNom().equals(MonstresNames.MonstruoEscupidosDeVeneno.toString())) {
			System.out.println("utilitzant: " + MonstresNames.MonstruoEscupidosDeVeneno.toString());
			// Al monstre que tu decideixis li treu un punt de victoria.
			idTarget = rand.nextInt(((contrincants.size() - 1) - 0) + 1) + 0;
			target = contrincants.get(idTarget);
			if (target.getPunts_Victoria() != 0) {
				target.setPunts_Victoria(target.getPunts_Victoria() - 1);
			}
		} else {
			System.out.println("Kaboom");
			return "No hi havia carta";
		}
		Monstre asociat = monst.getPoderasociat();
		monst.setPoderasociat(null);
		asociat.setPoderasociat(null);
		Update(asociat);
		Update(monst);
		return "Carta utilitzada";
	}

	public int CountMostresVius(Integer partida) {
		List<Monstre> listaVivos = ListMostresVius(partida);
		System.out.println("Hi ha " + listaVivos.size() + " monstres vius");
		return listaVivos.size();
	}

	public Boolean Reassign(Integer partida) {
		List<Monstre> listaVivos = ListMostresVius(partida);

		for (int i = 0; i < listaVivos.size(); i++) {
			if (listaVivos.get(i).getPunts_Victoria() > 20) {
				ActualitzarMonstresVius(partida);
				System.out.println(listaVivos.get(i).getId_Jugador().toString() + " HA GUANYAT");
				System.out.println("PARTIDA ACABA");
				return true;
			} else {
				ActualitzarMonstresVius(partida);
				return false;
			}
		}
		return null;
	}

	public Monstre MonstreViu(Integer partida) {

		List<Monstre> listaVivos = ListMostresVius(partida);
		if (listaVivos.size() == 1) {
			System.out.println("El Guanyador es: " + listaVivos.get(0));
			return listaVivos.get(0);

		} else {
			System.out.println("Hi ha mes d'un monstre viu retorno null");
			return null;
		}

	}

	public String ComprarCarta(Integer idcarta, Integer idm) {
		List<Monstre> all = findAll();
		Monstre comprador = null;
		Monstre carta = null;
		for (Monstre monstre : all) {
			if (monstre != null) {
				if (monstre.getId_Monstre() == idm) {
					comprador = monstre;
				}
				if (monstre.getId_Monstre() == idcarta && monstre.getId_Jugador() == null) {
					carta = monstre;
				}
			}
		}
		if (comprador != null || carta != null) {
			if (comprador.getEnergia() >= carta.getEnergia()) {
				comprador.setEnergia(comprador.getEnergia() - carta.getEnergia());
				comprador.setPoderasociat(carta);
				carta.setPoderasociat(comprador);
				Update(comprador);
				Update(carta);
				System.out.println("Carta Comprada");
				return "Carta " + carta.getNom() + " Comprada";
			}

			return "No hi ha suficient energia";
		} else {
			return "No se ha encontrado una Monstruo disponible para esa id";
		}
	}

	public Monstre MonstreMaxPuntVictoria(int partida) {
		List<Monstre> listaVivos = ListMostresVius(partida);
		List<Monstre> mfinal = new ArrayList<Monstre>();
		List<Integer> puntos = new ArrayList<Integer>();

		for (int i = 0; i < listaVivos.size() - 1; i++) {
			if (puntos.size() < 1) {
				puntos.add(listaVivos.get(i).getPunts_Victoria());
				mfinal.add(listaVivos.get(i));
			} else if (puntos.size() >= 1 && listaVivos.get(i).getPunts_Victoria() > puntos.get(0)) {
				puntos.remove(0);
				puntos.add(listaVivos.get(i).getPunts_Victoria());
				mfinal.remove(0);
				mfinal.add(listaVivos.get(i));
			}

		}

		return mfinal.get(0);

	}

}
