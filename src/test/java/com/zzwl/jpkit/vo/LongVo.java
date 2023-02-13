package com.zzwl.jpkit.vo;

import com.zzwl.jpkit.anno.JFString;
import com.zzwl.jpkit.anno.JRename;
import com.zzwl.jpkit.core.JSON;

/**
 * @author: backpackerxl
 * @date: 2023/2/11
 * @filename: LongVo
 * @email: backpackerxh@163.com
 **/
public class LongVo {
    private Long id;
    @JRename("dept_id")
    private long deptId;

    public LongVo(Long id, long deptId) {
        this.id = id;
        this.deptId = deptId;
    }

    public Long getId() {
        return id;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return JSON.stringify(this).pretty();
    }
}
