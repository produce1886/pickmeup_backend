package kr.co.pickmeup;

import io.swagger.models.auth.In;
import kr.co.pickmeup.domain.comments.Comment;
import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projects.ProjectRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentSaveTest {

    // final 붙이면 왜 안되는지?
    @Autowired
    private ProjectRepository projectRepository;

    @Before
    public void setUp() {
//        projectRepository = new ProjectRepository();
    }

    @Test
    public void 댓글_저장_테스트() {
        System.out.println("--Test start--");

        Project project = Project.builder()
                                .title("Test title2")
                                .email("doky")
                                .content("Test content2")
                                .build();

        Comment comment1 = Comment.builder()
                                .email("doky")
                                .content("Test comment2_1 content")
                                .build();
        project.addComment(comment1);

        Comment comment2 = Comment.builder()
                                .email("doky")
                                .content("Test comment2_2 content")
                                .build();
        project.addComment(comment2);

        projectRepository.save(project);

        System.out.println("--Test end--");

    }

    @Test
    public void 댓글_삭제_테스트() {
        Project project = projectRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("프로젝트 존재하지 않습니다."));

        projectRepository.delete(project);
    }

    @Test
    public void 태그스코어_계산_테스트() {
        double[] tmp = new double[3];

        System.out.println(tmp[0]);
        List<Integer> t = new ArrayList<>();
        t.add(1);

        System.out.println(t.size());

    }

    @Test
    public void 공백_제거_테스트() {
        String str = "안녕하세    요      !!ㅏㅣㅡㅡㅏ";
        for(String s :str.trim().split("\\s+")) {
            System.out.println(s);
        }
    }

}
