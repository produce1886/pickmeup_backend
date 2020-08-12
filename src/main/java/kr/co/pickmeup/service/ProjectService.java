package kr.co.pickmeup.service;

import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projects.ProjectRepository;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
import kr.co.pickmeup.domain.projectsTags.ProjectTagRepository;
import kr.co.pickmeup.domain.tags.Tag;
import kr.co.pickmeup.domain.tags.TagRepository;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectResponseDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectSaveRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TagService tagService;
    private final TagRepository tagRepository;
    private final ProjectTagRepository projectTagRepository;

    // create
    @Transactional
    public Long save(ProjectSaveRequestDto requestDto) {
        String content = requestDto.getContent();
        Set<String> tags = tagService.parseTags(content);

        Project project = requestDto.toEntity();

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

        return projectRepository.save(project).getId();
    }

    // read one
    @Transactional
    public ProjectResponseDto findById (Long id) {
        Project entity = projectRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        entity.addViewNum();

        return new ProjectResponseDto(entity);
    }

    /*
    // read all
    @Transactional(readOnly = true)
    public List<ProjectsListResponseDto> findAllDesc() {
        return projectRepository.findAllDesc().stream()
                .map(ProjectsListResponseDto::new)
                .collect(Collectors.toList());
    }
    */

    // update
    @Transactional
    public Long update(Long id, ProjectUpdateRequestDto requestDto) {
        Project project = projectRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        String content = project.getContent();
        Set<String> tags = tagService.parseTags(content);

        String new_content = requestDto.getContent();
        Set<String> new_tags = tagService.parseTags(new_content);

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
        for(ProjectTag projectTag: projectTagRepository.findAllByProject(project)) {
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

            project.addProjectTag(projectTag);
        }

        project.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Project project = projectRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        Set<String> tags = tagService.parseTags(project.getContent());

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

        projectRepository.delete(project);
    }

    @Transactional
    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
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
