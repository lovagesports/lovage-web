package com.lovage.sports.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lovage.sports.domain.Player;
import com.lovage.sports.service.PlayersService;

@RestController
@RequestMapping("/players")
public class PlayersController {

	@Autowired
	private PlayersService playerService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Player> getFields() {

		return playerService.getAllPlayers();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Player> getPlayerById(@PathVariable("id") String id) {

		Player player = playerService.getPlayer(id);
		if (player == null) {
			return new ResponseEntity<Player>(player, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Player>(player, HttpStatus.FOUND);
		}
	}
}
