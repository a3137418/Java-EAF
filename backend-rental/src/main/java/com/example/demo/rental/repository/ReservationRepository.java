package com.example.demo.rental.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.rental.model.entity.Reservation;
import com.example.demo.rental.model.enums.ReservationStatus;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findByUserIdOrderByCreatedAtDesc(Long userId);
	List<Reservation> findAllByOrderByCreatedAtDesc();
	
	@Query("""
		    SELECT COUNT(r) > 0 FROM Reservation r
		    WHERE r.itemId = :itemId
		        AND r.status IN :statuses
		        AND r.startTime < :newEndTime
		        AND r.endTime > :newStartTime
		    """)
	boolean existsOverlap(
	    @Param("itemId") Long itemId, 
	    @Param("statuses") Collection<ReservationStatus> statuses,
	    @Param("newEndTime") LocalDateTime newEndTime,
	    @Param("newStartTime") LocalDateTime newStartTime
	);
	
	/*
	boolean existsByItemIdAndStatusInStartTimeLessThanAndEndTimeGreaterThan(
			Long itemId, 
			Collection<ReservationStatus> statuses, 
			LocalDateTime newEndTime,
			LocalDateTime startEndTime);
	*/		
}