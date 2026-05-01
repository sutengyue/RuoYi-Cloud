package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.bean.BeanConvertUtils;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.domain.dto.SysNoticeDTO;
import com.ruoyi.system.domain.vo.SysNoticeVO;
import com.ruoyi.system.mapper.SysNoticeMapper;
import com.ruoyi.system.service.ISysNoticeService;

/**
 * 公告 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService
{

    /**
     * 查询公告信息
     */
    @Override
    public SysNoticeVO selectNoticeById(Long noticeId)
    {
        SysNotice notice = baseMapper.selectNoticeById(noticeId);
        return BeanConvertUtils.convert(notice, SysNoticeVO.class);
    }

    /**
     * 查询公告列表
     */
    @Override
    public List<SysNoticeVO> selectNoticeList(SysNoticeDTO noticeDTO)
    {
        SysNotice notice = BeanConvertUtils.convert(noticeDTO, SysNotice.class);
        List<SysNotice> list = baseMapper.selectNoticeList(notice);
        return BeanConvertUtils.convertList(list, SysNoticeVO.class);
    }

    /**
     * 新增公告
     */
    @Override
    public int insertNotice(SysNoticeDTO noticeDTO)
    {
        SysNotice notice = BeanConvertUtils.convert(noticeDTO, SysNotice.class);
        return baseMapper.insertNotice(notice);
    }

    /**
     * 修改公告
     */
    @Override
    public int updateNotice(SysNoticeDTO noticeDTO)
    {
        SysNotice notice = BeanConvertUtils.convert(noticeDTO, SysNotice.class);
        return baseMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     */
    @Override
    public int deleteNoticeById(Long noticeId)
    {
        return baseMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds)
    {
        return baseMapper.deleteNoticeByIds(noticeIds);
    }
}