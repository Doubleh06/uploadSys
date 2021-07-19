package cn.nlf.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 实体bean基类
 *
 * @author fonlin
 * @date 2018/4/19
 */
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    /**
//     * 这个字段不允许更新
//     */
//    @Column(updatable = false)
//    private Date createDate;
//
//    private Date updateDate;
//
//    /**
//     * 这个字段不允许更新
//     */
//    @Column(updatable = false)
//    private Integer createBy;
//
//    private Integer updateBy;
//
//    /**
//     * 是否删除，默认没有删除
//     */
//    private Boolean deleted = false;


}
