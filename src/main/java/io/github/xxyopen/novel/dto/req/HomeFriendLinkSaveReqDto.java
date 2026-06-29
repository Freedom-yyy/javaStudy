package io.github.xxyopen.novel.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户发表评论 请求DTO
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@Data
public class HomeFriendLinkSaveReqDto {

    /**
     * 链接名
     */
    @Schema(description = "链接名")
    @NotBlank(message = "链接名不能为空！")
    private String linkName;

    /**
     * 链接url
     */
    @Schema(description = "链接url")
    @NotBlank(message = "链接地址不能为空！")
    private String linkUrl;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer sort;

    /**
     * 是否开启;0-不开启 1-开启
     */
    @Schema(description = "是否开启：0-不开启 1-开启")
    private Integer isOpen;


}
