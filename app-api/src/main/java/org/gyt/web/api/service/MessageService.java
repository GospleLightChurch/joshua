package org.gyt.web.api.service;

import org.gyt.web.model.Message;
import org.gyt.web.model.MessageType;

import java.util.List;

/**
 * 留言消息API，提供以下接口
 * - 获取所有留言消息
 * - 根据类型获取留言消息
 * - 添加留言消息
 * Created by y27chen on 2016/9/20.
 */
public interface MessageService {

    /**
     * 获取指定留言信息
     *
     * @param id 留言信息ID
     * @return 留言信息
     */
    Message get(Long id);

    /**
     * 获取所有留言信息
     *
     * @return 返回所有留言信息列表
     */
    List<Message> getAll();

    /**
     * 根据留言类型来获取留言消息
     *
     * @param type 留言类型
     * @return 对应留言类型留言列表
     */
    List<Message> getByType(MessageType type);

    /**
     * 新建留言
     *
     * @param message 留言对象
     * @return 当留言创建成功是返回true，否则返回false
     */
    boolean createOrUpdate(Message message);
}
