package org.gyt.web.api.service;

import org.gyt.web.model.User;

import java.util.List;

/**
 * 用户API，提供以下接口
 * - 创建用户（仅限注册用户时使用）
 * - 用户查询
 * - 用户批量查询
 * - 用户数量查询
 * - 用户信息修改
 * - 用户锁定、解锁
 * - 用户禁用、激活
 * Created by y27chen on 2016/9/14.
 */
public interface UserService {

    /**
     * 创建新用户，仅限注册用户时使用
     *
     * @param user 新用户
     * @return 如果用户不存在，并且用户信息检查成功时返回true，否则返回false
     */
    boolean create(User user);

    /**
     * 获得单个用户信息
     *
     * @param username 用户名
     * @return 如果用户不存在则返回null
     */
    User get(String username);

    /**
     * 批量获取用户信息
     *
     * @param pageNumber 页面号，需要根据用户总数计算
     * @param pageSize   页面大小，需要根据用户总数计算
     * @return 当前页面的用户列表信息，个数最多为页面大小指定的个数，如果页面号不正确则返回空列表
     */
    List<User> get(int pageNumber, int pageSize);

    /**
     * 批量获取用户信息
     *
     * @param pageNumber 页面号，需要根据用户总数计算
     * @param pageSize   页面大小，需要根据用户总数计算
     * @param order      结果排序，ASC为升序，DESC为降序
     * @param sort       排序参考列名
     * @return 当前页面的用户列表信息，个数最多为页面大小指定的个数，如果页面号不正确则返回空列表
     */
    List<User> get(int pageNumber, int pageSize, String order, String... sort);

    /**
     * 返回所有用户信息
     *
     * @return 所有用户信息
     */
    List<User> getAll();

    /**
     * 返回当前用户总数量，用来计算分页参数
     *
     * @return 当前用户总数量
     */
    long count();

    /**
     * 修改用户信息，如果用户名不存在则不进行任何操作
     *
     * @param user 用户信息
     */
    void update(User user);

    /**
     * 锁定用户，如果用户被锁定，则只有最低访问权限
     *
     * @param username 锁定用户名
     * @return 如果用户存在，并且锁定状态被修改为锁定则返回true，否则返回false
     */
    boolean lock(String username);

    /**
     * 判断用户是否已经被锁定
     *
     * @param username 锁定用户名
     * @return 如果用户存在，并且已经锁定则返回true，否则返回false
     */
    boolean isLocked(String username);

    /**
     * 解锁用户
     *
     * @param username 解锁用户名
     * @return 如果用户存在，并且锁定状态被修改为解锁则返回true，否则返回false
     */
    boolean unlock(String username);

    /**
     * 激活用户
     *
     * @param username 激活用户名
     * @return 如果用户存在，并且状态被修改为激活则返回true，否则返回false
     */
    boolean enable(String username);

    /**
     * 判断用户是否已经被激活
     *
     * @param username 激活用户名
     * @return 如果用户存在，并且已经激活则返回true，否则返回false
     */
    boolean isEnable(String username);

    /**
     * 禁用用户，如果用户被禁用，则不能登录到系统
     *
     * @param username 禁用用户名
     * @return 如果用户存在，并且状态被修改为禁用则返回true，否则返回false
     */
    boolean disable(String username);
}
