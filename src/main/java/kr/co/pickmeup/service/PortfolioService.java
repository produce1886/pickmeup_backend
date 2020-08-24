package kr.co.pickmeup.service;

import kr.co.pickmeup.domain.portfolio.Portfolio;
import kr.co.pickmeup.domain.portfolio.PortfolioRepository;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
import kr.co.pickmeup.domain.projectsTags.ProjectTagRepository;
import kr.co.pickmeup.domain.tags.Tag;
import kr.co.pickmeup.domain.tags.TagRepository;
import kr.co.pickmeup.domain.users.User;
import kr.co.pickmeup.domain.users.UserRepository;
import kr.co.pickmeup.web.dto.PortfolioDto.PortfolioResponseDto;
import kr.co.pickmeup.web.dto.PortfolioDto.PortfolioSaveRequestDto;
import kr.co.pickmeup.web.dto.PortfolioDto.PortfolioUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final TagService tagService;
    private final TagRepository tagRepository;
    private final ProjectTagRepository projectTagRepository;
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    // image decoding
    public String decodeImage(String encodedImage) {
        byte[] image = Base64.decodeBase64(encodedImage);
        String result = new String(image);

        return result;
    }

    // create
    @Transactional
    public Long save(PortfolioSaveRequestDto requestDto) {
        // user 찾기
        String email = requestDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. email=" + email));

        // 태그 작업
//        String content = requestDto.getContent();
//        Set<String> tags = tagService.parseTags(content);
        Set<String> tags = new HashSet<>(Arrays.asList(requestDto.getTags()));


        Portfolio portfolio = requestDto.toEntity();
        String image = portfolio.getImage();
        if(image != null) {
            String decodedImage = decodeImage(image);
            portfolio.setImage(decodedImage);
        }

        for(String name : tags) {
            // join table 작업
            ProjectTag projectTag = ProjectTag.builder()
                    .tag(name)
                    .build();

            portfolio.addProjectTag(projectTag);

            // 태그 테이블 저장 작업
            // 태그 테이블에 없는 태그면
            if(!tagRepository.existsByName(name)) {
                Tag newTag = Tag.builder()
                        .name(name)
                        .score(0)
                        .count(1)
                        .build();
//                project.addTag(newTag);
                tagRepository.save(newTag);
            }

            // 태그 테이블에 있는 태그면
            else {
                double score = tagService.calcTagScore(name); // score 계산
                //System.out.println(score);

                Tag tag = tagRepository.findByName(name).orElseThrow(()-> new IllegalArgumentException("해당 태그가 존재하지 않습니다. name=" + name));
                tag.add_count();
                tag.setScore(score);

            }
        }

        // user 연동
        user.addPortfolio(portfolio);
        //projectRepository.save(project);
        return  userRepository.save(user).getId();

        //return projectRepository.save(project).getId();
    }

    // read one
    @Transactional
    public PortfolioResponseDto findById (Long id) {
        Portfolio entity = portfolioRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 포트폴리오는 존재하지 않습니다. id=" + id));
        entity.addViewNum();
        return new PortfolioResponseDto(entity);
    }

    // update
    @Transactional
    public Long update(Long id, PortfolioUpdateRequestDto requestDto) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        Set<String> tags = projectTagRepository.getTagListByProjectId(id);
        Set<String> new_tags = new HashSet<>(Arrays.asList(requestDto.getTags()));

        // 기존 태그들 count--
        for(String name: tags) {
            Tag tag = tagRepository.findByName(name).orElseThrow(()-> new IllegalArgumentException("해당 태그가 존재하지 않습니다. name=" + name));
            Integer count = tag.getCount();
            // 1개짜리를 없앤거면 태그 삭제
            if(count == 1) {
                tagRepository.delete(tag);
            }
            else {
                tag.subtract_count();
            }
        }

        // join 테이블 작업 초기화 & project_tag에서 옛 태그 레코드 삭제
        portfolio.deleteProjectTag();
        for(ProjectTag projectTag: projectTagRepository.findAllByPortfolio(portfolio)) {
            projectTagRepository.delete(projectTag);
        }

        // 새로운 태그 있는지 확인 후 추가
        for(String name: new_tags) {
            // 태그 테이블 저장 작업
            // 태그 테이블에 없는 태그면
            if(!tagRepository.existsByName(name)) {
                Tag newTag = Tag.builder()
                        .name(name)
                        .score(0)
                        .count(1)
                        .build();
                tagRepository.save(newTag);
            }

            // 태그 테이블에 있는 태그면
            else {
                double score = tagService.calcTagScore(name); // score 계산
                //System.out.println(score);

                Tag tag = tagRepository.findByName(name).orElseThrow(()-> new IllegalArgumentException("해당 태그가 존재하지 않습니다. name=" + name));
                tag.add_count();
                tag.setScore(score);
            }

            // join 테이블 작업
            ProjectTag projectTag = ProjectTag.builder()
                    .tag(name)
                    .build();

            portfolio.addProjectTag(projectTag);
        }

        String image = requestDto.getImage();
        if(image != null)
            image = decodeImage(image);

        portfolio.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getCategory(),
                requestDto.getHuntingField(), requestDto.getRegion(), requestDto.getPortfolioCategory(), image);

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 포트폴리오는 존재하지 않습니다. id=" + id));
        Set<String> tags = projectTagRepository.getTagListByProjectId(id);

        for (String name: tags) {
            Tag tag = tagRepository.findByName(name).orElseThrow(()-> new IllegalArgumentException("해당 태그가 존재하지 않습니다. name=" + name));
            Integer count = tag.getCount();

            // 1개짜리를 없앤거면 태그 삭제
            if(count == 1) {
                tagRepository.delete(tag);
            }
            else {
                tag.subtract_count();
            }
        }
        portfolioRepository.delete(portfolio);
    }
}
