package org.gyt.web.api.service;

import org.gyt.web.model.SlidePicture;

import java.util.List;

/**
 * 首页大图API，提供一下接口
 * - 添加首页大图
 * - 删除首页大图
 * - 获取所有大图
 * Created by y27chen on 2016/10/10.
 */
public interface SlidePictureService {
    /**
     * 获取所有大图
     *
     * @return 如果不存在，返回空列表
     */
    List<SlidePicture> list();

    /**
     * 返回指定大图
     *
     * @param id 大图ID
     * @return 如果不存在返回空
     */
    SlidePicture get(Long id);

    /**
     * 添加大图
     *
     * @param slidePicture 目标大图
     * @return 是否添加成功
     */
    boolean add(SlidePicture slidePicture);

    /**
     * 删除大图
     *
     * @param id 大图ID
     * @return 是否删除成功
     */
    boolean remove(Long id);
}
