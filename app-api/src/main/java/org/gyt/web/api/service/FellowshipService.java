package org.gyt.web.api.service;

import org.gyt.web.model.Fellowship;

import java.util.List;

/**
 * 团契API，提供以下接口
 * - 查询所有团契
 * - 查询指定团契
 * - 创建团契
 * - 激活、禁用团契
 * - 设置团契所有者
 * - 添加团契管理员
 * - 移除团契管理员
 * - 获取指定用户所有者团契
 * - 获取指定用户管理员团契
 * Created by y27chen on 2016/9/18.
 */
public interface FellowshipService {

    /**
     * 获取所有团契对象信息
     *
     * @return 如果没有团契存在，测返回空列表
     */
    List<Fellowship> getAll();

    /**
     * 获取指定团契对象信息
     *
     * @param name 团契名称
     * @return 如果团契不存在返回null
     */
    Fellowship get(String name);

    /**
     * 创建团契
     *
     * @param name        团契名称
     * @param displayName 团契中文名称
     * @return 如果团契不存在，并且创建成功时返回true，否则返回false
     */
    boolean create(String name, String displayName);

    /**
     * 激活团契
     *
     * @param name 激活团契名称
     * @return 如果团契存在，并且状态被修改为激活则返回true，否则返回false
     */
    boolean enable(String name);

    /**
     * 判断团契是否为激活状态
     *
     * @param name 团契名称
     * @return 如果团契存在并且状态为激活返回true，否则返回false
     */
    boolean isEnabled(String name);

    /**
     * 禁用团契，如果团契被禁用，则不能访问该团契和团契相应的文章
     *
     * @param name 团契名称
     * @return 如果团契存在，并且状态被修改为禁用则返回true，否则返回false
     */
    boolean disable(String name);

    /**
     * 设置团契所有者
     *
     * @param name     团契名称
     * @param username 所有者用户名
     * @return 如果团契存在，并且用户名存在，且具有相应权限，则返回true，否则返回false
     */
    boolean setOwner(String name, String username);

    /**
     * 添加团契管理员
     *
     * @param name     团契名称
     * @param username 管理员用户名
     * @return 如果团契存在，并且用户名存在，且具有相应权限，则返回true，否则返回false
     */
    boolean addAdmin(String name, String username);

    /**
     * 移除团契管理员
     *
     * @param name     团契名称
     * @param username 管理员用户名
     * @return 如果团契存在，并且用户名存在，则返回true，否则返回false
     */
    boolean removeAdmin(String name, String username);

    /**
     * 获取指定用户有用所有者的团契
     *
     * @param username 用户名
     * @return 如果该用户没有团契所有者则返回空列表
     */
    List<Fellowship> getUserOwnerFellowship(String username);

    /**
     * 获取指定用户有管理员的团契
     *
     * @param username 用户名
     * @return 如果该用户没有团契管理员则返回空列表
     */
    List<Fellowship> getUserAdminFellowship(String username);
}
