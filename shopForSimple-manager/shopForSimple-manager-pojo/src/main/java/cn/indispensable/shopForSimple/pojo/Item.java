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
package cn.indispensable.shopForSimple.pojo;

/**
 * @author cicicc
 * @since 1.0.0
 */
public class Item extends TbItem{
        public Item() {
        }
        public Item(TbItem tbItem) {
            this.setBarcode(tbItem.getBarcode());
            this.setCid(tbItem.getCid());
            this.setCreated(tbItem.getCreated());
            this.setId(tbItem.getId());
            this.setImage(tbItem.getImage());
            this.setNum(tbItem.getNum());
            this.setPrice(tbItem.getPrice());
            this.setSellPoint(tbItem.getSellPoint());
            this.setStatus(tbItem.getStatus());
            this.setTitle(tbItem.getTitle());
            this.setUpdated(tbItem.getUpdated());
        }
    public String[] getImages() {
        String image2 = this.getImage();
        if (image2 != null && !"".equals(image2)) {
            String[] strings = image2.split(",");
            return strings;
        }
        return null;
    }
}
