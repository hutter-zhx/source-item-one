package com.example.source.item.entity;

import java.util.Date;

public class UserInfo {
    private Integer id;

    private Integer uid;

    private String realName;

    private Byte sex;

    private Integer age;

    private String profession;

    private String address;

    private String identity;

    private Date createTime;

    private Date updateTime;

    private Integer modifierId;

    private String modifierName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getModifierId() {
        return modifierId;
    }

    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName == null ? null : modifierName.trim();
    }

    @Override
    public String toString() {
        return "UserInfo{" + "id=" + id + ", uid=" + uid + ", realName='" + realName + '\'' + ", sex=" + sex + ", age=" + age + ", profession='" + profession + '\'' + ", address='" + address + '\'' + ", identity='" + identity + '\'' + ", createTime=" + createTime + ", updateTime=" + updateTime + ", modifierId=" + modifierId + ", modifierName='" + modifierName + '\'' + '}';
    }
}
