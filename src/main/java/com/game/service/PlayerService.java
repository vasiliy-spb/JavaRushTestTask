package com.game.service;

// ЭТОТ КЛАСС СОЗДАЛ Я

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.*;

//@Component
@Service
@Transactional(readOnly = true)
public class PlayerService { // в этом классе должна быть бизнес-логика

    private final PlayersRepository playersRepository;
    @Autowired
    public PlayerService(PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    // Get players list (by params) | GET
    public List<Player> getPlayersList(PlayerValidator playerValidator, PlayerOrder playerOrder, Integer pageNumber, Integer pageSize) {
        Sort.Order sortOrder = Sort.Order.by(playerOrder.getFieldName());
        List<Player> playersList = playersRepository.findAll(Sort.by(sortOrder));

        List<Player> playersList2 = new ArrayList<>();

        for (Player currentPlayer : playersList) {
            if (playerValidator.getName() != null && !currentPlayer.getName().contains(playerValidator.getName())) continue;
            if (playerValidator.getTitle() != null && !currentPlayer.getTitle().contains(playerValidator.getTitle())) continue;
            if (playerValidator.getRace() != null && !currentPlayer.getRace().equals(playerValidator.getRace())) continue;
            if (playerValidator.getProfession() != null && !currentPlayer.getProfession().equals(playerValidator.getProfession())) continue;
            if (playerValidator.getAfter() != null && currentPlayer.getBirthday().getTime() < playerValidator.getAfter()) continue;
            if (playerValidator.getBefore() != null && currentPlayer.getBirthday().getTime() > playerValidator.getBefore()) continue;
            if (playerValidator.getBanned() != null && !currentPlayer.getBanned().equals(playerValidator.getBanned())) continue;
            if (playerValidator.getMinExperience() != null && currentPlayer.getExperience() < playerValidator.getMinExperience()) continue;
            if (playerValidator.getMaxExperience() != null && currentPlayer.getExperience() > playerValidator.getMaxExperience()) continue;
            if (playerValidator.getMinLevel() != null && currentPlayer.getLevel() < playerValidator.getMinLevel()) continue;
            if (playerValidator.getMaxLevel() != null && currentPlayer.getLevel() > playerValidator.getMaxLevel()) continue;
            playersList2.add(currentPlayer);
        }

        if (playersList2.size() < pageNumber * pageSize + pageSize) return playersList2.subList(pageNumber * pageSize, playersList2.size());
        return playersList2.subList(pageNumber * pageSize, pageNumber * pageSize + pageSize);
    }

    // Get players count (by params) | GET
    public Integer getPlayersCount(PlayerValidator playerValidator) {

        List<Player> allPlayers = playersRepository.findAll();

        List<Player> allPlayers2 = new ArrayList<>();

        for (Player currentPlayer : allPlayers) {
            if (playerValidator.getName() != null && !currentPlayer.getName().contains(playerValidator.getName())) continue;
            if (playerValidator.getTitle() != null && !currentPlayer.getTitle().contains(playerValidator.getTitle())) continue;
            if (playerValidator.getRace() != null && !currentPlayer.getRace().equals(playerValidator.getRace())) continue;
            if (playerValidator.getProfession() != null && !currentPlayer.getProfession().equals(playerValidator.getProfession())) continue;
            if (playerValidator.getAfter() != null && currentPlayer.getBirthday().getTime() < playerValidator.getAfter()) continue;
            if (playerValidator.getBefore() != null && currentPlayer.getBirthday().getTime() > playerValidator.getBefore()) continue;
            if (playerValidator.getBanned() != null && !currentPlayer.getBanned().equals(playerValidator.getBanned())) continue;
            if (playerValidator.getMinExperience() != null && currentPlayer.getExperience() < playerValidator.getMinExperience()) continue;
            if (playerValidator.getMaxExperience() != null && currentPlayer.getExperience() > playerValidator.getMaxExperience()) continue;
            if (playerValidator.getMinLevel() != null && currentPlayer.getLevel() < playerValidator.getMinLevel()) continue;
            if (playerValidator.getMaxLevel() != null && currentPlayer.getLevel() > playerValidator.getMaxLevel()) continue;
            allPlayers2.add(currentPlayer);
        }

        return allPlayers2.size();
    }

    // Create player | POST
    @Transactional
    public Player createPlayer(Player player) {
        if (player.getName() == null || player.getTitle() == null || player.getRace() == null || player.getProfession() == null || player.getBirthday() == null || player.getExperience() == null) return null;
        if (player.getName().length() < 1 || player.getName().length() > 12) return null;
        if (player.getTitle().length() > 30) return null;
        if (player.getExperience() < 0 || player.getExperience() > 10_000_000) return null;
        if (player.getBirthday().getTime() < 0) return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(player.getBirthday());
        if (calendar.get(Calendar.YEAR) < 2000 || calendar.get(Calendar.YEAR) > 3000) return null;

        if (player.getBanned() == null) player.setBanned(false);

        player.setLevel((int) (Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience());

        return playersRepository.saveAndFlush(player);
    }

    // Get player (by id) | GET
    public Player getPlayer(Long id) /* throws IllegalArgumentException */{
        Optional<Player> foundPlayer =  playersRepository.findById(id);
        return foundPlayer.orElse(null);
    }

    // Update player (by id) | POST
    @Transactional
    public Player updatePlayer(Long id, Player updatedPlayer) {
        Player player = playersRepository.findById(id).orElse(null);
        if (player == null) return null;

        if (updatedPlayer.getName() != null) player.setName(updatedPlayer.getName());
        if (updatedPlayer.getTitle() != null) player.setTitle(updatedPlayer.getTitle());
        if (updatedPlayer.getRace() != null) player.setRace(updatedPlayer.getRace());
        if (updatedPlayer.getProfession() != null) player.setProfession(updatedPlayer.getProfession());
        if (updatedPlayer.getBirthday() != null) player.setBirthday(updatedPlayer.getBirthday());
        if (updatedPlayer.getBanned() != null) player.setBanned(updatedPlayer.getBanned());
        if (updatedPlayer.getExperience() != null) player.setExperience(updatedPlayer.getExperience());

        player.setLevel((int) (Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience());

        return player;
    }

    // Delete player (by id) | DELETE
    @Transactional
    public boolean deletePlayer(Long id) {
        Player p = playersRepository.findById(id).orElse(null);
        if (p == null) return false;
        playersRepository.delete(p);
        return true;
    }

}
