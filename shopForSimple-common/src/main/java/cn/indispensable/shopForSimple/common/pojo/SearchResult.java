/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.indispensable.shopForSimple.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 封装查询结果对象
 * @author cicicc
 * @since 1.0.0
 */
public class SearchResult implements Serializable {
    //1）商品列表 List<SearchItem>
    private List<SearchItem> itemList;
    //2）总页数。Int totalPages。总记录数/每页显示的记录数向上取整。把每页显示的记录是配置到属性文件中。
    private int totalPages;
    //3）总记录数。Int recordCount
    private int recordCount;


    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRecourdCount() {
        return recordCount;
    }

    public void setRecourdCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
