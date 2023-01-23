package com.game.entity;

// ЭТОТ КЛАСС СОЗДАЛ Я


import org.hibernate.annotations.BatchSize;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Date;

@Entity // @Entity, указывает, что это сущность JPA.
@Table(name = "player") // или @Table(name = "Player") ?
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // с GenerationType.AUTO не проходит CreatePlayerTest/createPlayerTest
//    @Column(name = "id")
    private Long id;
//    @Column(name = "name")
    private String name;
//    @Column(name = "title")
    private String title;
//    @Column(name = "race")
    @Enumerated(EnumType.STRING) private Race race;
//    @Column(name = "profession")
    @Enumerated(EnumType.STRING) private Profession profession;
//    @Column(name = "experience")
    private Integer experience;
//    @Column(name = "level")
    private Integer level;
//    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;
//    @Column(name = "birthday")
    private Date birthday; // возможно, надо изменить на Long
//    @Column(name = "banned")
    private Boolean banned;

    public Player() {

    }

//    public Player(String name, String title, Race race, Profession profession) {
//        this.name = name;
//        this.title = title;
//        this.race = race;
//        this.profession = profession;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 13) this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.length() < 31) this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        if (0 <= experience && experience <= 10_000_000) this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    // возможно, надо изменить на Long
    public Date getBirthday() {
        return birthday;
    }

    // Диапазон значений года 2000..3000 включительно
    // возможно, надо изменить на Long
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                ", birthday=" + birthday +
                ", banned=" + banned +
                '}';
    }
}