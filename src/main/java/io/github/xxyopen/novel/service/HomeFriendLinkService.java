package io.github.xxyopen.novel.service;

import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.dto.req.HomeFriendLinkSaveReqDto;
import io.github.xxyopen.novel.dto.resp.HomeFriendLinkRespDto;
import io.github.xxyopen.novel.dto.resp.UserInfoRespDto;

/**
 * 友情链接 服务类
 *
 * @author  yyy
 * @date 2022/5/17
 */
public interface HomeFriendLinkService {

    /**
     *
     * @param dto 友情链接保存的dto
     * @return
     */
    RestResp<Void> saveFriendLink(HomeFriendLinkSaveReqDto dto );

    /**
     * 友情链接删除
     *
     * @param id     友情链接id
     * @return void
     */
    RestResp<Void> deleteFriendLink(Long id);

    /**
     *
     * @param id
     * @return
     */
    RestResp<HomeFriendLinkRespDto> getFriendLink(Long id);

    /**
     *
     * @param id
     * @return
     */
    RestResp<Void> updateFriendLink(Long id, String LinkUrl, String LinkName );

}
