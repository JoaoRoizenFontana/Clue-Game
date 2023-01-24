package model;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;


public class CartasTest {
	Cartas []testCartas;
	Cartas []cartas_jogo;
	int num_arma;
	int num_suspeito;
	int num_comodo;
	
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
		Random gerador = new Random();
		num_arma = gerador.nextInt(6);
		num_suspeito = 6 + gerador.nextInt(6);
		num_comodo = 12 + gerador.nextInt(9);
		testCartas = new Cartas[] 
				{
						new Cartas(cartas_jogo[num_arma].setTipo(), cartas_jogo[num_arma].setNome()),
						new Cartas(cartas_jogo[num_suspeito].setTipo(), cartas_jogo[num_suspeito].setNome()),
						new Cartas(cartas_jogo[num_comodo].setTipo(), cartas_jogo[num_comodo].setNome()),
				};
	}
	@Test
	public void envelopeTest() {
		assertSame("arma",testCartas[0].getTipo());
		assertSame("suspeito",testCartas[1].getTipo());
		assertSame("comodo",testCartas[2].getTipo());
		assertSame(cartas_jogo[num_arma].getNome(),testCartas[0].getNome());
		assertSame(cartas_jogo[num_suspeito].getNome(),testCartas[1].getNome());
		assertSame(cartas_jogo[num_comodo].getNome(),testCartas[2].getNome());
	}

}