
alter table qczj_hq add column `filterate_status` int(1)  comment '是否过滤通过：0:清洗中  1：通过 2：不通过';
alter table qczj_hq add column `check_status` int(1)  comment '质检状态 0：未知 1：成功  2：战败';