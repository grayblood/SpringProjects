package kingoftokio.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kingoftokio.Model.Jugador;
import kingoftokio.Model.Monstre;
import kingoftokio.Model.Partida;
import kingoftokio.Services.JugadorService;
import kingoftokio.Services.MonstreService;
import kingoftokio.Services.PartidaService;
import kingoftokio.Utils.MonstresNames;

@Controller
public class MainController {

	@Autowired
	JugadorService jugadorService;

	@Autowired
	MonstreService monstreService;

	@Autowired
	PartidaService partidaService;

	@GetMapping(path = "/") // Map ONLY GET Requests
	public @ResponseBody String welcome() {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		return "Spring es un infierno, pero no tanto como PHP y Javascript";
	}

	// -----------------
	// Partida
	// ----------------
	@GetMapping(path = "/Partida/Crear")
	public @ResponseBody Partida Crear(@RequestParam int p) {
		return createGame(p);
	}

	@GetMapping(path = "/Partida/FindAll")
	public @ResponseBody List<Partida> findAllPartida() {

		return partidaService.findAll();
	}

	@GetMapping(path = "/Partida/FindbyId")
	public @ResponseBody Partida findbyid(@RequestParam int idp) {
		return partidaService.findById(idp);
	}

	@GetMapping(path = "/Partida/roll")
	public @ResponseBody String roll() {
		return partidaService.roll().toString();
	}

	@GetMapping(path = "/Partida/Solveroll")
	public @ResponseBody String Solveroll(@RequestParam int idj, @RequestParam int idp) {
		return partidaService.SolveRoll(monstreService, idj, idp).toString();
	}

	@GetMapping(path = "/Partida/assignarTorn")
	public @ResponseBody int assignarTorn(@RequestParam int idp, @RequestParam int torn, @RequestParam int maxp) {
		return partidaService.assignarTorn(idp, torn, maxp);
	}

	// -----------------
	// Monstre
	// ----------------
	@GetMapping(path = "/Monstre/FindAll")
	public @ResponseBody String findAllMonstre() {
		List<Monstre> a = monstreService.findAll();
		return a.toString();
	}

	@GetMapping(path = "/Monstre/ComprarCarta")
	public @ResponseBody String ComprarCarta(@RequestParam int idc, @RequestParam int idm) {
		return monstreService.ComprarCarta(idc, idm);
	}

	@GetMapping(path = "/Monstre/ListMostresVius")
	public @ResponseBody String Listmostres(@RequestParam int idp) {
		return monstreService.ListMostresVius(idp).toString();
	}

	@GetMapping(path = "/Monstre/MonstresViusContrincants")
	public @ResponseBody List<Monstre> MonstresViusContrincants(@RequestParam int idp, @RequestParam int idplayer) {
		return monstreService.ListMonstresViusContrincants(idp, idplayer);
	}

	@GetMapping(path = "/Monstre/FindByidPartida")
	public @ResponseBody List<Monstre> findpartida(@RequestParam int idp) {
		return monstreService.findbyPartidaID(idp);
	}

	@GetMapping(path = "/Monstre/LlistarMonstreJugador")
	public @ResponseBody String LlistarMonstreJugador(@RequestParam int idj) {
		return monstreService.findById(idj).toString();
	}

	@GetMapping(path = "/Monstre/HiHaMonstreTokio")
	public @ResponseBody String HiHaMonstreTokio(@RequestParam int idp) {
		return "Hi ha un monstre a tokio en la partida Id " + idp + " ? " + monstreService.HiHaMonstreTokio(idp);
	}

	@GetMapping(path = "/Monstre/GetMonstreTokio")
	public @ResponseBody String GetMonstreTokio(@RequestParam int idp) {
		return monstreService.GetMonstreTokio(idp).toString();
	}

	@GetMapping(path = "/Monstre/SetMonstreRandom")
	public @ResponseBody String MonstreRandom(@RequestParam int idp) {
		Monstre m = monstreService.SetMonstreTokioAleatori(idp);
		return "El monstre = " + m.getNom() + " del jugador =" + m.getId_Jugador() + " ara es el Monstre a Tokio";
	}

	@GetMapping(path = "/Monstre/MonstreMaxPuntVictoria")
	public @ResponseBody Monstre MonstreMaxPuntVictoria(@RequestParam int idp) {
		return monstreService.MonstreMaxPuntVictoria(idp);
	}

