package kingoftokio.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kingoftokio.Model.Jugador;
import kingoftokio.Model.Monstre;
import kingoftokio.Model.Partida;
import kingoftokio.Repositoris.PartidaRepository;
import kingoftokio.Utils.MonstresNames;

@Service
public class PartidaService {

	@Autowired
	public PartidaRepository PartidaRepository;

	// MonstreRepository monstreRepository;

	public Partida findById(Integer id) {
		return PartidaRepository.findById(id).orElse(null);
	}

	public List<Partida> findAll() {

		return PartidaRepository.findAll();
	}

	public void delete(Integer id) {

		PartidaRepository.deleteById(id);
	}

	public Partida Insert(Partida p) {
		return PartidaRepository.save(p);
	}

	public Partida Update(Partida p) {
		return PartidaRepository.save(p);
	}

	public int assignarTorn(int partida, int torn, int maxplayers) {
		Partida par = findById(partida);
		if (torn >= maxplayers) {
			torn = 0;
		}
		par.setTorn(torn);
		Update(par);
		return torn;
	}

	public List<Integer> roll() {
		List<Integer> rolls = new ArrayList<Integer>();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			int r = random.nextInt(6 + 1 - 1) + 1;
			rolls.add(r);
		}
		System.out.println(rolls);
		return rolls;
	}

	public List<Integer> SolveRoll(MonstreService mDao, Integer jugador, Integer partida) {
		List<Integer> roll = roll();
		int uno = 0;
		int dos = 0;
		int tres = 0;
		int cuatro = 0;
		int cinco = 0;
		int seis = 0;
		for (Integer result : roll) {
			switch (result) {
			case 1:
				uno++;
				break;
			case 2:
				dos++;
				break;
			case 3:
				tres++;
				break;
			case 4:
				cuatro++;
				break;
			case 5:
				cinco++;
				break;
			case 6:
				seis++;
				break;
			default:
				System.out.println("ERROOOOOOOOOOOOOR");
			}
		}
		Monstre monstre = mDao.MonstreJugador(jugador, partida);
		System.out.println(monstre);
		if (monstre != null) {
			monstre.setEnergia(monstre.getEnergia() + cuatro);
			// Assignar vida si no esta en tokio
			if (!monstre.getOn_Tokio()) {
				int vida = monstre.getVida() + seis;
				if (vida <= 50) {
					monstre.setVida(vida);
				} else if (vida > 50) {
					monstre.setVida(50);
				}

				if (cinco != 0) {
					if (mDao.HiHaMonstreTokio(partida)) {
						Monstre ontoki = mDao.GetMonstreTokio(partida);
						ontoki.setVida(mDao.GetMonstreTokio(partida).getVida() - cinco);
						System.out.println("MAking damage to " + ontoki.getNom() + " with " + cinco);
						mDao.Update(ontoki);
						Random rand = new Random();
						Boolean res = rand.nextBoolean();
						if (res) {
							ontoki.setOn_Tokio(false);
							mDao.Update(ontoki);
							monstre.setOn_Tokio(true);
						}
					}

				}

				mDao.Update(monstre);
			} else if (monstre.getOn_Tokio()) {
				for (Monstre m : mDao.ListMostresVius(partida)) {
					if (m.getId_Jugador().getId_jugador() != jugador) {
						m.setVida(m.getVida() - cinco);
						mDao.Update(m);
					}

				}
			}

			// Assignar punts de victoria
			uno -= 3;
			dos -= 3;
			tres -= 3;
			if (uno >= 0) {
				monstre.setPunts_Victoria(monstre.getPunts_Victoria() + uno + 1);
			}
			if (dos >= 0) {
				monstre.setPunts_Victoria(monstre.getPunts_Victoria() + dos + 2);
			}
			if (dos >= 0) {
				monstre.setPunts_Victoria(monstre.getPunts_Victoria() + tres + 3);
			}
			mDao.Update(monstre);
		}
		return roll;
	}

	public void ChangeTorn(Integer id, Integer torn) {
		Partida partida = findById(id);
		partida.setTorn(torn);
		Update(partida);
	}

}
