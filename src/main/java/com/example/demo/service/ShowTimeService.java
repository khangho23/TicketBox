package com.example.demo.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.dao.ShowTimeDao;
import com.example.demo.domain.ShowTimeHasMovies;
import com.example.demo.dto.ShowTimeDto;
import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.enums.RequestStatusEnum;
import com.example.demo.entity.ShowTime;
import com.example.demo.exception.InvalidRequestParameterException;

import jakarta.validation.ConstraintViolationException;

@Service
public class ShowTimeService{
	@Autowired
	private ShowTimeDao showtimeDao;

	public Object findShowtimeByMovieAndDate(String showdate, String movieId, Integer page, Pageable pageable) {

		List<ShowTimeDto> listMovieByDate = showtimeDao.findShowTimeByMovieAndDate(showdate, movieId);
		int pageSize = 4;
		int startItem = page * pageSize;
		int totalElements = listMovieByDate.size();
		if (listMovieByDate.size() <= 0) {
			return "Xin lỗi, không có xuất chiếu vào ngày này, hãy chọn một ngày khác.";
		} else {
			int toIndex = Math.min(startItem + pageSize, totalElements);
			System.out.println(toIndex);
			listMovieByDate = listMovieByDate.subList(startItem, toIndex);
			return new PageImpl<>(listMovieByDate, PageRequest.of(pageable.getPageNumber(), pageSize), totalElements);
		}
	}

	public ShowTimeDto findById(int id) throws InvalidRequestParameterException {
		return showtimeDao.findById(id)
				.orElseThrow(() -> new InvalidRequestParameterException("id", RequestParameterEnum.NOT_FOUND));
	}

	public List<ShowTimeDto> findAll() {
		return showtimeDao.findAll();
	}

	public RequestStatusEnum insert(ShowTime showTime) throws InvalidRequestParameterException {
		return (showtimeDao.insert(showTime)) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE;
	}

	public RequestStatusEnum update(ShowTime req) throws InvalidRequestParameterException {
		showtimeDao.findById(req.getId())
				.orElseThrow(() -> new InvalidRequestParameterException(req.getId() + "",
						RequestParameterEnum.NOT_FOUND));
		return (showtimeDao.update(req) == 1) ? RequestStatusEnum.SUCCESS
				: RequestStatusEnum.FAILURE;
	}

	public RequestStatusEnum delete(Integer id) throws InvalidRequestParameterException {
		ShowTime showTime = showtimeDao.findById(id)
				.orElseThrow(() -> new InvalidRequestParameterException(id + "",
						RequestParameterEnum.NOT_FOUND));
		
		try {
			showtimeDao.delete(showTime);
		} catch (Exception ex) {
			throw new InvalidRequestParameterException("Vi phạm ràng buộc khóa ngoại", RequestParameterEnum.EXISTS);
		}
		
		return RequestStatusEnum.SUCCESS;
	}

	public List<?> findByCurrentDate(String branchid) {
		List<ShowTimeHasMovies> dto = showtimeDao.findByCurrentDate(branchid).stream()
				.map(ShowTimeHasMovies::convert).toList();
		return dto;
	}
	
	public List<ShowTimeDto> findByBranch(Optional<String> branchId) throws InvalidRequestParameterException {
		branchId.orElseThrow(() -> new InvalidRequestParameterException("Showtime branchId", RequestParameterEnum.NOTHING));
		List<ShowTimeDto> showtime = showtimeDao.findByBranch(branchId.get());
		if (showtime.isEmpty()) throw new InvalidRequestParameterException("Showtime", RequestParameterEnum.NOT_FOUND);
		
		return showtime;
	}
}
