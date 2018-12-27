package cn.gribe.modules.sys.shiro;

import cn.gribe.modules.sys.entity.SysUserEntity;
import cn.gribe.modules.sys.service.SysRoleService;
import cn.gribe.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Zhugw on 2018/12/23 0023.
 */
@Component
public class RuleTag {

    @Autowired
    SysUserService userService;

    /**
     * 是否拥有该权限
     * @return   true：是     false：否
     */
    public boolean hasBusiness() {
        SysUserEntity user = ShiroUtils.getUserEntity();
        user = userService.queryByRoleNameAndUserId("商家",user.getUserId());
        if(user != null){
            return false;
        }
        return true;
    }

}
