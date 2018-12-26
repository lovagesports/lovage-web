package com.lovage.sports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lovage.sports.domain.Player;
import com.lovage.sports.repo.PlayerRepository;

@Service
public class PlayersService {

	@Autowired
	private PlayerRepository playerRepository;

	public List<Player> getAllPlayers() {

		return playerRepository.findAll();
	}

	public Player getPlayer(String id) {

		Player found = playerRepository.findById(id).orElse(null);
		return found;
	}

	// Player player1 = new Player();
	// // player1.setId(21);
	// player1.setFirstName("Tudor");
	// player1.setLastName("Chirila");
	//
	// Player player2 = new Player();
	// // player2.setId(22);
	// player2.setFirstName("Kazimir");
	// player2.setLastName("Obrenovic");
	//
	// Player player3 = new Player();
	// // player3.setId(23);
	// player3.setFirstName("Laur");
	// player3.setLastName("Marat");
}
