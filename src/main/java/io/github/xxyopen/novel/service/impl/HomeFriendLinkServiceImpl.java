package io.github.xxyopen.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.xxyopen.novel.core.common.constant.ErrorCodeEnum;
import io.github.xxyopen.novel.core.common.exception.BusinessException;
import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.core.constant.DatabaseConsts;
import io.github.xxyopen.novel.dao.entity.HomeFriendLink;
import io.github.xxyopen.novel.dao.mapper.HomeFriendLinkMapper;
import io.github.xxyopen.novel.dto.req.HomeFriendLinkSaveReqDto;
import io.github.xxyopen.novel.dto.resp.HomeFriendLinkRespDto;
import io.github.xxyopen.novel.service.HomeFriendLinkService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HomeFriendLinkServiceImpl implements HomeFriendLinkService {
    private final HomeFriendLinkMapper homeFriendLinkMapper;

//    @Override
//    public RestResp<Void> saveFriendLink(String linkName, String linkUrl, Integer sort, Integer isOpen ) {
//        HomeFriendLink homeFriendLink = new HomeFriendLink();
//        homeFriendLink.setLinkName(linkName);
//        homeFriendLink.setLinkUrl(linkUrl);
//        homeFriendLink.setSort(sort);
//        homeFriendLink.setIsOpen(isOpen);
//        homeFriendLink.setCreateTime(LocalDateTime.now());
//        homeFriendLink.setUpdateTime(LocalDateTime.now());
//        homeFriendLinkMapper.insert(homeFriendLink);
//        return RestResp.ok();
//    }

    @Override
    public RestResp<Void> saveFriendLink(HomeFriendLinkSaveReqDto dto) {
        HomeFriendLink homeFriendLink = new HomeFriendLink();
        homeFriendLink.setLinkName(dto.getLinkName());
        homeFriendLink.setLinkUrl(dto.getLinkUrl());
        homeFriendLink.setSort(dto.getSort());
        homeFriendLink.setIsOpen(dto.getIsOpen());
        homeFriendLink.setCreateTime(LocalDateTime.now());
        homeFriendLink.setUpdateTime(LocalDateTime.now());
        homeFriendLinkMapper.insert(homeFriendLink);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> deleteFriendLink(Long id) {
        homeFriendLinkMapper.deleteById(id);
/*        QueryWrapper<HomeFriendLink> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.CommonColumnEnum.ID.getName(),id);
        homeFriendLinkMapper.delete(queryWrapper);*/
        return  RestResp.ok();
    }

    /**
     *
     * @param id
     * @return
     * @throws BusinessException 找不到友情鏈接會抛異常
     */
    @Override
    public RestResp<HomeFriendLinkRespDto> getFriendLink(Long id) throws BusinessException {
        HomeFriendLinkRespDto homeFriendLinkRespDto = new HomeFriendLinkRespDto();
        HomeFriendLink homeFriendLink = homeFriendLinkMapper.selectById(id);
        if(homeFriendLink != null) {
            homeFriendLinkRespDto.setLinkUrl(homeFriendLink.getLinkUrl());
            homeFriendLinkRespDto.setLinkName(homeFriendLink.getLinkName());
            return RestResp.ok(homeFriendLinkRespDto);
        }
        throw new BusinessException(ErrorCodeEnum.HOME_FRIEND_LINK_NOT_EXIST);

    }

    @Override
    public RestResp<Void> updateFriendLink(Long id, String LinkUrl, String LinkName) {
        val homeFriendLink = homeFriendLinkMapper.selectById(id);
        if (homeFriendLink != null) {
            homeFriendLink.setLinkUrl(LinkUrl);
            homeFriendLink.setLinkName(LinkName);
            homeFriendLink.setUpdateTime(LocalDateTime.now());
            homeFriendLinkMapper.updateById(homeFriendLink);
            return RestResp.ok();
        }
        return RestResp.error();
    }


}
