package com.moodstation.springboot.repository;

import com.moodstation.springboot.dto.UserPostSearchDto;
import com.moodstation.springboot.entity.QUserPost;
import com.moodstation.springboot.entity.UserPost;
import com.moodstation.springboot.jwt.JwtGenerator;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UserPostSearchRepositoryImpl implements UserPostSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final JwtGenerator jwtGenerator;

    @Override
    public Page<UserPost> userPostSearchPage(String accessToken, UserPostSearchDto userPostSearchDto, Pageable pageable) {

        List<UserPost> content = jpaQueryFactory.selectFrom(QUserPost.userPost)
                .where(searchByYearMonth(userPostSearchDto),
                        searchUser(accessToken)
                )
                .orderBy(QUserPost.userPost.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        System.out.println("사이즈 : " + content.size());

        return new PageImpl<>(content, pageable, content.size());
    }

    private BooleanExpression searchByYearMonth(UserPostSearchDto userPostSearchDto) {
        System.out.println(userPostSearchDto.getSearchDate().format(DateTimeFormatter.ofPattern("yyyyMM")));

        return QUserPost.userPost.regDate.yearMonth().like(userPostSearchDto.getSearchDate().format(DateTimeFormatter.ofPattern("yyyyMM")));
    }

    private BooleanExpression searchUser(String accessToken) {
        Map<String, Object> userParseInfo = jwtGenerator.getUserParseInfo(accessToken);
        String userId = userParseInfo.get("userId").toString();
        return QUserPost.userPost.user.email.like(userId);
    }

}
