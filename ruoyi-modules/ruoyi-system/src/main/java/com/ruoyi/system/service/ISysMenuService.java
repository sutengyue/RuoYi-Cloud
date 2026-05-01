package com.ruoyi.system.service;

import java.util.List;
import java.util.Set;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysMenu;
import com.ruoyi.system.domain.dto.SysMenuDTO;
import com.ruoyi.system.domain.vo.RouterVo;
import com.ruoyi.system.domain.vo.SysMenuVO;
import com.ruoyi.system.domain.vo.TreeSelect;

/**
 * 菜单 业务层
 * 
 * @author ruoyi
 */
public interface ISysMenuService extends IService<SysMenu>
{
    /**
     * 根据用户查询系统菜单列表
     * 
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenuVO> selectMenuList(Long userId);

    /**
     * 根据用户查询系统菜单列表
     * 
     * @param menuDTO 菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenuVO> selectMenuList(SysMenuDTO menuDTO, Long userId);

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据角色ID查询权限
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * 根据用户ID查询菜单树信息
     * 
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 根据角色ID查询菜单树信息
     * 
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    public List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * 构建前端路由所需要的菜单
     * 
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);

    /**
     * 构建前端所需要树结构
     * 
     * @param menus 菜单列表
     * @return 树结构列表
     */
    public List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * 根据菜单ID查询信息
     * 
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    public SysMenuVO selectMenuById(Long menuId);

    /**
     * 是否存在菜单子节点
     * 
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean hasChildByMenuId(Long menuId);

    /**
     * 查询菜单是否存在角色
     * 
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkMenuExistRole(Long menuId);

    /**
     * 新增保存菜单信息
     * 
     * @param menuDTO 菜单信息
     * @return 结果
     */
    public int insertMenu(SysMenuDTO menuDTO);

    /**
     * 修改保存菜单信息
     * 
     * @param menuDTO 菜单信息
     * @return 结果
     */
    public int updateMenu(SysMenuDTO menuDTO);

    /**
     * 保存菜单排序
     * 
     * @param menuIds 菜单ID
     * @param orderNums 排序ID
     */
    public void updateMenuSort(String[] menuIds, String[] orderNums);

    /**
     * 删除菜单管理信息
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    public int deleteMenuById(Long menuId);

    /**
     * 校验菜单名称是否唯一
     * 
     * @param menuDTO 菜单信息
     * @return 结果
     */
    public boolean checkMenuNameUnique(SysMenuDTO menuDTO);

    /**
     * 校验路由组合是否唯一
     *
     * @param menuDTO 菜单信息
     * @return 结果
     */
    public boolean checkRouteConfigUnique(SysMenuDTO menuDTO);
}