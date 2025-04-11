package com.example.mapper;

import com.example.entity.Order;
import com.example.entity.Picture;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PowerMapper {
    @Select("SELECT * FROM picture")
    List<Picture> findByTypeOne();

    @Select("SELECT * FROM picture WHERE flag = '1' AND `from` = #{from} AND `to` = #{to}")
    List<Picture> findByTypeTwo(String from, String to);

    @Select("SELECT * FROM picture WHERE username = #{username}")
    List<Picture> findByUsername(String from, String to, String username);

    @Select("SELECT * FROM picture WHERE pid = #{pid}")
    Picture findByPid(String pid);

    @Update("update picture set username = #{username} where pid = #{pid}")//表示被申请
    int updateOwner(String pid,String username);

    @Update("update picture set if_apply = '1' where pid = #{pid}")//表示被申请
    int restFlag1(String pid);

    @Insert("INSERT INTO picture (pid, if_apply, if_agree) VALUES (#{pid}, #{if_apply}, #{if_agree})")
    int creatPicture(String pid,String if_apply, String if_agree);

    @Insert("insert into `order` (`from`, `to`, `work_name`, `receiptID`, `recordId`, `time`, `status`) VALUES (#{from}, #{to}, #{imagebase}, #{receiptID}, #{recordId}, #{time}, #{status})")
    Boolean creatOrder(String from, String to, String imagebase, String receiptID, String recordId, String time, String status);

    @Insert("update `order` set `status`= #{status} where recordId = #{recordId}")
    Boolean changeOrder(String recordId, String status);

    @Insert("update picture set `from` = #{from} where pid = #{pid}")
    Boolean insertfrom(String from,String pid);

    @Select("SELECT DISTINCT pid FROM picture")
    List<String> findUrl();

    @Select("SELECT DISTINCT pid FROM picture WHERE username = #{username}")
    List<String> findUrlByUsername(String username);

    @Select("SELECT * FROM `order` WHERE `from` = #{username}")
    List<Order> findOrderByUsername(String username);
    @Select("SELECT * FROM picture WHERE if_apply = '1' AND (`from` = #{user} OR `to` = #{user})")
    List<Picture> findMessage(String user);
    @Select("SELECT DISTINCT pid FROM picture WHERE `from` = #{user} OR `to` = #{user}")
    List<String> findUrl1(String user);

}
