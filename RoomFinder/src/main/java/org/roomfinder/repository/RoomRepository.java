package org.roomfinder.repository;

import org.roomfinder.model.Room;
import org.roomfinder.model.RoomID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, RoomID> { }