	@GetMapping(path = "/Monstre/MonstreViu")
	public @ResponseBody Monstre MonstreViu(@RequestParam int idp) {
		return monstreService.MonstreViu(idp);
	}

	@GetMapping(path = "/Monstre/CountMostresVius")
	public @ResponseBody int CountMostresVius(@RequestParam int idp) {
		return monstreService.CountMostresVius(idp);
	}

	@GetMapping(path = "/Monstre/ActualitzarMonstresVius")
	public @ResponseBody Boolean ActualitzarMonstresVius(@RequestParam int idp) {
		return monstreService.ActualitzarMonstresVius(idp);
	}

	@GetMapping(path = "/Monstre/PoderLLiure")
	public @ResponseBody List<Monstre> poderLLiure(@RequestParam int idp) {
		return monstreService.ListMonstrePoderLliure(idp);
	}

	/*
	 * @GetMapping(path = "/Monstre/PoderLLiure") public @ResponseBody List<Monstre>
	 * poderLLiure(@RequestParam int idp) { return
	 * monstreService.ListMonstrePoderLliure(idp); }
	 */

	// -----------------
	// Jugador
	// ----------------

	@GetMapping(path = "/Jugador/FindAll")
	public @ResponseBody List<Jugador> findAllJugador() {
		return jugadorService.findAll();
	}

	// -----------------
	// Generar Partida
	// -----------------

	@GetMapping(path = "/Partida/NuevaPartida")
	public String StartSimulationAuto() {
		List<Jugador> jugadors = new ArrayList<Jugador>();
		int players = 0;
		int inputplayer = 0;
		System.out.println("quants jugadors n'hi hauran?");
		Random rand = new Random();
		inputplayer = rand.nextInt((4 - 2) + 1) + 2;
		for (int i = 0; i < inputplayer; i++) {
			Jugador jug = createplayersauto();
			jugadors.add(jug);
			players++;
		}
		Partida partida = createGame(players);
		createMonsterPlayersAuto(inputplayer, jugadors, partida);
		// crea Monstruos de poder
		monstreService.createPowerMonsters(partida);
		System.out.println("Game is starting");
		StartGameAuto(jugadors, partida.getId_Partida());
		List<Monstre> a = monstreService.findbyPartidaID(partida.getId_Partida());
		System.out.println(a);
		return a.toString();

	}

	public void StartGameAuto(List<Jugador> jug, Integer partida) {
		int i = 1; // reutilizar para el orden
		// randomizar el orden
		Collections.shuffle(jug);
		// Enseï¿½ar posiciones
		for (Jugador jugador : jug) {
			System.out.println("posicion " + i + " : " + jugador.getNom());
			i++;
		}

		Boolean end = false;
		i = 0;
		while (!end) {
			Boolean mort = monstreService.ActualitzarMonstresVius(partida);
			if (mort) {
				jug.remove(i);
				i++;
			}
			if (i >= jug.size()) {
				i = 0;
			}
			System.out.println("Torn: " + i);
			i = partidaService.assignarTorn(partida, i, jug.size());
			System.out.println("-------------------------------");
			System.out.println("Jugador: " + jug.get(i));
			System.out.println("-------------------------------");
			System.out.println("-------------------------------");
			System.out.println(monstreService.ListMostresVius(partida));
			System.out.println("-------------------------------");
			monstreService.ActualitzarMonstresVius(partida);
			// tokio?
			if (!monstreService.HiHaMonstreTokio(partida)) {
				Monstre monstre = monstreService.MonstreJugador(jug.get(i).getId_jugador(), partida);
				monstre.setOn_Tokio(true);
				monstreService.Update(monstre);
			}
			// resolir daus
			partidaService.SolveRoll(monstreService, jug.get(i).getId_jugador(), partida);
			System.out.println("Resolve");
			monstreService.ActualitzarMonstresVius(partida);
			if (monstreService.MonstreJugador(jug.get(i).getId_jugador(), partida) != null) {
				if (monstreService.MonstreJugador(jug.get(i).getId_jugador(), partida).getPoderasociat() == null) {
					ComprarCartaPoderAuto(jug.get(i).getId_jugador(), partida);
				} else {
					Random rand = new Random();
					Boolean res = rand.nextBoolean();

					System.out.println("Quieres Usar La carta de poder: "
							+ monstreService.MonstreJugador(jug.get(i).getId_jugador(), partida).getPoderasociat()
							+ " ?");
					if (res) {
						System.out.println("No se ha utilizado");

					} else {
						System.out.println("Se ha utilizado");
						monstreService.SolvePowerCarts(jug.get(i).getId_jugador(), partida);

					}
				}
			}
			monstreService.ActualitzarMonstresVius(partida);
			monstreService.CountMostresVius(partida);
			end = monstreService.Reassign(partida);
			i++;
			System.out.println("Vius = " + monstreService.MonstreViu(partida));
			if (monstreService.MonstreViu(partida) != null) {
				end = true;
			}
		}

		System.out.println("Todo Salio a pedir de Milhouse");
	}

