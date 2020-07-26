package kr.co.pickmeup.service;


import kr.co.pickmeup.domain.tags.Tag;
import kr.co.pickmeup.domain.tags.TagRepository;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

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
            norm_i = (p[i] - min_p[i]) / (max_p[i] - min_p[i]);
            // append normalized number
            norm_p[i] = norm_i;
        }
        return norm_p;
    }

    public void save(ProjectSaveRequestDto requestDto) {
        String content = requestDto.getContent();
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(content);

        // 태그 스코어
        double[] p1 = {100, 2000, 6000}, origin = {0, 0, 0};
        double[] min_p1 = {0, 0, 0}, max_p1 = {400, 4200, 10000};
        double score;

        p1 = normalization(p1, min_p1, max_p1);
        score = calcEuclideanDist(p1, origin);

        //List<String> tags = new ArrayList<String>();
        while (mat.find()) {

            // 태그 새로 생성해야하면
            Tag newTag = Tag.builder()
                        .name(mat.group(1))
                        .score(score)
                        .count(0)
                        .build();
                tagRepository.save(newTag);
        }
    }

    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
}
