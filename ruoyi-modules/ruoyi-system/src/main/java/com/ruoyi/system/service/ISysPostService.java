package com.ruoyi.system.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.dto.SysPostDTO;
import com.ruoyi.system.domain.vo.SysPostVO;

/**
 * 岗位信息 服务层
 * 
 * @author ruoyi
 */
public interface ISysPostService extends IService<SysPost>
{
    /**
     * 查询岗位信息集合
     * 
     * @param postDTO 岗位信息
     * @return 岗位列表
     */
    public List<SysPostVO> selectPostList(SysPostDTO postDTO);

    /**
     * 查询所有岗位
     * 
     * @return 岗位列表
     */
    public List<SysPostVO> selectPostAll();

    /**
     * 通过岗位ID查询岗位信息
     * 
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    public SysPostVO selectPostById(Long postId);

    /**
     * 根据用户ID获取岗位选择框列表
     * 
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    public List<Long> selectPostListByUserId(Long userId);

    /**
     * 校验岗位名称
     * 
     * @param postDTO 岗位信息
     * @return 结果
     */
    public boolean checkPostNameUnique(SysPostDTO postDTO);

    /**
     * 校验岗位编码
     * 
     * @param postDTO 岗位信息
     * @return 结果
     */
    public boolean checkPostCodeUnique(SysPostDTO postDTO);

    /**
     * 通过岗位ID查询岗位使用数量
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    public int countUserPostById(Long postId);

    /**
     * 删除岗位信息
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    public int deletePostById(Long postId);

    /**
     * 批量删除岗位信息
     * 
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    public int deletePostByIds(Long[] postIds);

    /**
     * 新增保存岗位信息
     * 
     * @param postDTO 岗位信息
     * @return 结果
     */
    public int insertPost(SysPostDTO postDTO);

    /**
     * 修改保存岗位信息
     * 
     * @param postDTO 岗位信息
     * @return 结果
     */
    public int updatePost(SysPostDTO postDTO);
}