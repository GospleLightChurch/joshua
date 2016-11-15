package org.gyt.web.api.service;

import org.gyt.web.model.Article;

import java.util.List;

/**
 * 文章API，提供以下接口
 * - 获取指定文章内容
 * - 获取团契文章列表
 * - 获取用户文章列表
 * - 获取最新发布的文章
 * - 获取所有文章
 * - 创建文章
 * - 激活、禁用文章
 * - 申请审核文章
 * - 发布文章
 * - 增加文章浏览次数
 * Created by y27chen on 2016/9/18.
 */
public interface ArticleService {

    /**
     * 获取指定文章对象信息
     *
     * @param id 文章ID
     * @return 如果文章不存在则返回null
     */
    Article get(Long id);

    /**
     * 获取团契文章列表，包含与团契相关的所有文章（已发布，禁用的）
     *
     * @param name 团契名称
     * @return 如果团契不存在或者没有文章返回空列表
     */
    List<Article> getFromFellowship(String name);

    /**
     * 获取团契最新发布文章列表
     *
     * @param name 团契名称
     * @return 如果团契不存在或者没有文章返回空列表
     */
    List<Article> getFellowshipPublishedArticles(String name);

    /**
     * 获取用户文章列表，包含与该用户相关的所有文章（文章作者，审阅者）
     *
     * @param username 用户名称
     * @return 如果用户不存在或者没有文章返回空列表
     */
    List<Article> getFromUser(String username);

    /**
     * 获取最新发布的文章
     *
     * @return 最新发布的文章
     */
    List<Article> getLatestArticles();

    /**
     * 获取最新的教堂咨询
     *
     * @return 组合最新的教堂咨询文章列表
     */
    List<Article> getChurchArticles();

    /**
     * 获取最新的团契文章
     *
     * @return 组合最新的教堂咨询文章列表
     */
    List<Article> getFellowshipArticles();

    /**
     * 获取所有文章
     *
     * @return 最有的文章，包括未发布和审核中的
     */
    List<Article> getAll();

    /**
     * 创建文章
     *
     * @param article 文章对象
     * @return 如果文章创建成功返回true，否则返回false
     */
    Article createOrUpdate(Article article);

    /**
     * 激活文章
     *
     * @param id 文章ID
     * @return 如果文章存在，并且状态被修改为已激活则返回true，否则返回false
     */
    boolean enable(Long id);

    /**
     * 判断文章是否为已经激活
     *
     * @param id 文章ID
     * @return 如果文章存在并且状态为激活返回true，否则返回false
     */
    boolean isEnabled(Long id);

    /**
     * 禁用文章，如果文章被禁用，则不能在前端访问
     *
     * @param id 文章ID
     * @return 如果文章存在并且状态为禁用返回true，否则返回false
     */
    boolean disable(Long id);

    /**
     * 申请审阅文章，文章状态会修改为审阅中
     *
     * @param id 文章ID
     * @return 如果文章存在，并且状态被修改为审阅中返回true，否则返回false
     */
    boolean audit(Long id);

    /**
     * 发布文章
     *
     * @param id 文章ID
     * @return 如果文章存在，并且状态被修改为已发布返回true，否则返回false
     */
    boolean publish(Long id);

    /**
     * 增加文章浏览次数
     *
     * @param article 目标文章
     * @return 增加次数以后的文章
     */
    Article increasePageView(Article article);

    /**
     * 增加文章浏览次数
     *
     * @param id 目标文章id
     * @return 增加次数以后的文章
     */
    Article increasePageView(Long id);
}
