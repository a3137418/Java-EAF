package com.example.demo.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.book.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer>{
	@Modifying
	@Query(value = "delete from publisher_storybook where publisher_id = :publisher_id and storybook_id = :storybook_id", nativeQuery = true )
	void deleteStoryBookFromPublisher(@Param("publisher_id") Integer publisherId, @Param("storybook_id") Integer storybookId);
}
