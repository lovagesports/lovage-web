package com.lovage.sports.web;

import org.springframework.beans.factory.annotation.Autowired;
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
	private PlayersService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Player getPlayerById(@PathVariable("id") int id) {

		Player player = null;// = service.getPlayers().stream().filter(f -> f.getId() ==
								// id).findFirst().orElse(null);
		return player;

	}
}
