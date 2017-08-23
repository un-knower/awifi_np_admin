package com.awifi.np.admin.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NPAdminPermission implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1713593451062716430L;

	private Integer id;

    private String permissionName;

    private Integer parentId;

    private String url;

    private String method;

    private Byte isMenu;

    private Byte isShow;

    private String remark;

    private String menuIcon;

    private Integer listOrder;

    private List<NPAdminPermission> childPermission=new ArrayList<NPAdminPermission>();
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Byte getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Byte isMenu) {
        this.isMenu = isMenu;
    }

    public Byte getIsShow() {
        return isShow;
    }

    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getListOrder() {
        return listOrder;
    }

    public void setListOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }
	public void addSub(NPAdminPermission p) {
		for(NPAdminPermission s:this.childPermission){
			if(s.getId().intValue()==p.getId().intValue()){
				this.childPermission.remove(s);
				break;
			}
		}
		this.childPermission.add(p);
	}

	public List<NPAdminPermission> getChildPermission() {
		return childPermission;
	}

	public void setChildPermission(List<NPAdminPermission> childPermission) {
		this.childPermission = childPermission;
	}

	public void removeSub(NPAdminPermission permission) {
		this.childPermission.remove(permission);
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childPermission == null) ? 0 : childPermission.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isMenu == null) ? 0 : isMenu.hashCode());
		result = prime * result + ((isShow == null) ? 0 : isShow.hashCode());
		result = prime * result + ((listOrder == null) ? 0 : listOrder.hashCode());
		result = prime * result + ((menuIcon == null) ? 0 : menuIcon.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((permissionName == null) ? 0 : permissionName.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NPAdminPermission other = (NPAdminPermission) obj;
		if (childPermission == null) {
			if (other.childPermission != null)
				return false;
		} else if (!childPermission.equals(other.childPermission))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isMenu == null) {
			if (other.isMenu != null)
				return false;
		} else if (!isMenu.equals(other.isMenu))
			return false;
		if (isShow == null) {
			if (other.isShow != null)
				return false;
		} else if (!isShow.equals(other.isShow))
			return false;
		if (listOrder == null) {
			if (other.listOrder != null)
				return false;
		} else if (!listOrder.equals(other.listOrder))
			return false;
		if (menuIcon == null) {
			if (other.menuIcon != null)
				return false;
		} else if (!menuIcon.equals(other.menuIcon))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (permissionName == null) {
			if (other.permissionName != null)
				return false;
		} else if (!permissionName.equals(other.permissionName))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}