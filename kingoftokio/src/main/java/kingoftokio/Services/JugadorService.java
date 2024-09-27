package kingoftokio.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kingoftokio.Model.Jugador;
import kingoftokio.Model.Monstre;
import kingoftokio.Model.Partida;
import kingoftokio.Repositoris.JugadorRepository;

@Service
public class JugadorService {
	@Autowired
	JugadorRepository jugadorRepository;

	public Jugador findById(Integer id) {
		return jugadorRepository.findById(id).orElse(null);
	}

	public List<Jugador> findAll() {
		return jugadorRepository.findAll();
	}

	public void delete(Integer id) {

		jugadorRepository.deleteById(id);
	}

	public Jugador Insert(Jugador p) {
		return jugadorRepository.save(p);
	}

	public Jugador Update(Jugador p) {
		return jugadorRepository.save(p);
	}

}
