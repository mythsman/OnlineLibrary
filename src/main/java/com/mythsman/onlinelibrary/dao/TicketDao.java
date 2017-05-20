package com.mythsman.onlinelibrary.dao;

import com.mythsman.onlinelibrary.model.Ticket;
import com.mythsman.onlinelibrary.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by myths on 5/15/17.
 */
@Mapper
@Service
public interface TicketDao {
    @Select({"insert into ticket(uid,ticket,expire) values(#{uid},#{ticket},#{expire})"})
    public void insert(
            @Param("uid") int uid,
            @Param("ticket") String ticket,
            @Param("expire") Date expire
    );
    @Select({"select * from ticket where ticket=#{ticket}"})
    public Ticket selectByTicket(@Param("ticket") String ticket);
}
