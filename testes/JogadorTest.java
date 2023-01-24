package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class JogadoresTest {
	Jogador testJogadores[];
	Cartas cartas_jogo[];
	
	
	@Before
	public void setUp() {
		cartas_jogo = new Cartas[] 
				{
					new Cartas("arma", "Corda"),
					new Cartas("arma", "Cano de Chumbo"),
					new Cartas("arma", "Faca"),
					new Cartas("arma", "Chave Inglesa"),
					new Cartas("arma", "Castiçal"),
					new Cartas("arma", "Revolver"),
					new Cartas("suspeito", "Srta. Scarlet"),
					new Cartas("suspeito", "Coronel Mustard"),
					new Cartas("suspeito", "Professor Plum"),
					new Cartas("suspeito", "Reverendo Green"),
					new Cartas("suspeito", "Sra. White"),
					new Cartas("suspeito", "Sra. Peacock"),
					new Cartas("comodo", "Escritorio"),
					new Cartas("comodo", "Entrada"),
					new Cartas("comodo", "Sala de estar"),
					new Cartas("comodo", "Biblioteca"),
					new Cartas("comodo", "Salao de jogos"),
					new Cartas("comodo", "Sala de jantar"),
					new Cartas("comodo", "Jardim de inverno"),
					new Cartas("comodo", "Sala de musica"),
					new Cartas("comodo", "Cozinha"),
			};
		
		testJogadores = new Jogador[]
			{
				new Jogador("Srta. Scarlet"),	
				new Jogador("Coronel Mustard"),
				new Jogador("Professor Plum"),
				new Jogador("Reverendo Green"),
				new Jogador("Sra. White"),
				new Jogador("Sra. Peacock"),
			};
		testJogadores[0].distribuiCartas(cartas_jogo[0]);
	}
	
	@Test
	public void cartasjogadorestest() {
		assertSame("Srta. Scarlet", testJogadores[0].nome());
		assertSame(cartas_jogo[0].Tipo(), testJogadores[0].cartas_jogador[0].Tipo());
		assertSame(cartas_jogo[0].Nome(), testJogadores[0].cartas_jogador[0].Nome());
	}

}