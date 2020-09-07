package kr.co.pickmeup.service;

import kr.co.pickmeup.domain.portfolio.Portfolio;
import kr.co.pickmeup.domain.portfolio.PortfolioRepository;
import kr.co.pickmeup.domain.portfoliosTags.PortfolioTag;
import kr.co.pickmeup.domain.portfoliosTags.PortfolioTagRepository;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
import kr.co.pickmeup.domain.tags.Tag;
import kr.co.pickmeup.domain.tags.TagRepository;
import kr.co.pickmeup.domain.users.User;
import kr.co.pickmeup.domain.users.UserRepository;
import kr.co.pickmeup.web.dto.PortfolioDto.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final TagService tagService;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioTagRepository portfolioTagRepository;

    // image decoding
    public String decodeImage(String encodedImage) {
        byte[] image = Base64.decodeBase64(encodedImage);
        String result = new String(image);
        return result;
    }

    // create
    @Transactional
    public void save(PortfolioSaveRequestDto requestDto) {
        // user 찾기
        String email = requestDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. email=" + email));

        // 태그 작업
        Set<String> tags = new HashSet<>(Arrays.asList(requestDto.getTags()));

        //image
        Portfolio portfolio = requestDto.toEntity();
        String image = requestDto.getImage();


        if(image != null && image != "" && image!="null") {
                String decodedImage = decodeImage(image);
                portfolio.setImage(decodedImage);
            }



        for(String name : tags) {
            // join table 작업
            PortfolioTag portfolioTag = PortfolioTag.builder()
                    .tag(name)
                    .build();

            portfolio.addPortfolioTag(portfolioTag);

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
                Tag tag = tagRepository.findByName(name).orElseThrow(()-> new IllegalArgumentException("해당 태그가 존재하지 않습니다. name=" + name));
                tag.add_count();
                tag.setScore(score);

            }
        }

        // user 연동
        user.addPortfolio(portfolio);
        userRepository.save(user);
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
        Set<String> tags = portfolioTagRepository.getTagListByPortfolioId(id);
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

        // join 테이블 작업 초기화 & portfolio_tag에서 옛 태그 레코드 삭제
        portfolio.deletePortfolioTag();
        System.out.println("===========================================================================");
        for(PortfolioTag p : portfolio.getPortfolioTag()) {
            System.out.println(p.getTag());
        }
        System.out.println("===========================================================================");

        for(PortfolioTag portfolioTag: portfolioTagRepository.findAllByPortfolio(portfolio)) {
            portfolioTagRepository.delete(portfolioTag);
        }

        System.out.println("===========================================================================");
        for(PortfolioTag portfolioTag: portfolioTagRepository.findAllByPortfolio(portfolio)) {
            System.out.println(portfolioTag.getTag());
        }
        System.out.println("===========================================================================");

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
            PortfolioTag portfolioTag = PortfolioTag.builder()
                    .tag(name)
                    .build();

            portfolio.addPortfolioTag(portfolioTag);
        }


        String image = requestDto.getImage();
            if(image != null) {
                image = decodeImage(image);
            }

        portfolio.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getCategory(),
                requestDto.getHuntingField(), image);

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 포트폴리오는 존재하지 않습니다. id=" + id));
        Set<String> tags = portfolioTagRepository.getTagListByPortfolioId(id);

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

    @Transactional
    public PortfolioListResponseList findAll(PortfolioPaginationRequestDto requestDto) {

        List<Portfolio> portfolios = portfolioRepository.findAll();
        List<Long> portfoliosId = new ArrayList<>();
        for(Portfolio p : portfolios) {
            portfoliosId.add(p.getId());
        }

        List<Portfolio> newPortfolios = new ArrayList<>();
        List<Long> newPortfolioId1 = new ArrayList<>();
        List<Long> newPortfolioId2 = new ArrayList<>();

        // 아무 필터링도 걸리지 않으면 전체 출력
        if(requestDto.getCategory() == "") {
            newPortfolioId1 = portfoliosId;
        }

        if(requestDto.getHuntingField() == "") {
            newPortfolioId2 = portfoliosId;
        }

        // 필터 하나라도 요청이 있으면
        for(Portfolio p : portfolios) {

            if(requestDto.getCategory() != "" && requestDto.getCategory().equals(p.getCategory())) {
                newPortfolioId1.add(p.getId());
            }

            if(requestDto.getHuntingField() != "" && requestDto.getHuntingField().equals(p.getHuntingField())) {
                newPortfolioId2.add(p.getId());
            }
        }

        // 교집합 찾기
        List<Long> intersect = newPortfolioId1.stream()
                .filter(newPortfolioId2::contains)
                .collect(Collectors.toList());


        // id를 project로 변환
        for(Long id: intersect) {
            Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
            newPortfolios.add(portfolio);
        }

        // search
        if(requestDto.getKeyword() != "") {

            String[] splitKeyword = requestDto.getKeyword()
                    .replaceAll(",", "")
                    .trim().split("\\s+"); // space 제거 후 공백으로 배열화

            boolean flag = false;

            Iterator<Portfolio> iter = newPortfolios.iterator();

            while(iter.hasNext()) {
                Portfolio np = iter.next();
                Set<String> tags = portfolioTagRepository.getTagListByPortfolioId(np.getId());

                for(String keyword: splitKeyword) {
                    // 프로젝트 제목 or 본문에 키워드 들어있으면
                    if (np.getTitle().contains(keyword) || np.getContent().contains(keyword))
                        flag = true;

                    // 프로젝트 태그에 키워드 들어있으면
                    for(String tag: tags) {
                        if(tag.contains(keyword))
                            flag = true;
                    }
                }
                if(!flag) {
                    iter.remove();
                }
                flag = false;
            }

        }

        // page로 프로젝트 리스트 변환
        MutableSortDefinition mutableSortDefinition = new MutableSortDefinition(requestDto.getSortColumn(), false, false);
        PagedListHolder page = new PagedListHolder<>(newPortfolios, mutableSortDefinition);

        page.resort();
        page.setPageSize(requestDto.getSize()); // number of items per page
        page.setPage(requestDto.getPage());      // set to first page


        PortfolioListResponseList list = PortfolioListResponseList.builder()
                .pagelist(page.getPageList())
                .nrOfElements(page.getNrOfElements())
                .build();

        return list;
    }
}