package kr.co.pickmeup.service;

import kr.co.pickmeup.domain.projects.Projects;
import kr.co.pickmeup.domain.projects.ProjectsRepository;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectResponseDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectSaveRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectUpdateRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectsService {

    private final ProjectsRepository projectsRepository;


    // create
    public Long save(ProjectSaveRequestDto requestDto) {
        return projectsRepository.save(requestDto.toEntity()).getId();
    }

    // read one
    @Transactional
    public ProjectResponseDto findById (Long id) {
        Projects entity = projectsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        return new ProjectResponseDto(entity);
    }

    // read all
    @Transactional(readOnly = true)
    public List<ProjectsListResponseDto> findAllDesc() {
        return projectsRepository.findAllDesc().stream()
                .map(ProjectsListResponseDto::new)
                .collect(Collectors.toList());
    }

    // update
    @Transactional
    public Long update(Long id, ProjectUpdateRequestDto requestDto) {

        Projects notices = projectsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id=" + id));
        notices.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Projects notice = projectsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        projectsRepository.delete(notice);
    }
}
