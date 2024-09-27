package kingoftokio.Repositoris;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kingoftokio.Model.Jugador;
import kingoftokio.Services.MonstreService;

public interface PartidaRepository extends JpaRepository<kingoftokio.Model.Partida,Integer> {

}