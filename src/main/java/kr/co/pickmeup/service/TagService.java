package kr.co.pickmeup.service;


import kr.co.pickmeup.domain.comments.CommentRepository;
import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projects.ProjectRepository;
import kr.co.pickmeup.domain.projectsTags.ProjectTagRepository;
import kr.co.pickmeup.domain.tags.Tag;
import kr.co.pickmeup.domain.tags.TagRepository;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectPaginationRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectResponseDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectSaveRequestDto;
import kr.co.pickmeup.web.dto.TagsDto.TagPaginationRequestDto;
import kr.co.pickmeup.web.dto.TagsDto.TagScoreResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;
    private final ProjectTagRepository projectTagRepository;

    /*
    * 태그 스코어 계산 ********************
    * */
    public static double calcEuclideanDist(double[] p1, double[] p2) {
        double sum = 0;
        double dist;

        for (int i = 0; i < p1.length; i++) {
            sum += Math.pow(p1[i] - p2[i], 2);
        }
        dist = Math.sqrt(sum);

        return dist;
    }

    public static double[] normalization(double[] p, double[] min_p, double[] max_p) {
        double[] norm_p = new double[p.length];

        for (int i = 0; i < p.length; i++) {
            double norm_i;
            // normalize
            norm_i = (p[i] - min_p[i]) / (max_p[i] - min_p[i] + 1E-8);
            // append normalized number
            norm_p[i] = norm_i;
        }
        return norm_p;
    }

    @Transactional
    public double calcTagScore(String name) {

        // 태그 스코어
        double[] origin = {0, 0, 0}, min_p1 = {0, 0, 0};
        double score;

        // p1 포맷 = {해쉬태그가 사용된 사용된 게시물 개수, 해쉬태그를 사용한 게시물의 댓글수 총합, 해쉬태그를 사용한 게시물의 조회수 총합}
        double[] max_p1 = new double[3], p1 = new double[3];

        // max_p1 할당
        max_p1[0] = projectRepository.getAllProjectsCount();
        max_p1[1] = commentRepository.getAllCommentsCount();
        max_p1[2] = projectRepository.getAllViewnumCount();

        // p1 할당
        List<Long> projectIdListByHashtag = projectTagRepository.getProjectIdListByHashtag(name);

        double p1_2 = 0, p1_3 = 0;
        for(Long project_id: projectIdListByHashtag) {
            p1_2 += commentRepository.getCommentsCount(project_id);
            p1_3 += projectRepository.getViewnumCount(project_id);
        }

        p1[0] = projectIdListByHashtag.size();
        p1[1] = p1_2;
        p1[2] = p1_3;

        p1 = normalization(p1, min_p1, max_p1);
        score = calcEuclideanDist(p1, origin);

        return score;
    }

    /*
     *********************
     * */



    /*
     * 게시글 content에서 tag이름 파싱
     * */
    public Set<String> parseTags(String content) {
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(content);

        Set<String> tags = new HashSet<>();

        while (mat.find()) {

            String name = mat.group(1);

            tags.add(name);

        }

        return tags;
    }



    /*
     * 게시글 전체 불러오기
     * */
//    public Page<Tag> findAll(Pageable pageable) {
//        return tagRepository.findAll(pageable);
//    }
    @Transactional
    public Page<Tag> findAll(Pageable pageable, TagPaginationRequestDto requestDto) {
        System.out.println(requestDto.getSize());
        // 전체 조회이면
        if(requestDto.getSize() == null) {
            return tagRepository.findAll(pageable);
        }

        // 전체 조회 + 페이지네이션
        else {
            PageRequest pageRequest = PageRequest.of(0, requestDto.getSize(), Sort.by("score").descending());
            return tagRepository.findAll(pageRequest);
        }

    }

}
