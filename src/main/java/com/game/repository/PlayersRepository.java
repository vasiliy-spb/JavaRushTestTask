package com.game.repository;

// ЭТОТ КЛАСС СОЗДАЛ Я

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayersRepository extends JpaRepository<Player, Long> {

}


/*

JpaRepository
    findAll () — получить список всех доступных объектов в базе данных
    findAll(…) – получить список всех доступных сущностей и отсортировать их по заданному условию.
    save(…) – сохранить объект Iterable . Здесь мы можем передать несколько объектов, чтобы сохранить их в пакете.
    flush() — сбросить все отложенные задачи в базу данных
    saveAndFlush(…) — сохранить сущность и немедленно сбросить изменения
    deleteInBatch(…) — удалить Iterable сущностей. Здесь мы можем передать несколько объектов, чтобы удалить их в пакетном режиме.

CrudRepository
    save(…) – сохранить объект Iterable . Здесь мы можем передать несколько объектов, чтобы сохранить их в пакете.
    findOne(…) — получить один объект на основе переданного значения первичного ключа
    findAll () — получить Iterable всех доступных объектов в базе данных
    count() — возвращает общее количество сущностей в таблице.
    delete(…) — удалить сущность на основе переданного объекта
    exists(…) - проверить, существует ли объект на основе переданного значения первичного ключа

 */