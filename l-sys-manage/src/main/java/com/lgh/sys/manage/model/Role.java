package com.lgh.sys.manage.model;

import java.util.Date;

public class Role {
    private String roleId;

    private Date createTime;

    private String creatorId;

    private Integer flag;

    private String remark;

    private String roleDescription;

    private Integer roleIndex;

    private String roleName;

    private Integer showUsers;

    private Date updateTime;

    private String updaterId;

    private String sysFlag;

    /**
     * @return role_id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return creator_id
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    /**
     * @return flag
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * @param flag
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return role_description
     */
    public String getRoleDescription() {
        return roleDescription;
    }

    /**
     * @param roleDescription
     */
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription == null ? null : roleDescription.trim();
    }

    /**
     * @return role_index
     */
    public Integer getRoleIndex() {
        return roleIndex;
    }

    /**
     * @param roleIndex
     */
    public void setRoleIndex(Integer roleIndex) {
        this.roleIndex = roleIndex;
    }

    /**
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * @return show_users
     */
    public Integer getShowUsers() {
        return showUsers;
    }

    /**
     * @param showUsers
     */
    public void setShowUsers(Integer showUsers) {
        this.showUsers = showUsers;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return updater_id
     */
    public String getUpdaterId() {
        return updaterId;
    }

    /**
     * @param updaterId
     */
    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId == null ? null : updaterId.trim();
    }

    /**
     * 获取1为登录后跳后台用户
     *
     * @return sys_flag - 1为登录后跳后台用户
     */
    public String getSysFlag() {
        return sysFlag;
    }

    /**
     * 设置1为登录后跳后台用户
     *
     * @param sysFlag 1为登录后跳后台用户
     */
    public void setSysFlag(String sysFlag) {
        this.sysFlag = sysFlag == null ? null : sysFlag.trim();
    }
}