package com.example.mapper;

import com.example.entity.Account;
import com.example.entity.AccountUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{text} or email = #{text}")
    Account findAccountByNameOrEmail(String text);
    @Select("select * from user where username = #{text} or email = #{text}")
    AccountUser findAccountUserByNameOrEmail(String text);
    @Insert("insert into user (email,username,password,publicKey,markcode) values (#{email}, #{username}, #{password}, #{publicKey}, #{markcode})")
    int creatAccount(String username,String password,String email,String publicKey,String markcode);
    @Update("update user set password = #{password} where email = #{email})")
    int restPasswordByEmail(String password,String email);

    @Insert("insert into product (id,name,cname,price) values (#{id}, #{name}, #{cname}, #{price})")
    int addBuyCar(String id,String name,String cname,String price);

    @Update("UPDATE picture SET `to` = #{to},username = #{username},work_name = #{work_name},theme = #{theme},time = #{time},pid_marked = #{sb_marked} WHERE pid = #{sb}")
   int addOrder(String to,String sb,String username, String work_name, String theme, String time, String sb_marked);
    @Update("UPDATE picture SET if_agree = '1' WHERE pid = #{pid}")
    int Reset1(String pid);
    @Update("UPDATE picture SET if_agree = '2' WHERE pid = #{pid}")
    int Reset2(String pid);
}
