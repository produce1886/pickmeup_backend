package kr.co.pickmeup.service;


import kr.co.pickmeup.domain.projects.Project;
import kr.co.pickmeup.domain.projectsTags.ProjectTag;
import kr.co.pickmeup.domain.tags.Tag;
import kr.co.pickmeup.domain.users.User;
import kr.co.pickmeup.domain.users.UserRepository;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectUpdateRequestDto;
import kr.co.pickmeup.web.dto.UsersDto.UserResponseDto;
import kr.co.pickmeup.web.dto.UsersDto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // image decoding
    public String decodeImage(String encodedImage) {
        byte[] image = Base64.decodeBase64(encodedImage);
        if(image==null)
                return null;
        String result = new String(image);
        return result;
    }

    @Transactional
    public UserResponseDto findById (Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자는 존재하지 않습니다. id=" + id));
        return new UserResponseDto(user);
    }

    @Transactional
    public Long update(Long id, UserUpdateDto updateDto) {

        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 사용자는 존재하지 않습니다. id=" + id));

        String image = updateDto.getImage();

        String newImage = null;
        if(image != null)
            newImage = decodeImage(image);


        user.update(updateDto.getEmail(), updateDto.getUsername(), decodeImage(updateDto.getImage()), updateDto.getSex(), updateDto.getBirth(),
                updateDto.getUniversity(), updateDto.getMajor(), updateDto.getArea(), updateDto.getInterests(), updateDto.getIntroduce(),
                updateDto.getSex_security(), updateDto.getBirth_security(), updateDto.getUniversity_security(), updateDto.getMajor_security(),
                updateDto.getArea_security(), updateDto.getInterests_security(), updateDto.getIntroduce_security());

        return user.getId();
    }
}

