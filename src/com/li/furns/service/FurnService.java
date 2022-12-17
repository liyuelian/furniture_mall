package com.li.furns.service;

import com.li.furns.entity.Furn;

import java.util.List;

/**
 * @author 李
 * @version 1.0
 */
public interface FurnService {
    /**
     * 返回家居信息
     * @return
     */
    public List<Furn> queryFurns();

}
