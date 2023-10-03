package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.dao.ShowTimeDao;
import com.example.demo.dto.ShowTimeDto;
@Service
public class ShowTimeService {
	@Autowired
	private ShowTimeDao showtimeDao;
	
	public Object findShowtimeByMovieAndDate(String showdate, String movieId ,Integer page, Pageable pageable ){
		
		List<ShowTimeDto>listMovieByDate = showtimeDao.findShowTimeByMovieAndDate(showdate,movieId);
		int pageSize = 4;
		int startItem = page * pageSize;
		int totalElements = listMovieByDate.size();
		if (listMovieByDate.size() <= 0) {
			return "Xin lỗi, không có xuất chiếu vào ngày này, hãy chọn một ngày khác.";
		} else {
			int toIndex = Math.min(startItem + pageSize, totalElements);
			System.out.println(toIndex);
			listMovieByDate = listMovieByDate.subList(startItem, toIndex);
			return new PageImpl<>(listMovieByDate, PageRequest.of(pageable.getPageNumber(), pageSize),totalElements);
		}
	}

	public ShowTimeDto findById(int id){
		return showtimeDao.findById(id);
	}
}
