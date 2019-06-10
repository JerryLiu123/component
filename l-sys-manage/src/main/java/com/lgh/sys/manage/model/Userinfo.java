package com.lgh.sys.manage.model;

import java.util.Date;

public class Userinfo {
    private String id;

    private Date createTime;

    private String creatorId;

    private Integer errorNum;

    private String loginName;

    private String password;

    private String remark;

    private String state;

    private Date updateTime;

    private String updaterId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
     * @return error_num
     */
    public Integer getErrorNum() {
        return errorNum;
    }

    /**
     * @param errorNum
     */
    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    /**
     * @return login_name
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
}