	public void ComprarCartaPoderAuto(Integer jug, Integer partida) {
		Boolean end = false;
		int i = 0;

		// Lista monstre Poder
		List<Monstre> poder = monstreService.ListMonstrePoderLliure(partida);
		System.out.println("POSA EL NUMERO DE LA OPCIÃ“ DESITJADA");
		System.out.println("-------------------------------");
		System.out.println(monstreService.MonstreJugador(jug, partida).getEnergia());
		for (Monstre monstre : poder) {
			System.out.println(i + " - " + poder.get(i).getNom() + " " + poder.get(i).getEnergia() + " Energia");
			i++;
		}
		System.out.println("5 Cerrar tienda");

		Random rand = new Random();
		for (Monstre monstre : poder) {
			if (monstreService.MonstreJugador(jug, partida).getEnergia() >= monstre.getEnergia()) {
				monstreService.ComprarCarta(monstre.getId_Monstre(), jug);
			}
		}
	}

	Partida createGame(int players) {
		System.out.println("Creant Partida");
		Partida partida00 = new Partida(players);
		partidaService.Insert(partida00);
		return partida00;
	}

	Jugador createplayersauto() {
		List<String> names = new ArrayList<String>();
		names.add("Bob");
		names.add("Jill");
		names.add("Tom");
		names.add("Brandon");
		names.add("Bob2");
		names.add("Jill2");
		names.add("Tom2");
		names.add("Brandon2");
		names.add("Tom3");
		names.add("Brandon3");
		int randnum;
		System.out.println("Quin es el nom del jugador?");
		Random rand = new Random();
		randnum = rand.nextInt(((names.size() - 1) - 0) + 1) + 0;
		String inputplayer1 = names.get(randnum);
		names.remove(randnum);
		System.out.println("y el cognom?");
		randnum = rand.nextInt(((names.size() - 1) - 0) + 1) + 0;
		String inputplayer2 = names.get(randnum);
		names.remove(randnum);
		Jugador jug = new Jugador(inputplayer1, inputplayer2);
		jugadorService.Insert(jug);
		return jug;
	}

	void createMonsterPlayersAuto(int inputplayer, List<Jugador> jug, Partida partida) {
		List<MonstresNames> names = new ArrayList<MonstresNames>();
		names.add(MonstresNames.King);
		names.add(MonstresNames.MekaDracron);
		names.add(MonstresNames.Ciberkitty);
		names.add(MonstresNames.Gigazaur);
		names.add(MonstresNames.Space_Penguin);
		names.add(MonstresNames.Alienoid);

		int number = 0;
		for (int i = 0; i < inputplayer; i++) {
			Random rand = new Random();
			number = rand.nextInt((names.size() - 1) + 1) + 0;
			System.out.println("Names size =" + names.size());
			System.out.println("Number =" + number);
			Monstre m = createplayerMonsters(number);
			names.remove(number);
			monstreService.Insert(m);
			m.setId_Jugador(jug.get(i));
			m.setId_Partida(partida);
			monstreService.Update(m);
			System.out.println("Created Monster = " + m);
		}
	}

	Monstre createplayerMonsters(int monName) {
		Monstre M;
		switch (monName) {
		case 0:
			M = new Monstre(MonstresNames.King);
			return M;
		case 1:
			M = new Monstre(MonstresNames.MekaDracron);
			return M;
		case 2:
			M = new Monstre(MonstresNames.Ciberkitty);
			return M;
		case 3:
			M = new Monstre(MonstresNames.Gigazaur);
			return M;
		case 4:
			M = new Monstre(MonstresNames.Space_Penguin);
			return M;
		case 5:
			M = new Monstre(MonstresNames.Alienoid);
			return M;

		default:
			System.out.println("Error");
			break;
		}

		return null;
	}
}
