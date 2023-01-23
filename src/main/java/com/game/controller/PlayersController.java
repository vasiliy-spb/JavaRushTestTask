package com.game.controller;

// ЭТОТ КЛАСС СОЗДАЛ Я

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.service.PlayerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
@RestController
@RequestMapping("/rest")
public class PlayersController {
    private final PlayerService playerService;

    @Autowired
    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Get players list (by params) | GET
    @GetMapping("/players")
    public ResponseEntity<List<Player>> getPlayersList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "3") Integer pageSize
            /* Params */) {

        PlayerValidator playerValidator = new PlayerValidator(name,title,race,profession,after,before,banned,minExperience,maxExperience,minLevel,maxLevel);

        List<Player> playersList = playerService.getPlayersList(playerValidator,order,pageNumber,pageSize);

        return new ResponseEntity<>(playersList,HttpStatus.OK);
    }

    // Get players count (by params) | GET
    @GetMapping("/players/count")
    public ResponseEntity<Integer> getPlayersCount(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            Model model) {

        PlayerValidator playerValidator = new PlayerValidator(name,title,race,profession,after,before,banned,minExperience,maxExperience,minLevel,maxLevel);

        Integer validCount = playerService.getPlayersCount(playerValidator);

        if (validCount == null) return new ResponseEntity<>(validCount, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(validCount, HttpStatus.OK);
    }

    // Create player | POST
    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(
            @RequestBody Player player) {

        Player newPlayer = playerService.createPlayer(player);

        if (newPlayer == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(newPlayer, HttpStatus.OK);
    }

    // Get player (by id) | GET
    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long id) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
        Player gotPlayer = null;
        gotPlayer = playerService.getPlayer(id);
        if (gotPlayer == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 400 | getPlayerByIdNotExistTest
        return new ResponseEntity<>(gotPlayer,HttpStatus.OK);
    }

    // Update player (by id) | POST
    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(
            @PathVariable("id") Long id,
            @RequestBody Player player,
            BindingResult bindingResult) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (player.getExperience() != null && (player.getExperience() < 0 || player.getExperience() > 10_000_000)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (player.getBirthday() != null && player.getBirthday().getTime() < 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Player updatedPlayer = playerService.updatePlayer(id, player);
        if (updatedPlayer == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedPlayer,HttpStatus.OK);
    }

    // Delete player (by id) | DELETE
    @DeleteMapping("/players/{id}")
    public ResponseEntity deletePlayer(@PathVariable("id") Long id) {
        if (id <= 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        boolean successDelete = playerService.deletePlayer(id);

        if (successDelete) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}