package com.lgh.sys.manage.model;


public class Menuinfo {
    private String menuId;

    private String menuName;

    private String parentId;

    private String requestUrl;

    private String menuIcon;

    private String state;

    private String insertTime;

    private String orderId;

    /**
     * 菜单图片地址
     */
    private String menuImgPath;

    /**
     * 是否为父节点
     */
    private String isparent;

    /**
     * 菜单等级
     */
    private String level;

    /**
     * @return menu_id
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    /**
     * @return menu_name
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * @return parent_id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * @return request_url
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * @param requestUrl
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    /**
     * @return menu_icon
     */
    public String getMenuIcon() {
        return menuIcon;
    }

    /**
     * @param menuIcon
     */
    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
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
     * @return insert_time
     */
    public String getInsertTime() {
        return insertTime;
    }

    /**
     * @param insertTime
     */
    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime == null ? null : insertTime.trim();
    }

    /**
     * @return order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取菜单图片地址
     *
     * @return menu_img_path - 菜单图片地址
     */
    public String getMenuImgPath() {
        return menuImgPath;
    }

    /**
     * 设置菜单图片地址
     *
     * @param menuImgPath 菜单图片地址
     */
    public void setMenuImgPath(String menuImgPath) {
        this.menuImgPath = menuImgPath == null ? null : menuImgPath.trim();
    }

    /**
     * 获取是否为父节点
     *
     * @return isparent - 是否为父节点
     */
    public String getIsparent() {
        return isparent;
    }

    /**
     * 设置是否为父节点
     *
     * @param isparent 是否为父节点
     */
    public void setIsparent(String isparent) {
        this.isparent = isparent == null ? null : isparent.trim();
    }

    /**
     * 获取菜单等级
     *
     * @return level - 菜单等级
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置菜单等级
     *
     * @param level 菜单等级
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }
}