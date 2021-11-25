package cn.uploadSys.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 角色菜单关联表
 *
 * @author fonlin
 * @date 2018/4/20
 */

@Data
@NoArgsConstructor
public class ClassesStudents extends BaseEntity{

    private Integer sid;

    private Integer cid;

    private Date createTime;

    public ClassesStudents(Integer sid, Integer cid, Date createTime) {
        this.sid = sid;
        this.cid = cid;
        this.createTime = createTime;
    }
}
