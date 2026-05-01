package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanConvertUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.dto.SysPostDTO;
import com.ruoyi.system.domain.vo.SysPostVO;
import com.ruoyi.system.mapper.SysPostMapper;
import com.ruoyi.system.mapper.SysUserPostMapper;
import com.ruoyi.system.service.ISysPostService;

/**
 * 岗位信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService
{
    @Autowired
    private SysUserPostMapper userPostMapper;

    /**
     * 查询岗位信息集合
     */
    @Override
    public List<SysPostVO> selectPostList(SysPostDTO postDTO)
    {
        SysPost post = BeanConvertUtils.convert(postDTO, SysPost.class);
        List<SysPost> list = baseMapper.selectPostList(post);
        return BeanConvertUtils.convertList(list, SysPostVO.class);
    }

    /**
     * 查询所有岗位
     */
    @Override
    public List<SysPostVO> selectPostAll()
    {
        List<SysPost> list = baseMapper.selectPostAll();
        return BeanConvertUtils.convertList(list, SysPostVO.class);
    }

    /**
     * 通过岗位ID查询岗位信息
     */
    @Override
    public SysPostVO selectPostById(Long postId)
    {
        SysPost post = baseMapper.selectPostById(postId);
        return BeanConvertUtils.convert(post, SysPostVO.class);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     */
    @Override
    public List<Long> selectPostListByUserId(Long userId)
    {
        return baseMapper.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     */
    @Override
    public boolean checkPostNameUnique(SysPostDTO postDTO)
    {
        SysPost post = BeanConvertUtils.convert(postDTO, SysPost.class);
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = baseMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     */
    @Override
    public boolean checkPostCodeUnique(SysPostDTO postDTO)
    {
        SysPost post = BeanConvertUtils.convert(postDTO, SysPost.class);
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = baseMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     */
    @Override
    public int countUserPostById(Long postId)
    {
        return userPostMapper.countUserPostById(postId);
    }

    /**
     * 删除岗位信息
     */
    @Override
    public int deletePostById(Long postId)
    {
        return baseMapper.deletePostById(postId);
    }

    /**
     * 批量删除岗位信息
     */
    @Override
    public int deletePostByIds(Long[] postIds)
    {
        for (Long postId : postIds)
        {
            SysPostVO postVO = selectPostById(postId);
            SysPost post = BeanConvertUtils.convert(postVO, SysPost.class);
            if (countUserPostById(postId) > 0)
            {
                throw new ServiceException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return baseMapper.deletePostByIds(postIds);
    }

    /**
     * 新增保存岗位信息
     */
    @Override
    public int insertPost(SysPostDTO postDTO)
    {
        SysPost post = BeanConvertUtils.convert(postDTO, SysPost.class);
        return baseMapper.insertPost(post);
    }

    /**
     * 修改保存岗位信息
     */
    @Override
    public int updatePost(SysPostDTO postDTO)
    {
        SysPost post = BeanConvertUtils.convert(postDTO, SysPost.class);
        return baseMapper.updatePost(post);
    }
}