package com.example.source.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserDTO {

    private Integer id;

    private String username;

    private String userPhone;

    private String userEmail;

    private String realName;

    private Byte sex;

    private Integer age;

    private String profession;

    private String address;

    private String identity;

    private Byte userStatus;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;


}
