package cn.indispensable.shopForSimple.search.service;

import cn.indispensable.shopForSimple.common.utils.E3Result;

public interface SearchItemService {
    /**
     * 添加数据到索引库中
     */
    E3Result importItems();
}
