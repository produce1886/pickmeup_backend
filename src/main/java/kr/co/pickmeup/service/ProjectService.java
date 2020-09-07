package kr.co.pickmeup.service;

import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projects.ProjectRepository;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
import kr.co.pickmeup.domain.projectsTags.ProjectTagRepository;
import kr.co.pickmeup.domain.tags.Tag;
import kr.co.pickmeup.domain.tags.TagRepository;
import kr.co.pickmeup.domain.users.User;
import kr.co.pickmeup.domain.users.UserRepository;
import kr.co.pickmeup.web.dto.ProjectsDto.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TagService tagService;
    private final TagRepository tagRepository;
    private final ProjectTagRepository projectTagRepository;
    private final UserRepository userRepository;

    // image decoding
    public String decodeImage(String encodedImage) {
        byte[] image = Base64.decodeBase64(encodedImage);
        String result = new String(image);

        return result;
    }

    // create
    @Transactional
    public Long save(ProjectSaveRequestDto requestDto) {
        // user 찾기
        String email = requestDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. email=" + email));

        // 태그 작업
        Set<String> tags = new HashSet<>(Arrays.asList(requestDto.getTags()));


        Project project = requestDto.toEntity();
        String image = project.getImage();
        if(image != null) {
            String decodedImage = decodeImage(image);
            project.setImage(decodedImage);
        }

        for(String name : tags) {
            // join table 작업
            ProjectTag projectTag = ProjectTag.builder()
                    .tag(name)
                    .build();

            project.addProjectTag(projectTag);

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
        user.addProject(project);
        return  userRepository.save(user).getId();
    }

    // read one
    @Transactional
    public ProjectResponseDto findById (Long id) {
        Project entity = projectRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        entity.addViewNum();

        return new ProjectResponseDto(entity);
    }

    // update
    @Transactional
    public Long update(Long id, ProjectUpdateRequestDto requestDto) {
        Project project = projectRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

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
        project.deleteProjectTag();
        System.out.println("===========================================================================");
        for(ProjectTag p : project.getProjectTag()) {
            System.out.println(p.getTag());
        }
        System.out.println("===========================================================================");


        for(ProjectTag projectTag: projectTagRepository.findAllByProject(project)) {
            projectTagRepository.delete(projectTag);
        }

        System.out.println("===========================================================================");
        for(ProjectTag projectTag: projectTagRepository.findAllByProject(project)) {
            System.out.println(projectTag.getTag());
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

                Tag tag = tagRepository.findByName(name).orElseThrow(()-> new IllegalArgumentException("해당 태그가 존재하지 않습니다. name=" + name));
                tag.add_count();
                tag.setScore(score);
            }

            // join 테이블 작업
            ProjectTag projectTag = ProjectTag.builder()
                    .tag(name)
                    .build();

            project.addProjectTag(projectTag);
        }

        String image = requestDto.getImage();
        if(image != null)
            image = decodeImage(image);

        project.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getCategory(),
                requestDto.getHuntingField(), requestDto.getRegion(), requestDto.getProjectCategory(), image);

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Project project = projectRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
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

        projectRepository.delete(project);
    }

    @Transactional
    public ProjectListResponseDto findAll(ProjectPaginationRequestDto requestDto) {

        List<Project> projects = projectRepository.findAll();
        List<Long> projectsId = new ArrayList<>();
        for(Project p : projects) {
            projectsId.add(p.getId());
        }

        List<Project> newProjects = new ArrayList<>();
        List<Long> newProjectsId1 = new ArrayList<>();
        List<Long> newProjectsId2 = new ArrayList<>();
        List<Long> newProjectsId3 = new ArrayList<>();
        List<Long> newProjectsId4 = new ArrayList<>();

        // 아무 필터링도 걸리지 않으면 전체 출력
        if(requestDto.getCategory() == "") {
            newProjectsId1 = projectsId;
        }

        if(requestDto.getHuntingField() == "") {
            newProjectsId2 = projectsId;
        }

        if(requestDto.getRegion() == "") {
            newProjectsId3 = projectsId;
        }

        if(requestDto.getProjectCategory() == "") {
            newProjectsId4 = projectsId;
        }

        // 필터 하나라도 요청이 있으면
        for(Project p : projects) {

            if(requestDto.getCategory() != "" && requestDto.getCategory().equals(p.getCategory())) {
                newProjectsId1.add(p.getId());
            }

            if(requestDto.getHuntingField() != "" && requestDto.getHuntingField().equals(p.getHuntingField())) {
                newProjectsId2.add(p.getId());
            }

            if(requestDto.getRegion() != "" && requestDto.getRegion().equals(p.getRegion())) {
                newProjectsId3.add(p.getId());
            }

            if(requestDto.getProjectCategory() != "" && requestDto.getProjectCategory().equals(p.getProjectCategory())) {
                newProjectsId4.add(p.getId());
            }
        }

        // 교집합 찾기
        List<Long> intersect1 = newProjectsId1.stream()
                .filter(newProjectsId2::contains)
                .collect(Collectors.toList());

        List<Long> intersect2 = newProjectsId3.stream()
                .filter(newProjectsId4::contains)
                .collect(Collectors.toList());

        List<Long> intersect = intersect1.stream()
                .filter(intersect2::contains)
                .collect(Collectors.toList());


        // id를 project로 변환
        for(Long id: intersect) {
            Project project = projectRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
            newProjects.add(project);
        }

        // search
        if(requestDto.getKeyword() != "") {

            String[] splitKeyword = requestDto.getKeyword()
                    .replaceAll(",", "")
                    .trim().split("\\s+"); // space 제거 후 공백으로 배열화

            boolean flag = false;

            Iterator<Project> iter = newProjects.iterator();

            while(iter.hasNext()) {
                Project np = iter.next();
                Set<String> tags = projectTagRepository.getTagListByProjectId(np.getId());

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
        PagedListHolder page = new PagedListHolder<>(newProjects, mutableSortDefinition);

        page.resort();
        page.setPageSize(requestDto.getSize()); // number of items per page
        page.setPage(requestDto.getPage());      // set to first page

        ProjectListResponseDto list = ProjectListResponseDto.builder()
                .pagelist(page.getPageList())
                .nrOfElements(page.getNrOfElements())
                .build();

        return list;
    }

    @Transactional
    public Page<Project> findByCategory(String category, Pageable pageable) {
        return projectRepository.findByCategory(category, pageable);
    }

    @Transactional
    public Page<Project> findByHuntingField(String huntingField, Pageable pageable) {
        return projectRepository.findByHuntingField(huntingField, pageable);
    }

    @Transactional
    public Page<Project> findByRegion(String region, Pageable pageable) {
        return projectRepository.findByRegion(region, pageable);
    }

    @Transactional
    public Page<Project> findByProjectCategory(String projectCategory, Pageable pageable) {
        return projectRepository.findByProjectCategory(projectCategory, pageable);
    }

    // search
    @Transactional
    public List<ProjectResponseDto> searchProjects(String keyword) {
        List<Project> projects = new ArrayList<>();

        // keyword가 들어간 Title을 가진 애들을 찾음
        List<Project> projects_byTitle = projectRepository.findByTitleContains(keyword);

        // keyword가 들어간 Content를 가진 애들을 찾음
        List<Project> projects_byContent = projectRepository.findByContentContains(keyword);

        // merge
        projects.addAll(projects_byTitle);
        projects.addAll(projects_byContent);

        List<ProjectResponseDto> projectResponseDtoList = new ArrayList<>();

        if(projects.isEmpty()) return projectResponseDtoList;

        for(Project project : projects) {
            projectResponseDtoList.add(new ProjectResponseDto(project));
        }

        return projectResponseDtoList;
    }
}