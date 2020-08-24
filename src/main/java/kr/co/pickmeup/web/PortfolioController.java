package kr.co.pickmeup.web;

import kr.co.pickmeup.service.PortfolioService;
import kr.co.pickmeup.web.dto.PortfolioDto.PortfolioPaginationRequestDto;
import kr.co.pickmeup.web.dto.PortfolioDto.PortfolioResponseDto;
import kr.co.pickmeup.web.dto.PortfolioDto.PortfolioSaveRequestDto;
import kr.co.pickmeup.web.dto.PortfolioDto.PortfolioUpdateRequestDto;
import kr.co.pickmeup.web.dto.ProjectsDto.ProjectPaginationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(value="/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    // 프로젝트 save
    @PostMapping()
    public ResponseEntity save(@RequestBody PortfolioSaveRequestDto requestDto) {
        portfolioService.save(requestDto);
        return new ResponseEntity<> (HttpStatus.OK);
    }

    // 하나 불러오기 (detail)
    @GetMapping("/{id}")
    public PortfolioResponseDto findById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PortfolioUpdateRequestDto requestDto) {
        Long portfolioId = portfolioService.update(id, requestDto);
        return new ResponseEntity<> (portfolioId, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        portfolioService.delete(id);
        return new ResponseEntity<> (HttpStatus.OK);
    }

    // 필터링, 전체 불러오기
    @PostMapping("list")
    public ResponseEntity findAll(Pageable pageable, @RequestBody PortfolioPaginationRequestDto requestDto) {
        PagedListHolder page = portfolioService.findAll(requestDto);
        return new ResponseEntity<>(page.getPageList(), HttpStatus.OK);
    }
}
