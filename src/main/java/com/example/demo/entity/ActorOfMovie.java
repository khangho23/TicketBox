package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * ActorOfMovie of Movie
 * 
 */
@Entity
@Data
@Table
@NoArgsConstructor
public class ActorOfMovie {
	/*
	 *	actorId of ActorOfMovie 
	 */
    @Column
    private int actorId;
	/*
	 *	movieId of actor
	 */
    @Column
    private String movieId;
}
