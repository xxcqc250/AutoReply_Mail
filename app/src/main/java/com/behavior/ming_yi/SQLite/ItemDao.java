package com.behavior.ming_yi.SQLite;

import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/6.
 */

public interface ItemDao<T> {

    /**
     * 新增一條記錄
     *
     * @param item
     * @return T
     */
    T insert(T item);

    /**
     * 更新記錄
     *
     * @param item
     * @return
     */
    boolean update(T item);

    /**
     * 取得所有記錄
     *
     * @return List
     */
    List<T> getAll();

    /**
     * 刪除一條記錄
     *
     * @param id
     * @return
     */
    boolean delete(long id);

    /**
     * 刪除全部記錄
     *
     * @return
     */
    void deleteAll();

    /**
     * 關閉資料庫
     *
     * @return
     */
    void close();

    /**
     * 取得資料筆數
     *
     * @return
     */
    int getRecordNumber();
}
