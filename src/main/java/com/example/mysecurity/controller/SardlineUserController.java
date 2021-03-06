package com.example.mysecurity.controller;

import com.example.mysecurity.common.Result;
import com.example.mysecurity.entity.SardlineRole;
import com.example.mysecurity.entity.SardlineUser;
import com.example.mysecurity.entity.SardlineUserRole;
import com.example.mysecurity.entity.base.PageParm;
import com.example.mysecurity.entity.req.SardineUserReq;
import com.example.mysecurity.entity.req.UserReq;
import com.example.mysecurity.entity.so.UserListSo;
import com.example.mysecurity.service.SardlineUserOrgService;
import com.example.mysecurity.service.SardlineUserRoleService;
import com.example.mysecurity.service.SardlineUserService;
import com.example.mysecurity.vo.UserVo;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * (SardlineUser)表控制层
 *
 * @author fjx
 * @since 2020-10-10 10:40:57
 */
@RestController
@RequestMapping("user")
public class SardlineUserController {


    Logger logger = LoggerFactory.getLogger(SardlineUserController.class);
    /**
     * 服务对象
     */
    @Resource
    private SardlineUserService sardlineUserService;

    @Resource
    private SardlineUserRoleService sardlineUserRoleService;

    @Resource
    private SardlineUserOrgService sardlineUserOrgService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SardlineUser selectOne(String id) {
        return this.sardlineUserService.queryById(id);
    }


    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("register")
    public Result register(@Validated UserReq user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("有報錯信息");
        }
        return sardlineUserService.register(user);
    }


    /**
     * 用户修改
     */
    @PostMapping("update")
    public Result<Boolean> update(@Validated SardlineUser user) {
        return Result.success(sardlineUserService.update(user) == null ? false : true);
    }


    @PostMapping("queryUser")
    public Result<UserVo> queryUserForLogin(String username) {

        if (sardlineUserService.queryUserForLogin(username) != null) {
            return Result.success(sardlineUserService.queryUserForLogin(username));
        } else {
            return Result.noData();
        }


    }

    /**
     * 用户列表
     */
    @PostMapping("list")
    public Result<PageInfo<UserListSo>> UserList(@Validated PageParm pageParm, @Validated SardlineUser sardlineUser) {

        return Result.success(this.sardlineUserService.queryAll(pageParm, sardlineUser));

    }

    /**
     * 删除用户
     */
    @PostMapping("deleteUser")
    public Result<Boolean> deleteUser(String userId) {

        return Result.success(this.sardlineUserService.delete(userId));
    }


    /**
     * 分配角色
     */
    @PostMapping("setRoles")
    public Result<Boolean> setRoles(@RequestParam("userId") String userId, @RequestParam("roleIds") String roleIds) {

        return Result.success(this.sardlineUserRoleService.setRoles(userId, roleIds));


    }

    /**
     * 分配角色
     */
    @PostMapping("setOrgs")
    public Result<Boolean> setOrgs(@RequestParam("userId") String userId, @RequestParam("orgIds") String orgIds) {

        return Result.success(this.sardlineUserOrgService.setOrgs(userId, orgIds));
    }

    /**
     * 分配角色
     */
    @PostMapping("updatePassword")
    public Result<Boolean> updatePassword(SardineUserReq req) {

        return Result.success(this.sardlineUserService.updatePassword(req));
    }


}