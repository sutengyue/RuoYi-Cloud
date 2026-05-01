package com.ruoyi.system.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.domain.dto.SysNoticeDTO;
import com.ruoyi.system.domain.vo.SysNoticeVO;

/**
 * 公告 服务层
 * 
 * @author ruoyi
 */
public interface ISysNoticeService extends IService<SysNotice>
{
    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public SysNoticeVO selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     * 
     * @param noticeDTO 公告信息
     * @return 公告集合
     */
    public List<SysNoticeVO> selectNoticeList(SysNoticeDTO noticeDTO);

    /**
     * 新增公告
     * 
     * @param noticeDTO 公告信息
     * @return 结果
     */
    public int insertNotice(SysNoticeDTO noticeDTO);

    /**
     * 修改公告
     * 
     * @param noticeDTO 公告信息
     * @return 结果
     */
    public int updateNotice(SysNoticeDTO noticeDTO);

    /**
     * 删除公告信息
     * 
     * @param noticeId 公告ID
     * @return 结果
     */
    public int deleteNoticeById(Long noticeId);

    /**
     * 批量删除公告信息
     * 
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    public int deleteNoticeByIds(Long[] noticeIds);
}