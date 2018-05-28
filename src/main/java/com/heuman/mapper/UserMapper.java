package com.heuman.mapper;

import com.heuman.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * Created by heuman on 2018/3/26.
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Select("select * from user where username=#{username}")
    User findByUsername(@Param("username") String username);

    @Select("select roleName from user_role ur left join user u on ur.userId=u.userId left join role r on ur.roleId=r.roleId where ur.userId=#{userId}")
    Set<String> findRolesByUserId(@Param("userId") String userId);
    
}
