package io.github.xxyopen.novel.controller.homework;

import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.core.constant.ApiRouterConsts;
import io.github.xxyopen.novel.core.constant.SystemConfigConsts;
import io.github.xxyopen.novel.dao.entity.HomeFriendLink;
import io.github.xxyopen.novel.dto.req.HomeFriendLinkSaveReqDto;
import io.github.xxyopen.novel.dto.resp.HomeFriendLinkRespDto;
import io.github.xxyopen.novel.service.HomeFriendLinkService;
import io.github.xxyopen.novel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FriendLinkController", description = "友情链接模块")
@SecurityRequirement(name = SystemConfigConsts.HTTP_AUTH_HEADER_NAME)
@RestController
@RequestMapping(ApiRouterConsts.API_FRIEND_LINK_PREFIX)
@RequiredArgsConstructor
public class FriendLinkController {
    private final HomeFriendLinkService homeFriendLinkService;

    /**
     *
     * 友情链接新增接口
     */
    @Operation(summary = "友情链接新增接口")
    @PostMapping("homefriendlink")
    public RestResp<Void> saveHomeFriendLink(@RequestBody HomeFriendLinkSaveReqDto dto) {
        return homeFriendLinkService.saveFriendLink(dto);
    }

    /**
     *
     * 友情链接删除接口
     */
    @Operation(summary = "友情链接删除接口")
    @DeleteMapping("deletehomefriendlink/{id}")
    public RestResp<Void> deleteHomeFriendLink(@Parameter(description = "链接id") @PathVariable Long id) {
        return homeFriendLinkService.deleteFriendLink(id);
    }

    /**
     * 友情链接查询接口
     */
    @Operation(summary = "友情链接查询接口")
    @GetMapping("gethomefriendlin/{id}")
    public RestResp<HomeFriendLinkRespDto>getHomeFriendLink(@Parameter(description = "链接id") @PathVariable Long id) {
        return homeFriendLinkService.getFriendLink(id);
    }

    @Operation(summary = "友情链接更新接口")
    @PutMapping("updatehomefriendlink")
    public RestResp<Void> updateHomeFriendLink( Long id, String linkUrl, String linkName) {
        return homeFriendLinkService.updateFriendLink(id, linkUrl, linkName);
    }
}
