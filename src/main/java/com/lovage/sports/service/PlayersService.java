package com.lovage.sports.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lovage.sports.domain.Player;

@Service
public class PlayersService {

	public List<Player> getPlayers() {

		Player player1 = new Player();
		// player1.setId(21);
		player1.setFirstName("Tudor");
		player1.setLastName("Chirila");

		Player player2 = new Player();
		// player2.setId(22);
		player2.setFirstName("Kazimir");
		player2.setLastName("Obrenovic");

		Player player3 = new Player();
		// player3.setId(23);
		player3.setFirstName("Laur");
		player3.setLastName("Marat");

		return Arrays.asList(player1, player2, player3);
	}
}
