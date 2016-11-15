package org.gyt.web.api.service;

import org.gyt.web.model.Authority;
import org.gyt.web.model.Role;

import java.util.List;
import java.util.Set;

/**
 * 角色API，提供以下接口
 * - 获取所有角色
 * - 获取指定角色
 * - 创建角色
 * - 添加权限到角色
 * - 移除权限到角色
 * - 禁用、激活角色
 * <p>
 * ====== 用户相关接口
 * <p>
 * - 获得用户拥有角色
 * - 为用户添加角色
 * - 为用户删除角色
 * Created by Administrator on 2016/9/16.
 */
public interface RoleService {

    /**
     * 获取所有角色对象信息
     *
     * @return 如果没有角色则返回空列表
     */
    List<Role> get();

    /**
     * 根据角色名字获取角色对象信息
     *
     * @param name 角色名字
     * @return 对应角色对象信息，如果为不存在则返回null
     */
    Role get(String name);

    /**
     * 创建角色，默认没有任何权限
     *
     * @param name 角色唯一的名字
     * @return 当且仅当角色不存在并且创建成功时返回true，否则返回false
     */
    boolean create(String name);

    /**
     * 创建角色，默认没有任何权限
     *
     * @param name        角色唯一名字
     * @param displayName 角色显示名字
     * @return 当且仅当角色不存在并且创建成功时返回true，否则返回false
     */
    boolean create(String name, String displayName);

    /**
     * 添加权限到角色
     *
     * @param name      角色名字
     * @param authority 权限
     * @return 角色存在，并且权限不存在，并且添加成功时返回true，否则返回false
     */
    boolean add(String name, Authority authority);

    /**
     * 移除角色权限
     *
     * @param name      角色名字
     * @param authority 权限
     * @return 角色存在，并且权限在角色里面存在，并且成功移除以后返回true，否则返回false
     */
    boolean remove(String name, Authority authority);

    /**
     * 激活角色
     *
     * @param name 激活角色
     * @return 如果角色存在，并且状态被修改为激活则返回true，否则返回false
     */
    boolean enable(String name);

    /**
     * 判断角色是否已经被激活
     *
     * @param name 激活角色
     * @return 如果角色存在，并且已经激活则返回true，否则返回false
     */
    boolean isEnable(String name);

    /**
     * 禁用角色，如果角色被禁用，则不能该角色所有的权限都不能使用
     *
     * @param name 禁用角色
     * @return 如果角色存在，并且状态被修改为禁用则返回true，否则返回false
     */
    boolean disable(String name);

    /**
     * 获得用户角色对象信息
     *
     * @param username 用户名
     * @return 如果用户不存在，则返回空列表
     */
    Set<Role> getFromUser(String username);

    /**
     * 为用户添加角色
     *
     * @param username 用户名
     * @param roleName 角色名字
     * @return 当用户存在，并且角色存在，并且角色在用户中不存在时返回true，否则返回false
     */
    boolean addToUser(String username, String roleName);

    /**
     * 为用户移除角色
     *
     * @param username 用户名
     * @param roleName 角色名字
     * @return 当用户存在，并且角色存在，并且角色在用户中存在时返回true，否则返回false
     */
    boolean removeFromUser(String username, String roleName);
}
