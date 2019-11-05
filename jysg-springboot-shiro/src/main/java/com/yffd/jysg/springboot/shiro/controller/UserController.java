package com.yffd.jysg.springboot.shiro.controller;

import com.yffd.jysg.springboot.shiro.mapper.RoleMapper;
import com.yffd.jysg.springboot.shiro.mapper.UserMapper;
import com.yffd.jysg.springboot.shiro.realm.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userInfo")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    /**
     * 创建固定写死的用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public String login(Model model) {
        System.out.println("创建用户成功");
        return "创建用户成功";

    }

    /**
     * 删除固定写死的用户
     *
     * @param model
     * @return
     */
    @RequiresPermissions("userInfo:del")
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ResponseBody
    public String del(Model model) {
        System.out.println("删除用户名为用户成功");
        return "删除用户名为用户成功";

    }

    @RequiresPermissions("userInfo:view")
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    public String view(Model model) {
        System.out.println("这是用户列表页");
        userMapper.findByUserName("admin");
        return "这是用户列表页";

    }


    /**
     * 给admin用户添加 userInfo:del 权限
     * @param model
     * @return
     */
    @RequestMapping(value = "/addPermission",method = RequestMethod.GET)
    @ResponseBody
    public String addPermission(Model model) {
        LOG.info(">>>>添加权限，同时清除缓存");
        //在sys_role_permission 表中  将 删除的权限 关联到admin用户所在的角色
        //roleMapper.addPermission(1,3);

        //添加成功之后 清除缓存
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
        ShiroRealm shiroRealm = (ShiroRealm) securityManager.getRealms().iterator().next();
        //清除权限 相关的缓存
        shiroRealm.clearAllCache();
        return "给admin用户添加 userInfo:del 权限成功";

    }

    /**
     * 删除admin用户 userInfo:del 权限
     * @param model
     * @return
     */
    @RequestMapping(value = "/delPermission",method = RequestMethod.GET)
    @ResponseBody
    public String delPermission(Model model) {
        LOG.info(">>>>删除权限，同时清除缓存");
        //在sys_role_permission 表中  将 删除的权限 关联到admin用户所在的角色
//        roleMapper.delPermission(1,3);
        //添加成功之后 清除缓存
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        ShiroRealm shiroRealm = (ShiroRealm) securityManager.getRealms().iterator().next();
        //清除权限 相关的缓存
        shiroRealm.clearAllCache();

        return "删除admin用户userInfo:del 权限成功";

    }

    @RequiresPermissions("userInfo:clearCache")
    @RequestMapping(value = "/clearCache",method = RequestMethod.GET)
    @ResponseBody
    public String clearCache(String username) {

        String[] keys = new String[3];
        keys[0] = "shiro:cache:authenticationCache:"+username;
        keys[1] = "shiro:cache:authorizationCache:"+username;
        keys[2] = "shiro:cache:retrylimit:"+username;

        //原子性 命令 删除多个key
        redisTemplate.delete(CollectionUtils.arrayToList(keys));

        return "删除"+username+"权限成功";

    }

}