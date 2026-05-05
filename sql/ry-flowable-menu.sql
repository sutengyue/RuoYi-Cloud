SET NAMES utf8mb4;

-- ----------------------------
-- 流程管理菜单
-- ----------------------------

-- 一级菜单 - 流程管理
INSERT INTO sys_menu VALUES('5', '流程管理', '0', '5', 'flowable', null, '', '', 1, 0, 'M', '0', '0', '', 'tree-table', 'admin', sysdate(), '', null, '流程管理目录');

-- 二级菜单
INSERT INTO sys_menu VALUES('600', '流程部署', '5', '1', 'deployment', 'flowable/deployment/index', '', '', 1, 0, 'C', '0', '0', 'flowable:deployment:list', 'deploy', 'admin', sysdate(), '', null, '流程部署菜单');
INSERT INTO sys_menu VALUES('601', '流程定义', '5', '2', 'process', 'flowable/process/index', '', '', 1, 0, 'C', '0', '0', 'flowable:process:list', 'process', 'admin', sysdate(), '', null, '流程定义菜单');
INSERT INTO sys_menu VALUES('602', '运行中流程', '5', '3', 'instance', 'flowable/instance/index', '', '', 1, 0, 'C', '0', '0', 'flowable:instance:list', 'instance', 'admin', sysdate(), '', null, '运行中流程菜单');
INSERT INTO sys_menu VALUES('603', '任务管理', '5', '4', 'task', 'flowable/task/index', '', '', 1, 0, 'C', '0', '0', 'flowable:task:list', 'task', 'admin', sysdate(), '', null, '任务管理菜单');
INSERT INTO sys_menu VALUES('604', '已完成流程', '5', '5', 'history', 'flowable/history/index', '', '', 1, 0, 'C', '0', '0', 'flowable:history:list', 'history', 'admin', sysdate(), '', null, '已完成流程菜单');

-- 流程部署按钮
INSERT INTO sys_menu VALUES('1100', '部署查询', '600', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:deployment:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1101', '部署上传', '600', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:deployment:upload', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1102', '部署删除', '600', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:deployment:remove', '#', 'admin', sysdate(), '', null, '');

-- 流程定义按钮
INSERT INTO sys_menu VALUES('1103', '流程查询', '601', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:process:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1104', '流程启动', '601', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:process:start', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1105', '流程挂起', '601', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:process:suspend', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1106', '流程激活', '601', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:process:activate', '#', 'admin', sysdate(), '', null, '');

-- 运行中流程按钮
INSERT INTO sys_menu VALUES('1107', '实例查询', '602', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:instance:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1108', '实例终止', '602', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:instance:terminate', '#', 'admin', sysdate(), '', null, '');

-- 任务管理按钮
INSERT INTO sys_menu VALUES('1109', '任务查询', '603', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:task:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1110', '任务认领', '603', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:task:claim', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1111', '任务完成', '603', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:task:complete', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1112', '任务转派', '603', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:task:delegate', '#', 'admin', sysdate(), '', null, '');

-- 已完成流程按钮
INSERT INTO sys_menu VALUES('1113', '历史查询', '604', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:history:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1114', '历史删除', '604', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:history:remove', '#', 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 角色菜单关联 - 普通角色
-- ----------------------------
INSERT INTO sys_role_menu VALUES ('2', '5');
INSERT INTO sys_role_menu VALUES ('2', '600');
INSERT INTO sys_role_menu VALUES ('2', '601');
INSERT INTO sys_role_menu VALUES ('2', '602');
INSERT INTO sys_role_menu VALUES ('2', '603');
INSERT INTO sys_role_menu VALUES ('2', '604');
INSERT INTO sys_role_menu VALUES ('2', '1100');
INSERT INTO sys_role_menu VALUES ('2', '1101');
INSERT INTO sys_role_menu VALUES ('2', '1102');
INSERT INTO sys_role_menu VALUES ('2', '1103');
INSERT INTO sys_role_menu VALUES ('2', '1104');
INSERT INTO sys_role_menu VALUES ('2', '1105');
INSERT INTO sys_role_menu VALUES ('2', '1106');
INSERT INTO sys_role_menu VALUES ('2', '1107');
INSERT INTO sys_role_menu VALUES ('2', '1108');
INSERT INTO sys_role_menu VALUES ('2', '1109');
INSERT INTO sys_role_menu VALUES ('2', '1110');
INSERT INTO sys_role_menu VALUES ('2', '1111');
INSERT INTO sys_role_menu VALUES ('2', '1112');
INSERT INTO sys_role_menu VALUES ('2', '1113');
INSERT INTO sys_role_menu VALUES ('2', '1114');