package com.zukxu.mybatis.demo1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author zukxu
 * @since 2021-12-02
 */
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 用户ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户账号
     */
      private String account;

      /**
     * 密码
     */
      private String password;

      /**
     * 用户昵称
     */
      private String nickName;

      /**
     * 用户类型（00系统用户）
     */
      private String userType;

      /**
     * 用户邮箱
     */
      private String email;

      /**
     * 头像地址
     */
      private String avatar;

      /**
     * 性别(字典 1男 2女 3未知)
     */
      private Integer sex;

      /**
     * 手机号码
     */
      private String phone;

      /**
     * 最后一次登录时间
     */
      private LocalDateTime lastLoginTime;

      /**
     * 最后一次登录IP
     */
      private String lastLoginIp;

      /**
     * 状态（字典 0正常 1冻结 2删除）
     */
      private Integer status;

      /**
     * 创建者
     */
      private String createBy;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;

      /**
     * 更新者
     */
      private String updateBy;

      /**
     * 更新时间
     */
      private LocalDateTime updateTime;

      /**
     * 备注
     */
      private String remark;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getAccount() {
        return account;
    }

      public void setAccount(String account) {
          this.account = account;
      }
    
    public String getPassword() {
        return password;
    }

      public void setPassword(String password) {
          this.password = password;
      }
    
    public String getNickName() {
        return nickName;
    }

      public void setNickName(String nickName) {
          this.nickName = nickName;
      }
    
    public String getUserType() {
        return userType;
    }

      public void setUserType(String userType) {
          this.userType = userType;
      }
    
    public String getEmail() {
        return email;
    }

      public void setEmail(String email) {
          this.email = email;
      }
    
    public String getAvatar() {
        return avatar;
    }

      public void setAvatar(String avatar) {
          this.avatar = avatar;
      }
    
    public Integer getSex() {
        return sex;
    }

      public void setSex(Integer sex) {
          this.sex = sex;
      }
    
    public String getPhone() {
        return phone;
    }

      public void setPhone(String phone) {
          this.phone = phone;
      }
    
    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

      public void setLastLoginTime(LocalDateTime lastLoginTime) {
          this.lastLoginTime = lastLoginTime;
      }
    
    public String getLastLoginIp() {
        return lastLoginIp;
    }

      public void setLastLoginIp(String lastLoginIp) {
          this.lastLoginIp = lastLoginIp;
      }
    
    public Integer getStatus() {
        return status;
    }

      public void setStatus(Integer status) {
          this.status = status;
      }
    
    public String getCreateBy() {
        return createBy;
    }

      public void setCreateBy(String createBy) {
          this.createBy = createBy;
      }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }
    
    public String getUpdateBy() {
        return updateBy;
    }

      public void setUpdateBy(String updateBy) {
          this.updateBy = updateBy;
      }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

      public void setUpdateTime(LocalDateTime updateTime) {
          this.updateTime = updateTime;
      }
    
    public String getRemark() {
        return remark;
    }

      public void setRemark(String remark) {
          this.remark = remark;
      }

    @Override
    public String toString() {
        return "SysUser{" +
              "id=" + id +
                  ", account=" + account +
                  ", password=" + password +
                  ", nickName=" + nickName +
                  ", userType=" + userType +
                  ", email=" + email +
                  ", avatar=" + avatar +
                  ", sex=" + sex +
                  ", phone=" + phone +
                  ", lastLoginTime=" + lastLoginTime +
                  ", lastLoginIp=" + lastLoginIp +
                  ", status=" + status +
                  ", createBy=" + createBy +
                  ", createTime=" + createTime +
                  ", updateBy=" + updateBy +
                  ", updateTime=" + updateTime +
                  ", remark=" + remark +
              "}";
    }
}
