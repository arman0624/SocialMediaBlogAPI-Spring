package com.example.repository;

import com.example.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;


public interface MessageRepository extends JpaRepository<Message, Integer>{
    
    void deleteById(int id);

    @Query("SELECT m FROM Message m WHERE m.postedBy = :accountId")
    List<Message> getMessagesByAccountId(@Param("accountId") int accountId);

    @Query("SELECT m FROM Message m")
    List<Message> getMessages();

    Message findMessageByMessageId(Integer messageId);
}
