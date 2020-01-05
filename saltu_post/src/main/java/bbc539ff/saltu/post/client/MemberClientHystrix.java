package bbc539ff.saltu.post.client;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;

public class MemberClientHystrix implements MemberClient {
    @Override
    public Result findByMemberId(String memberId, String token) {
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
}
