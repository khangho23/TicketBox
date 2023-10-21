package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import com.example.demo.entity.ShowTime;

import lombok.Data;

/*
 * 
 * ShowTimeDto of ShowTime
 * 
 */

@Data
@Entity
public class ShowTimeDto extends ShowTime {
	/*
	 * RoomName of ShowTimeDto
	 */
	
	@Column(name = "movieid")
    private String movieId;
	
	/*
	 * RoomName of ShowTimeDto
	 */
	
	@Column(name = "room")
    private String roomName;
	
	/*
	 * DimensionName of ShowTimeDto
	 */

	@Column(name = "dimensionname")
	private String dimensionName;

	/*
	 * branchId of ShowTimeDto
	 */
	@Column(name = "branchId")
	private String branchId;
	/*
	 * branchName of ShowTimeDto
	 */

	@Column(name = "branchname")
	private String branchName;

	/*
	 * branchAddress of ShowTimeDto
	 */

	@Column(name = "branchAddress")
	private String branchAddress;

	/*
	 * movieName of ShowTimeDto
	 */
	@Column(name = "movieName")
	private String movieName;
	/*
	 * languageName of ShowTimeDto
	 */
	@Column(name = "languageName")
	private String languageName;
}
